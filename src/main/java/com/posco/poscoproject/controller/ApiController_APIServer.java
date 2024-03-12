package com.posco.poscoproject.controller;

import com.posco.poscoproject.dto.OrderDetailWithItemDTO;
import com.posco.poscoproject.entity.*;
import com.posco.poscoproject.service.*;

import lombok.RequiredArgsConstructor;


import org.json.simple.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Random;

// 해당 클래스는 추후 분리될 예정
@RestController // restController
@RequiredArgsConstructor
public class ApiController_APIServer {

    private final CategoryService_API categoryServiceAPI;
    private final ItemService_API itemServiceAPI;
    private final OrderBranchService_API orderBranchServiceAPI;
    private final OrderDetailService_API orderDetailServiceAPI;

    // ajax 요청 insert 처리
    @PostMapping("insert_category")
    public  String insert_category(@RequestBody JSONObject jsonObject){
//      ajax 에서 받아온 데이터로 카테고리 등록을 위한 객체 생성
        Category category = new Category();
        category.setName(jsonObject.get("name").toString());

//        카테고리 저장
        categoryServiceAPI.insertCol(category);
        return "ok";
    }
    @PostMapping("insert_item")
    public String insert_item(@RequestBody JSONObject jsonObject){
//      ajax에서 받아온 데이터로 item 등록을 위한 객체 생성
//      item 객체에 Category객체가 저장되므로 카테고리를 필요한 데이터만 넣어서 임의로 생성하여 저장한다.
        Category category = new Category();
        category.setId(Long.parseLong(jsonObject.get("category_id").toString()));

//      item 객체를 생성해서 넣어 준다.
        Item item = new Item();
        item.setPrice(Integer.parseInt(jsonObject.get("price").toString()));
        item.setName(jsonObject.get("name").toString());
        item.setCategory(category);
        itemServiceAPI.insertCol(item);

        return "ok";
    }
    @PostMapping("insert_order")
    public String insert_order(@RequestBody JSONObject jsonObject){
//      ajax 에서 미리 생성한 데이터를 받아온다. orderBranch 와 orderDetail 에 필요한 모든 데이터가 넘어옴
//      item_id와 quantity 쌍 리스트 혹은 맵이 필요하다.
//      datetime 과 payment 데이터가 넘어온다.
//      {"qnt":[3,4,1,5,3,1,1,5],"item_id":[11,33,22,53,44,24,38,22], "timestamp":1660964493110}

        System.out.println("jsonObject" + jsonObject);

        //  JSONArray 가 잘 안 되어서 직접 파싱해주었다.
        String qnt = jsonObject.get("qnt").toString();
        String quantity[] = qnt.replace("[", "").replace("]", "").replace(" ", "").split(",");
        String i_id = jsonObject.get("item_id").toString();
        String item_id[] = i_id.replace("[", "").replace("]", "").replace(" ", "").split(",");

//        for (int i = 0; i < item_id.length; i++)
//            System.out.println(">>>>>>>"+item_id[i]+"<<<<");

//      임의의 날짜 생성(ajax에서 timestamp 값으로 넘겨준 날짜를 설정해준다)
        Date date = new Date();
        date.setTime(Long.parseLong(jsonObject.get("timestamp").toString()));

//      orderDetail 에는 orderBranch 의 id가 필요하므로 orderBranch 를 먼저 insert 한 뒤 인서트한
//      id를 받아와 orderDetail 에 넣어줘야 한다.
//        for(int i = 0; i < quantity.length; i++)
//            System.out.println("qnt>>>>>>>>>>>" + quantity[i]);

//      payment 는 item 에서 price 값을 가져와 수량과 곱해 주어야 한다.
        int payment = 0;
        for(int i = 0; i < item_id.length; i++){
            int price = itemServiceAPI.getPrice(Long.parseLong(item_id[i]));
            payment += price * Integer.parseInt(quantity[i]);
        }

        OrderBranch orderBranch = new OrderBranch();
        //  종속성이 걸려 있으므로 branch 를 생성해 넣어야 한다.
        Branch branch = new Branch();

        //  orderBranch id 가져오기
        //  나중에 지점이 추가되면 이메일을 변경해 준다.
//        branch.setId(branchService.findBranchByEmail("goodjobproject@naver.com").getId());
        //  orderBranch가 여러개 입력되어 id 값을 랜덤값으로 변경
        Random random = new Random();
        branch.setId((long)random.nextInt(38) + 1);
        orderBranch.setBranch(branch);
        orderBranch.setOrderDate(date);
        orderBranch.setPayment(payment);

        //orderBranch 에서 생성한 id를 받아와 orderDetail 생성
        Long order_branch_id = orderBranchServiceAPI.insertCol(orderBranch);

        //  item 갯수만큼 order_detail 이 들어간다.
        //  반복문 시작 영속성 컨텍스트 문제로 for 문 안에서 엔티티를 생성해야 한다.
        //  item과 quantitiy만 변경해서 insert 했을 때 하나만 들어갔다(for문의 마지막 데이터)
//        System.out.println("item_id.length>>>>>>>>>>>>>>>>>>>>"+item_id.length);
        for(int i = 0; i < item_id.length; i++) {
            OrderDetail orderDetail = new OrderDetail();
            //  OrderDetail 에 넣을 OrderBranch 생성(id 값만 필요하므로 id만 넣어서 사용한다)
            OrderBranch orderBranchId = new OrderBranch();
            orderBranchId.setId(order_branch_id);
            orderDetail.setOrderBranch(orderBranchId);
            Item item = new Item();
            item.setId(Long.parseLong(item_id[i]));
            orderDetail.setItem(item);
            orderDetail.setQuantity(Integer.parseInt(quantity[i]));
            orderDetailServiceAPI.insertCol(orderDetail);
//            System.out.println("item_id>>>>>>>>>>>"+ item_id[i] + ": quantity>>>>>>>>>>>>>>>>>" + quantity[i]);
        }

        return "ok";
    }

//    주문 상세내역을 위한 API, 현재 파일은 주문내역 api 콜을 받는 파일이므로 나중에 분리하는 게 좋겠다.
    @PostMapping("/postorder/{no}")
    public @ResponseBody List<OrderDetailWithItemDTO> orderDetails(@PathVariable("no") Long order_branch_id) {
        //  order_detail_id를 사용해 orderdetailservice api에서 주문 상세목록을 받아온다
        System.out.println("orderDetails>>>>>>>>>>>>>>>>>>>>>>>>>>>"+order_branch_id);
        List<OrderDetailWithItemDTO> orderDetailWithItemDTOList = orderDetailServiceAPI.getOrderDetailWithItem(order_branch_id);

        return orderDetailWithItemDTOList;
    }

}