package com.posco.poscoproject.dto;

import com.posco.poscoproject.entity.Category;
import com.posco.poscoproject.entity.Item;
import com.posco.poscoproject.entity.SampleEntity;
import lombok.*;

import javax.persistence.Column;
import java.util.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class ItemDTO {
    private Long id;

    private Category category;

    private String name;

    private int price;

    @Builder
    public ItemDTO(Long id, Category category, String name, int price) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
    }
}
