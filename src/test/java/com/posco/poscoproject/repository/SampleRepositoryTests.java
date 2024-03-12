package com.posco.poscoproject.repository;

import com.posco.poscoproject.dto.ItemDTO;
import com.posco.poscoproject.entity.Item;
import com.posco.poscoproject.service.SampleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
public class SampleRepositoryTests {

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private SampleService sampleService;


    @DisplayName("데이터 넣기")
    public void insertData() {
//        IntStream.rangeClosed(1, 50).forEach(i -> {
//            Item item = Item.builder()
//                    .category_id("User" + i)
//                    .name("Hello SpringBoot" + i)
//                    .price((int) ((Math.random()+1)*100))
//                    .build();
//            System.out.println(sampleRepository.save(item));
//        });
    }

    @Test
    @DisplayName("모든 내용 가져오기")
    public void getAll(){
        this.insertData();

//        List<SampleEntity> sampleEntities = sampleRepository.findAll();
        List<ItemDTO> itemDTOList = sampleService.getSamplelist();

        System.out.println("list : "+ itemDTOList.toString());
    }
}
