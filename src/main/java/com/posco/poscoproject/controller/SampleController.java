package com.posco.poscoproject.controller;

import com.posco.poscoproject.dto.ItemDTO;
import com.posco.poscoproject.service.OrderBranchService_API;
import com.posco.poscoproject.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;
    private final OrderBranchService_API orderBranchService;

//    @GetMapping("/sampleList")
//    public String list(Model model) {
//        List<ItemDTO> sampleList = sampleService.getSamplelist();
//        System.out.println("LIST : " + sampleList.toString());
//
//        model.addAttribute("sampleList", sampleList);
//        return "Sample/list";
//    }

//    @GetMapping("/main")
//    public String main(Model model) {
//
//        return "main";
//    }
}
