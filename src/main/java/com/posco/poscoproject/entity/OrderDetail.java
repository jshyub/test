package com.posco.poscoproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name="order_branch_id")
    private OrderBranch orderBranch;

    @Column
    private int quantity;


    public OrderDetail(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

}
