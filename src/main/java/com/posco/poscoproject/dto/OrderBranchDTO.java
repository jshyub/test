package com.posco.poscoproject.dto;

import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.entity.OrderBranch;
import com.posco.poscoproject.entity.OrderDetail;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderBranchDTO {
    private Long id;

    private List<OrderDetail> orderDetail = new ArrayList<>();

    private Branch branch;

    private int payment;

    private Date orderDate;

    @Builder
    public OrderBranchDTO(Long id, int payment, Date date) {
        this.id = id;
        this.payment = payment;
        this.orderDate = date;
    }

}