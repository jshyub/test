package com.posco.poscoproject.controller;

import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.repository.BranchRepository;
import com.posco.poscoproject.service.BranchService;
import com.posco.poscoproject.service.ChartService;
import com.posco.poscoproject.service.SampleService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import static com.mysql.cj.conf.PropertyKey.logger;

@Controller
@RequiredArgsConstructor
public class ChartController {

    @Autowired
    private ChartService chartService;

    private final SampleService sampleService;
    private final BranchService branchService;
    private final BranchRepository branchRepository;
//    @GetMapping(value={"/index"})
//    public String index(){
//        return "index";
//    }



    @ModelAttribute("branchId")
    public String getCurrentBranchId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String email = authentication.getName();

        // 사용자의 username을 이용하여 해당 사용자의 지점 ID를 가져옴
        Branch branch = (Branch) branchRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found"));

        return branch.getId().toString();
    }

    @GetMapping("/chart/month")
    public String showChart(Model model, @RequestParam(name = "branchId", required = false) Long branchId) {
        Date today = new Date(System.currentTimeMillis());
        // 본사 데이터
        Long dailySalesAll = chartService.getDailySalesAll(today);
        Long weekSalesAll = chartService.getWeekSalesAll();
        Long lastWeekSalesAll = chartService.getLastWeekSalesAll();
        Long monthSalesAll = chartService.getMonthSalesAll();
        Long lastMonthSalesAll = chartService.getLastMonthSalesAll();
        int branchCount = chartService.getBranchCount();

        // 이하 동일한 방식으로 지점별 데이터를 가져와서 Model에 추가

        model.addAttribute("dailySalesAll", dailySalesAll);
        model.addAttribute("weekSalesAll", weekSalesAll);
        model.addAttribute("lastWeekSalesAll", lastWeekSalesAll);
        model.addAttribute("monthSalesAll", monthSalesAll);
        model.addAttribute("lastMonthSalesAll", lastMonthSalesAll);
        model.addAttribute("branchCount", branchCount);

        if (branchId != null) {
            // branchId가 넘어온 경우에만 해당 지점의 차트를 보여줌
            return showBranchChart(branchId, model);
        }

        return "/chart/month"; // 차트를 표시할 View의 이름
    }

    // 지점별 차트 페이지를 보여주는 메서드
    @GetMapping("/chart/month/{branchId}")
    public String showBranchChart(@PathVariable("branchId") Long branchId, Model model) {

        Date today = new Date(System.currentTimeMillis());

        Long dailySales = chartService.getDailySalesById(today, branchId);
        Long weekSales = chartService.getWeekSalesById(branchId);
        Long lastWeekSales = chartService.getLastWeekSalesById(branchId);
        Long monthSales = chartService.getMonthSalesById(branchId);
        Long lastMonthSales = chartService.getLastMonthSalesById(branchId);
        int orderCount = chartService.getOrderCountById(branchId);

        model.addAttribute("dailySales", dailySales);
        model.addAttribute("weekSales", weekSales);
        model.addAttribute("lastWeekSales", lastWeekSales);
        model.addAttribute("monthSales", monthSales);
        model.addAttribute("lastMonthSales", lastMonthSales);
        model.addAttribute("orderCount", orderCount);

        return "/chart/month"; // 지점별 차트를 표시할 View의 이름
    }


