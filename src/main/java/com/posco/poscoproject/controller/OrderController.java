package com.posco.poscoproject.controller;

import com.posco.poscoproject.dto.*;
import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.entity.Item;
import com.posco.poscoproject.entity.OrderBranch;
import com.posco.poscoproject.enumtype.Authority;
import com.posco.poscoproject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final BranchService branchService;

    private final OrderBranchService_API orderBranchService_api;

    private final OrderDetailService_API orderDetailService_api;

    private final ItemService_API itemService_api;

    /*
    // 검색 처리 (미완성)
    @GetMapping("/order/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model) {
        List<ItemDTO> itemDTOList = orderSerivce.searchPosts(keyword);

        model.addAttribute("itemDTOList", itemDTOList);

        return "Sample/list2";
    }
     */

/*
    // 페이징 처리 (코드 가독성 위해 주석 처리하고
    @GetMapping({"/orders", "/orders/{page}", "/search"})
    public String orderHist(@PathVariable("page") Optional<Integer> page,
                            Principal principal, Model model,
                            @RequestParam(value = "startDate", required = false, defaultValue = "1900-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                            @RequestParam(value = "endDate", required = false, defaultValue = "9999-12-31") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        SearchDTO searchDto = new SearchDTO(startDate, endDate);
        System.out.println("search : " + searchDto.toString());

        // 이때 principal.getName 은 email 에 해당함
        Branch branch = branchService.findBranchByEmail(principal.getName());


        // 페이징 데이터가 담기는 리스트
        Page<OrderBranch> orderBranchList;

//        System.out.println("list : "+orderBranchList);

        if (branch.getAuthority().equals(Authority.ADMIN)) {
                orderBranchList = orderService.searchAllByOrderDate(pageable, searchDto);

        }else{
                orderBranchList = orderService.searchByOrderDate(pageable, branch.getId(), searchDto);

        }

//        System.out.println("testlist : " + orderBranchList);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        model.addAttribute("orders", orderBranchList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "branch/list";
    }
 */

    @GetMapping({"/orders", "/orders/{page}", "/search", "/postorder/list"})
    public String orderHist(Principal principal, Model model,
                            @RequestParam(value = "startDate", required = false, defaultValue = "1900-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                            @RequestParam(value = "endDate", required = false, defaultValue = "9999-12-31") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                            @PageableDefault(page = 0, size = 10, sort = "orderDate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<OrderBranch> list = orderBranchService_api.orderList(pageable);
//        Page<Item> lists = itemService_api.itemList(pageable);

        List<OrderBranchDTO> orderBranchDTOList = orderBranchService_api.getOrderBranchlist();
        List<ItemDTO> itemDTOList = itemService_api.getItemlist();

        model.addAttribute("orderBranchDTOList", orderBranchDTOList);
        model.addAttribute("itemDTOList", itemDTOList);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 9, list.getTotalPages());

        SearchDTO searchDto = new SearchDTO(startDate, endDate);
        System.out.println("search : " + searchDto.toString());

        // 이때 principal.getName 은 email 에 해당함
        Branch branch = branchService.findBranchByEmail(principal.getName());

        // 페이징 데이터가 담기는 리스트
        Page<OrderBranch> orderBranchList;

        if (branch.getAuthority().equals(Authority.ADMIN)) {
            orderBranchList = orderService.searchAllByOrderDate(pageable, searchDto);

        } else {
            orderBranchList = orderService.searchByOrderDate(pageable, branch.getId(), searchDto);

        }

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("orders", orderBranchService_api.orderList(pageable));
        model.addAttribute("user", branchService.findBranchByEmail(principal.getName()));


        return "branch/list";
    }


//    @PostMapping({"/search", "/search/{page}"})
//    public String searchByOrderDate(@ModelAttribute SearchDto searchDto) {
////        model.addAttribute("noticeBoardDtoList", orderService.searchByOrderDate(pageable, searchDto));
//        System.out.println("search : " + searchDto);
////        orderService.searchByOrderDate(pageable, searchDto).forEach(item ->{
////            System.out.println("item : " + item.getOrderDate());
////        });
//        return "branch/list";
//    }

    // 매출 리스트 list.html (주문 내역 상세 목록)
//    @GetMapping("/postorder")
//    public String list(Model model, Principal principal) {
//        List<OrderBranchDTO> orderBranchDTOList = orderBranchService_api.getOrderBranchlist();
//
//        model.addAttribute("orderBranchDTOList", orderBranchDTOList);
//        model.addAttribute("user", branchService.findBranchByEmail(principal.getName()));
//
//        return "branch/list";
//    }

//    @GetMapping("/postorder/list")
//    public String orderBranchs(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
//    Pageable pageable, Principal principal){
//
//        model.addAttribute("list", orderBranchService_api.orderbranch(pageable))
//
//        model.addAttribute("user", branchService.findBranchByEmail(principal.getName()));
//
//        return "branch/list";
//        }

}
