package com.posco.poscoproject.service;


import com.posco.poscoproject.dto.ItemDTO;
import com.posco.poscoproject.entity.Item;
import com.posco.poscoproject.repository.SampleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class SampleService {
    private SampleRepository sampleRepository;

    @Transactional
    public List<ItemDTO> getSamplelist() {
        List<Item> Items = sampleRepository.findAll();
        List<ItemDTO> itemDTOList = new ArrayList<>();

        for (Item item : Items) {
            ItemDTO itemDTO = ItemDTO.builder()
                    .id(item.getId())
                    .category(item.getCategory())
                    .name(item.getName())
                    .price(item.getPrice())
                    .build();
            itemDTOList.add(itemDTO);
//            System.out.println("dto : " + itemDTO.toString());
        }

//        System.out.println("size : " + itemDTOList.size());

        return itemDTOList;
    }
}
