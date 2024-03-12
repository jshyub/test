package com.posco.poscoproject.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderBranch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 더미데이터 삽입을 위해 AUTO 에서 IDENTITY 로 변경함 /상협
    private Long id;

    @OneToMany(mappedBy = "orderBranch", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetail = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Column
    private int payment;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Builder
    public OrderBranch(Long id, int payment, Date date) {
        this.id = id;
        this.payment = payment;
        this.orderDate = date;
    }

    public OrderBranch(Long id, Branch branch, int payment){
        this.id = id;
        this.payment = payment;
        this.branch = branch;
    }
}
