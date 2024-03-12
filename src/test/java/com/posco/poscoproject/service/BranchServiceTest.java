package com.posco.poscoproject.service;

import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.enumtype.Authority;
import com.posco.poscoproject.enumtype.BranchStatus;
import com.posco.poscoproject.repository.BranchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@SpringBootTest
//@Transactional
class BranchServiceTest {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private BranchService branchService;

    @Test
    @DisplayName("데이터 넣기")
    public void branchtest(){
        String encodedPassword = passwordEncoder.encode("1234"); // 비밀번호 암호화

        Branch admin = Branch.builder()
                .id(0L)
                .email("user4@kokeetea.com")
                .password(encodedPassword)
                .name("부산")
                .address("부산시")
                .phone("010-0000-0000")
                .status(BranchStatus.Confirmed)
                .authority(Authority.USER)
                .build();
        System.out.println("branch : " + admin);

        branchRepository.save(admin);
    }

    @Test
    @DisplayName("branchDAta")
    public void test(){
        long week = branchRepository.getWeekSalesAll();
        long day = branchRepository.getDailySalesAll(Date.valueOf(LocalDate.now()));

        System.out.println("week : " + week);
        System.out.println("day : " + day);
    }

    @Test
    @DisplayName("주별 계산")
    public void weekTest(){
        double week = branchRepository.getWeekSalesAll();
        double lastWeek = branchRepository.getLastWeekSalesAll();

        System.out.println("week : "+week);
        System.out.println("last : "+lastWeek);
        double per = ((week - lastWeek) / lastWeek) * 100;
        System.out.println("per : "+per);

    }

    @Test
    @DisplayName("지점 계산")
    public void branchSales(){
        double week = branchRepository.getWeekSalesById(1L);
        double lastWeek = branchRepository.getLastWeekSalesById(1L);
        int count = branchRepository.getOrderCountById(1L);

        System.out.println("week : "+week);
        System.out.println("last : "+lastWeek);
        double per = ((week - lastWeek) / lastWeek) * 100;
        System.out.println("per : "+per);

        System.out.println("count : " + count);
    }
}