//    @GetMapping("/chart/month")
//    public String showMonthChart(Model model, Principal principal, @PathVariable(required = false) Long branchId, HttpSession session) {
//        if (principal != null && principal instanceof Authentication) {
//            Authentication authentication = (Authentication) principal;
//            if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("USER"))) {
//                // USER 역할인 경우
//                Branch branch = branchService.findBranchByEmail(principal.getName());
//                branchId = branch.getId();
//                if (branchId == null) {
//                    // branchId가 없는 경우에는 리다이렉트로 branchId 필요
//                    // branchId를 가져오는 로직 필요
//                    return "redirect:/chart/month/{branchId}";
//                } else {
//                    // branchId가 null이 아닌 경우에만 세션에 저장
//                    session.setAttribute("branchId", branchId);
//                }
//            }
//        }
//        // ADMIN 역할이거나 권한이 없는 경우, 또는 branchId가 주어진 경우 기본 페이지로 이동
//        Date today = new Date(System.currentTimeMillis());
//        Long dailySalesAll = chartService.getDailySalesAll(today);
//        Long weekSalesAll = chartService.getWeekSalesAll();
//        Long lastWeekSalesAll = chartService.getLastWeekSalesAll();
//        Long monthSalesAll = chartService.getMonthSalesAll();
//        Long lastMonthSalesAll = chartService.getLastMonthSalesAll();
//        int branchCount = chartService.getBranchCount();
//
//        model.addAttribute("dailySalesAll", dailySalesAll);
//        model.addAttribute("weekSalesAll", weekSalesAll);
//        model.addAttribute("lastWeekSalesAll", lastWeekSalesAll);
//        model.addAttribute("monthSalesAll", monthSalesAll);
//        model.addAttribute("lastMonthSalesAll", lastMonthSalesAll);
//        model.addAttribute("branchCount", branchCount);
//
//        if (branchId != null) {
//            Long dailySales = chartService.getDailySalesById(today, branchId);
//            Long weekSales = chartService.getWeekSalesById(branchId);
//            Long lastWeekSales = chartService.getLastWeekSalesById(branchId);
//            Long monthSales = chartService.getMonthSalesById(branchId);
//            Long lastMonthSales = chartService.getLastMonthSalesById(branchId);
//            int orderCount = chartService.getOrderCountById(branchId);
//
//            model.addAttribute("dailySales", dailySales);
//            model.addAttribute("weekSales", weekSales);
//            model.addAttribute("lastWeekSales", lastWeekSales);
//            model.addAttribute("monthSales", monthSales);
//            model.addAttribute("lastMonthSales", lastMonthSales);
//            model.addAttribute("orderCount", orderCount);
//        }
//
//        return "/chart/month"; // 차트를 표시할 View의 이름
//    }




//    @GetMapping("/chart/month")
//    public String monthAll(Model model, Principal principal){
//        model.addAttribute("user", branchService.findBranchByEmail(principal.getName()));
//
//        return "/chart/month";
//    }

    @GetMapping("/chart/daily")
    public String dailyAll(Model model, Principal principal){
        // ChartService를 통해 지난 7일간의 일별 매출 데이터를 조회
        List<Object[]> dailySalesData = chartService.findDailySalesForLast7Days();
        // 조회된 데이터를 모델에 추가
        model.addAttribute("dailySalesData", dailySalesData);
        model.addAttribute("user", branchService.findBranchByEmail(principal.getName()));
        return "/chart/daily";
    }


    @GetMapping("/chart/daily/{branchId}")
    public String dailySalesByBranch(@PathVariable("branchId") Long branchId, Model model, Principal principal) {
        // ChartService를 통해 특정 지점(branchId)의 지난 7일간의 일별 매출 데이터를 조회
        List<Object[]> dailySalesDataByBranch = chartService.findDailySalesForLast7DaysByBranch(branchId);

        // 조회된 데이터를 모델에 추가
        model.addAttribute("dailySalesDataByBranch", dailySalesDataByBranch);
        // 현재 사용자의 지점 정보를 모델에 추가 (예: 네비게이션 바에서 사용자의 지점명 표시 등에 사용)
        Branch currentBranch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("currentBranch", currentBranch);
        model.addAttribute("user", branchService.findBranchByEmail(principal.getName()));
        return "/chart/daily";
    }



//    @GetMapping("chart2")
//    public String chart2(Model model, Principal principal){
//
//        Map<String, Integer> itemsSum = getData();
//
//        model.addAttribute("data", itemsSum);
//
//        return "chart2";
//    }

//    @GetMapping("chart3")
//    public String chart3(Model model){
//
//        return "chart3";
//    }


    @GetMapping("ajaxtest")
    public String ajaxtest(){
        return "ajaxtest";
    }

    @GetMapping("ajax")
    public String ajax(){
        return "ajax";
    }

//    Map<String, Integer> getData(){
//        Map<String, Integer> itemsSum = new TreeMap<>();
//
//        for(int i=1; i<=30; i++){
//            itemsSum.put(String.valueOf(i), (int)((Math.random()+1)*10)*15000);
//        }
//
//        return itemsSum;
//    }

}
