package com.posco.poscoproject.service;

import com.posco.poscoproject.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Service
public class ChartService {
    @Autowired
    private BranchRepository branchRepository;

    public Long getDailySalesAll(Date date) {
        return branchRepository.getDailySalesAll(date);
    }

    public Long getWeekSalesAll() {
        return branchRepository.getWeekSalesAll();
    }

    public Long getLastWeekSalesAll() {
        return branchRepository.getLastWeekSalesAll();
    }

    public Long getMonthSalesAll() {
        return branchRepository.getMonthSalesAll();
    }

    public Long getLastMonthSalesAll() {
        return branchRepository.getLastMonthSalesAll();
    }

    public int getBranchCount() {
        return branchRepository.getBranchCount();
    }

    public List<Object[]> getMonthlySales() {
        return branchRepository.getMonthlySales();
    }

    // 일별 매출 - 본사
    public List<Object[]> findDailySalesForLast7Days() {
        Calendar cal = Calendar.getInstance();

        // 종료 날짜 설정 (오늘, java.util.Date)
        java.util.Date endDateUtil = cal.getTime();
        // java.util.Date를 java.sql.Date로 변환
        Date endDate = new Date(endDateUtil.getTime());

        // 시작 날짜 설정 (7일 전, java.util.Date)
        cal.add(Calendar.DAY_OF_MONTH, -7);
        java.util.Date startDateUtil = cal.getTime();
        // java.util.Date를 java.sql.Date로 변환
        Date startDate = new Date(startDateUtil.getTime());

        // Repository 메서드 호출
        return branchRepository.findDailySalesForLast7Days(startDate, endDate);
    }

    // 일별 매출 - 지점
    public List<Object[]> findDailySalesForLast7DaysByBranch(Long branchId) {
        Calendar cal = Calendar.getInstance();

        // 종료 날짜 설정 (오늘, java.util.Date)
        java.util.Date endDateUtil = cal.getTime();
        // java.util.Date를 java.sql.Date로 변환
        Date endDate = new Date(endDateUtil.getTime());

        // 시작 날짜 설정 (7일 전, java.util.Date)
        cal.add(Calendar.DAY_OF_MONTH, -7);
        java.util.Date startDateUtil = cal.getTime();
        // java.util.Date를 java.sql.Date로 변환
        Date startDate = new Date(startDateUtil.getTime());

        // Repository 메서드 호출, 특정 지점의 ID를 매개변수로 전달
        return branchRepository.findDailySalesForLast7DaysByBranch(startDate, endDate, branchId);
    }


    // 지점 매출 순위 top10
    public List<Object[]> findTop10SalesBranches() {
        Pageable topTen = PageRequest.of(0, 10); // 상위 10개 지점
        return branchRepository.findTop10SalesBranches(topTen);
    }

    // 가장 많이 팔린 메뉴 top5
    public List<Object[]> findTop5SoldItems() {
        Pageable topFive = PageRequest.of(0, 5); // 상위 5개 결과
        return branchRepository.findTop5SoldItems(topFive);
    }

    // 특정 지점의 월별 매출 데이터를 가져오는 메서드
    public List<Object[]> getMonthlySalesByBranch(Long branchId) {
        return branchRepository.getMonthlySalesByBranch(branchId);
    }

    // 지점별 데이터 가져오기
    public Long getDailySalesById(Date date, Long id) {
        return branchRepository.getDailySalesById(date, id);
    }

    public Long getWeekSalesById(Long id) {
        return branchRepository.getWeekSalesById(id);
    }

    public Long getLastWeekSalesById(Long id) {
        return branchRepository.getLastWeekSalesById(id);
    }

    public Long getMonthSalesById(Long id) {
        return branchRepository.getMonthSalesById(id);
    }

    public Long getLastMonthSalesById(Long id) {
        return branchRepository.getLastMonthSalesById(id);
    }

    public int getOrderCountById(Long id) {
        return branchRepository.getOrderCountById(id);
    }

    // 가장 많이 팔린 메뉴 top5 - 지점
    public List<Object[]> findTop5SoldItemsByBranch(Long branchId) {
        Pageable topFive = PageRequest.of(0, 5); // 상위 5개 결과만 조회
        return branchRepository.findTop5SoldItemsByBranch(branchId, topFive);
    }
}
