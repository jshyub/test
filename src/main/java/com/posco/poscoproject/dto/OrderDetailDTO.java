package com.posco.poscoproject.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderDetailDTO {

    private Long id;

    private ItemDTO itemDTO;

    private int quantity;

    private int price;

    @Builder
    public OrderDetailDTO (Long id, ItemDTO itemDTO, int quantity, int price){
        this.id = id;
        this.quantity = quantity;
        this.itemDTO = itemDTO;
        this.price = price;
    }

}
