package com.posco.poscoproject.entity;

import lombok.*;

import javax.persistence.*;

// 해당 클래스는 추후 분리될 예정
@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id", updatable = false)
    private Category category;

    @Column
    private String name;

    @Column
    private int price;

    @ManyToOne
    @JoinColumn(name="order_branch_id")
    private OrderBranch orderBranch;

    @Builder
    public Item(Long id, String name, int price) {
        this.id = id;
//        this.category = category;
        this.name = name;
        this.price = price;
    }
}

