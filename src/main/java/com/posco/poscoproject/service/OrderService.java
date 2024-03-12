package com.posco.poscoproject.service;

import com.posco.poscoproject.dto.ItemDTO;
import com.posco.poscoproject.dto.SearchDTO;
import com.posco.poscoproject.entity.Item;
import com.posco.poscoproject.entity.OrderBranch;
import com.posco.poscoproject.entity.OrderDetail;
import com.posco.poscoproject.repository.ItemRepository_API;
import com.posco.poscoproject.repository.OrderBranchRepository;
import com.posco.poscoproject.repository.OrderDetailRepository_API;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderBranchRepository orderBranchRepository;

    private final ItemRepository_API itemRepository_api;

    private final OrderDetailRepository_API orderDetailRepository_api;

    
    // 검색 처리
//    @Transactional
//    public List<ItemDTO> searchPosts(String keyword) {
//        List<OrderBranch> orderBranches = orderBranchRepository.findByOrder_dateContaining(keyword);
//        List<ItemDTO> itemDTOList = new ArrayList<>();
//
//        if (orderBranches.isEmpty()) return itemDTOList;
//
//        for (OrderBranch orderBranch : orderBranches) {
//            itemDTOList.add(this.convertEntityToDto(orderBranch));
//        }
//
//        return itemDTOList;
//    }


    // 특정 지점 매출 내역 페이징 처리
//    public Page<OrderBranch> getOrderList(Long id, Pageable pageable) {
//
//        List<OrderBranch> list = orderBranchRepository.getBranchOrderListById(id, pageable);
//        Long totalCount = orderBranchRepository.orderCount(id);
//
////        System.out.println("list : " + list);
////        System.out.println("total : "+totalCount);
//
//        return new PageImpl<OrderBranch>(list, pageable, totalCount);
//    }
//
//
//    // 본사 매출 내역 페이징 처리
//    public Page<OrderBranch> getAllBranchOrderList(Pageable pageable) {
//
//        List<OrderBranch> list = orderBranchRepository.getBranchOrderAll(pageable);
////        System.out.println("list : " + list);
//
//        int totalCount = orderBranchRepository.orderCountAll();
////        System.out.println("total : "+totalCount);
//
//        return new PageImpl<OrderBranch>(list, pageable, totalCount);
//    }


    // 날짜 검색 : 본사
    public Page<OrderBranch> searchAllByOrderDate(Pageable pageable, SearchDTO search) {
        List<OrderBranch> list = orderBranchRepository.searchAllByOrderDate(pageable, Date.valueOf(search.getStartDate()), Date.valueOf(search.getEndDate()));
        int totalCount = orderBranchRepository.orderCountAll(Date.valueOf(search.getStartDate()), Date.valueOf(search.getEndDate()));


        return new PageImpl<OrderBranch>(list, pageable, totalCount);

    }

    // 날짜 검색 : 지점
    public Page<OrderBranch> searchByOrderDate(Pageable pageable, Long id, SearchDTO search) {
        List<OrderBranch> list = orderBranchRepository.searchByOrderDate(pageable, id, Date.valueOf(search.getStartDate()), Date.valueOf(search.getEndDate()));

        int totalCount = orderBranchRepository.orderCount(id, Date.valueOf(search.getStartDate()), Date.valueOf(search.getEndDate()));

        return new PageImpl<OrderBranch>(list, pageable, totalCount);
    }

    // 페이징
    public Page<OrderBranch> orderList(Pageable pageable) {
        return orderBranchRepository.findAll(pageable);
    }

    // 매출 리스트 listDetail.html 주문 상세 내역 조회
    @Transactional
    public ItemDTO getPost(Long id) {
        Optional<Item> itemWrapper = itemRepository_api.findById(id);
        Optional<OrderBranch> orderBranchWrapper = orderBranchRepository.findById(id);
        Optional<OrderDetail> orderDetailWrapper = orderDetailRepository_api.findById(id);
        Item item = itemWrapper.get();


        ItemDTO itemDTO = ItemDTO.builder()
                .id(item.getId())
                .category(item.getCategory())
                .name(item.getName())
                .price(item.getPrice())
                .build();

        return itemDTO;
    }

}
