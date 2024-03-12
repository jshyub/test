package com.posco.poscoproject.service;

import com.posco.poscoproject.entity.Item;
import com.posco.poscoproject.entity.OrderDetail;
import com.posco.poscoproject.repository.BranchRepository;
import com.posco.poscoproject.repository.ItemRepository_API;
import com.posco.poscoproject.repository.OrderBranchRepository;
import com.posco.poscoproject.repository.OrderDetailRepository_API;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderDetailService_APITest {

    @Autowired
    private ItemRepository_API itemRepository_api;

    @Autowired
    private OrderDetailRepository_API orderDetailRepository_api;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private OrderBranchRepository orderBranchRepository;

    @Test
    public void ItemTest(){
//        List<OrderDetail> list = orderBranchRepository.findOrderDetailById(7L);
        List<OrderDetail> list = orderDetailRepository_api.findOrderDetailById(7L);
        list.forEach(i->{
            System.out.println("name : " + i.getItem().getName());
        });
//        list.get(0).getItem().getName();
        // 다른거 해주시면 좋을거같아요

    // 지금까지 작업 메인에서 하신거에요?
    }
}