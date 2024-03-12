package com.posco.poscoproject.controller;

import com.posco.poscoproject.repository.ItemRepository_API;
import com.posco.poscoproject.service.ItemService_API;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

// 얘는 전반적으로 모든 부분에서 테스트할때 저는 특히 디비 연결해서 값 가져올때 미리 확인하거나할때 많이 써요
@SpringBootTest
@Transactional
class OrderControllerTest {

    @Autowired
    private ItemService_API itemService_api;

    @Autowired
    private ItemRepository_API itemRepository_api;



    @Test
    public void itemDTOtesT(){
        System.out.println();
        System.out.println("엔티티 : "+itemService_api.getPost(7L));  // 그러면 서비스에서 getPostㄹ ㅏ는 메서드에서 에러가 나는 거에요

        System.out.println();
//        System.out.println("test : " + itemRepository_api.findById(1L).get());  // 얘가 에러가 나요 얘 자체가 잘못된거에요
//        System.out.println("test : " + item);

        // 이거 6시까지는 되야해요

        // 1. 레포지토리에서 item 엔티티를 가져와야해요 => 이거 확인 완료
        // 2. 레포지토리에서 마찬가지로 orderDetail 엔티티도 잘 불러와지나 같이 확인해야해요 => 다음에 해야하는건 order_detail 값이 불러와지는지 확인
//        2번 전체가 조금 이해가 안됐어요
//         3. 두개 다 잘 불러와지면 그 다음에 모델에 담고 뷰로 보내고 => 여기서는 바로 item.price 하기보다는 일단
        // ${item} ㅇ런식으로 전체가 다 찍히는지 확인해보세요 그래야지만 내가 원하는 값이 다 넘어오는지 알 수 있어요
        // 4. 그 다음에 ${item.price} 해서 실제로 값을 원하는 부분에 넣어주세요

    }
}