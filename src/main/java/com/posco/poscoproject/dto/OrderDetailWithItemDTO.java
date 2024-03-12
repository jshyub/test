package com.posco.poscoproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderDetailWithItemDTO {
    private int quantity;
    private String name;
    private int price;
}
