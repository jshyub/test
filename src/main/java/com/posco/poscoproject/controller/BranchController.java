package com.posco.poscoproject.controller;

import com.posco.poscoproject.dto.BranchDTO;
import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;
    private final PasswordEncoder passwordEncoder;

    // 지점 insert 요청
    @GetMapping("/branchreq")
    public @ResponseBody String branchInsert(){
        for(int i=2; i<40; i++){
            String branch = "branch" + i;
            BranchDTO branchDTO = BranchDTO.builder()
                    .id((long) i)
                    .name(branch)
                    .email("kokeettea" + i + "@kokeetea.com")
                    .password(branch+i)
                    .address(branch)
                    .phone("010-1234-456" + i)
                    .build();

            branchService.insertBranch(branchDTO);

        }

        return "insert_ok";
    }

    // 지점 관리 페이지
    @GetMapping("branchmanage")
    public String branchManage(Model model, Principal principal){
        model.addAttribute("list", branchService.getBranchList());
        model.addAttribute("user", branchService.findBranchByEmail(principal.getName()));

        return "branch/branchManage";
    }

    @GetMapping("/branchmanage/list")
    public String branchManage(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
    Pageable pageable, Principal principal){

        Page<Branch> list = branchService.branchList(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 9, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("user", branchService.findBranchByEmail(principal.getName()));

        return "branch/branchManage";
    }

    @PostMapping("/branchPermit")
    public @ResponseBody String permit(String id){
//        System.out.println("허가");
        branchService.permitBranch(Long.parseLong(id));
        return "permit";
    }

    //지점정보 조회
    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model, Principal principal) {
        BranchDTO branchDTO = branchService.getPost(no);

        model.addAttribute("branchDTO", branchDTO);

        model.addAttribute("user", branchService.findBranchByEmail(principal.getName()));

        return "branch/branchDetail";
    }

    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model, Principal principal) {
        BranchDTO branchDTO = branchService.getPost(no);
//        System.out.println(branchDTO.toString()); status와 authority 가 안 넘어온다.>> BranchDTO와 BranchService에서 객체 생성시 빠져 있다!!
//        수정 완료!
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+branchDTO.getAuthority());
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+branchDTO.getStatus());
        model.addAttribute("branchDTO", branchDTO);
        model.addAttribute("user", branchService.findBranchByEmail(principal.getName()));

        return "branch/branchUpdate";
    }

    @PutMapping("/post/edit/{no}")
    public String update(BranchDTO branchDTO, Principal principal, Model model) {
        // 지점 정보 업데이트 시 패스워드 인코딩
        branchDTO.setPassword(passwordEncoder.encode(branchDTO.getPassword()));
        branchService.savePost(branchDTO);
        model.addAttribute("user", branchService.findBranchByEmail(principal.getName()));

        return "branch/branchDetail";
    }
}
