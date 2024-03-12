package com.posco.poscoproject.service;

import com.posco.poscoproject.entity.OrderBranch;
import com.posco.poscoproject.repository.OrderBranchRepository;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@SpringBootTest
@Transactional
@Slf4j
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderBranchRepository orderBranchRepository;

    @Test
    @DisplayName("페이징")
    public void page() {
        Pageable pageable = PageRequest.of(1, 10);

//        Page<OrderBranch> list = orderService.searchAllByOrderDate(pageable);
//
//        System.out.println(list.getTotalPages());
//
//        list.forEach(i -> {
//            System.out.println(i.getId() + " " + i.getOrderDate());
//        });
    }

    @Test
    @DisplayName("날짜 검색 페이징")
    public void datePaging(){
        Pageable pageable = PageRequest.of(1, 10);

        LocalDate date = LocalDate.now();
        Date d = Date.valueOf(date);

        System.out.println("d : " + d);
        orderBranchRepository.searchAllByOrderDate(pageable, d, d);

    }

    @Test
    public void pageTest(){

    }
}