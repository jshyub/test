package com.posco.poscoproject.service;

import com.posco.poscoproject.dto.OrderBranchDTO;
import com.posco.poscoproject.dto.OrderDetailDTO;
import com.posco.poscoproject.dto.OrderDetailWithItemDTO;
import com.posco.poscoproject.entity.Item;
import com.posco.poscoproject.entity.OrderBranch;
import com.posco.poscoproject.entity.OrderDetail;

import com.posco.poscoproject.repository.ItemRepository_API;
import com.posco.poscoproject.repository.OrderDetailRepository_API;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailService_API {

    private final OrderDetailRepository_API orderDetailRepositoryAPI;

    public void insertCol(OrderDetail orderDetail){
        orderDetailRepositoryAPI.save(orderDetail);
    }

    // 상세 주문 내역 불러올 떄
    @Transactional
    public List<OrderDetailWithItemDTO> getOrderDetailWithItem(Long order_branch_id) {
        List<Object[]> result = orderDetailRepositoryAPI.selectOrderDetailWithItemByOrderBranchId(order_branch_id);
        List<OrderDetailWithItemDTO> orderDetailWithItemDTOList = new ArrayList<>();
        System.out.println("result>>>>>>>>>>>>"+result.get(0)[0]);
        for(int i = 0; i < result.size(); i++){
            OrderDetailWithItemDTO o = new OrderDetailWithItemDTO();
            o.setQuantity((int)result.get(i)[0]);
            o.setName(result.get(i)[1].toString());
            o.setPrice((int)result.get(i)[2]);
            orderDetailWithItemDTOList.add(o);
        };

        return orderDetailWithItemDTOList;
    }


    @Transactional
    public List<OrderBranchDTO> getOrderDetaillist(){
        List<OrderDetail> orderDetails = orderDetailRepositoryAPI.findAll();
        List<OrderBranchDTO> orderBranchDTOList = new ArrayList<>();

        for (OrderDetail orderDetail : orderDetails) {
            OrderBranchDTO orderBranchDTO = OrderBranchDTO.builder()
                    .id(orderDetail.getId())
                    .payment(orderDetail.getQuantity())
                    .build();
            orderBranchDTOList.add(orderBranchDTO);
        }
        return orderBranchDTOList;
    }

}
