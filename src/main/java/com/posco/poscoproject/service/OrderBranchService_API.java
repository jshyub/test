package com.posco.poscoproject.service;

import com.posco.poscoproject.dto.OrderBranchDTO;
import com.posco.poscoproject.entity.OrderBranch;
import com.posco.poscoproject.repository.OrderBranchRepository;
import com.posco.poscoproject.repository.OrderBranchRepository_API;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderBranchService_API {

    private final OrderBranchRepository_API orderBranchRepositoryAPI;

    private final OrderBranchRepository_API orderBranchRepository_api;

    public Long insertCol(OrderBranch orderBranch){

        return orderBranchRepositoryAPI.save(orderBranch).getId();
    }

    // 페이징
    public Page<OrderBranch> orderList(Pageable pageable) {
        return orderBranchRepository_api.findAll(pageable);
    }

    // 주문 상세 내역 목록 list.html
    @Transactional
    public List<OrderBranchDTO> getOrderBranchlist() {
        List<OrderBranch> orderBranches = orderBranchRepository_api.findAll();
        List<OrderBranchDTO> orderBranchDTOList = new ArrayList<>();

        for (OrderBranch orderBranch : orderBranches) {
            OrderBranchDTO orderBranchDTO = OrderBranchDTO.builder()
                    .id(orderBranch.getId())
                    .payment(orderBranch.getPayment())
                    .date(orderBranch.getOrderDate())
                    .build();
            orderBranchDTOList.add(orderBranchDTO);
        }
        return orderBranchDTOList;
    }

}
