package com.posco.poscoproject.controller;

import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.enumtype.Authority;
import com.posco.poscoproject.flaskutill.FlaskRequest;
import com.posco.poscoproject.service.BranchService;
import com.posco.poscoproject.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final BranchService branchService;
    private final FlaskRequest flaskRequest;
    @Autowired
    private ChartService chartService;

    @GetMapping(value = {"/", "/login/loginform", "login/loginformsub"})
    public String income(){
//                System.out.println("loginform");

        return "login/login";
    }

    @GetMapping("/login/error")
    public String loginFail(@RequestParam(value = "error", required = false)String error,
                            @RequestParam(value = "exception", required = false)String exception,
                            Model model, RedirectAttributes redirect) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login/login";
    }

    @GetMapping("/main")
    public String goMain(Model model, Principal principal){
        Branch branch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("user", branch);

        if(Authority.ADMIN.equals(branch.getAuthority())){

            long daily = branchService.dailySales().isPresent() ? branchService.dailySales().get() : 0 ;
//            long week = branchService.weekSales();
            long month = branchService.monthSales();

            model.addAttribute("count", branchService.getBranchCount());
            model.addAttribute("daily", daily);
//            model.addAttribute("week", week);
            model.addAttribute("month", month);

            model.addAttribute("compareLstMonth", branchService.compareLastMonth(month));
//            model.addAttribute("compareLstWeek", branchService.compareLastWeek(week));
            model.addAttribute("compareLstMonth", branchService.compareLastMonth(month));
            // 월별 매출 총액 그래프
            List<Object[]> monthlySalesData = chartService.getMonthlySales();
            model.addAttribute("monthlySalesData", monthlySalesData);
            // 가장 많이 팔린 메뉴5
            List<Object[]> topSoldItems = chartService.findTop5SoldItems();
            model.addAttribute("topSoldItems", topSoldItems);
            // 지점별 매출 상위 10개 지점 정보
            List<Object[]> topSalesBranches = chartService.findTop10SalesBranches();
            model.addAttribute("topSalesBranches", topSalesBranches);
        }else{

            long daily = branchService.dailySalesById(branch.getId()).isPresent() ? branchService.dailySales().get() : 0 ;
//            long week = branchService.weekSalesById(branch.getId());
            long month = branchService.monthSalesById(branch.getId());

            model.addAttribute("count", branchService.orderCountById(branch.getId()));
            model.addAttribute("daily", daily);
//            model.addAttribute("week", week);
            model.addAttribute("month", month);

//            model.addAttribute("compareLstMonth", branchService.compareLastMonth(month));
//            model.addAttribute("compareLstWeek", branchService.compareLastWeekById(week, branch.getId()));
            model.addAttribute("compareLstMonth", branchService.compareLastMonthById(month, branch.getId()));

            // 특정 지점의 월별 매출 데이터 추가
            List<Object[]> monthlySalesByBranch = chartService.getMonthlySalesByBranch(branch.getId());
            model.addAttribute("monthlySalesDataByBranch", monthlySalesByBranch);

            // 특정 지점의 가장 많이 팔린 상품 상위 5개 추가
            List<Object[]> topSoldItemsByBranch = chartService.findTop5SoldItemsByBranch(branch.getId());
            model.addAttribute("topSoldItemsByBranch", topSoldItemsByBranch);
        }
        return "main";
    }
}
