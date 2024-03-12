package com.posco.poscoproject.service;

import com.posco.poscoproject.dto.ItemDTO;
import com.posco.poscoproject.dto.OrderDetailDTO;
import com.posco.poscoproject.entity.Item;
import com.posco.poscoproject.entity.OrderDetail;
import com.posco.poscoproject.repository.ItemRepository_API;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 해당 클래스는 추후 분리될 예정
@Service
@Transactional
@RequiredArgsConstructor
public class ItemService_API {

    private final ItemRepository_API itemRepositoryAPI;

    private final ItemRepository_API itemRepository_api;

    public void insertCol(Item item){
        itemRepositoryAPI.save(item);
    }

    public int getPrice(Long id){
        return itemRepositoryAPI.getById(id).getPrice();
    }

    public Page<Item> itemList(Pageable pageable){
        return itemRepository_api.findAll(pageable);
    }

    // 주문 내역 상세 불러올 떄
    @Transactional
    public ItemDTO getPost(Long id) {
        Optional<Item> itemWrapper = itemRepository_api.findById(id);
        Item item = itemWrapper.get();

        ItemDTO itemDTO = ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .build();
        return itemDTO;
    }

    @Transactional
    public List<ItemDTO> getItemlist() {
        List<Item> items = itemRepository_api.findAll();
        List<ItemDTO> itemDTOList = new ArrayList<>();

        for ( Item item : items) {
            ItemDTO itemDTO = ItemDTO.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .price(item.getPrice())
                    .build();

            itemDTOList.add(itemDTO);
        }

        return itemDTOList;
    }


}
