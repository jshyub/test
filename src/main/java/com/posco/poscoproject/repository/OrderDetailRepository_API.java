package com.posco.poscoproject.repository;

import com.posco.poscoproject.entity.Item;
import com.posco.poscoproject.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository_API extends JpaRepository<OrderDetail, Long> {


    @Query(value = "select * from order_detail o where o.order_branch_id = :id", nativeQuery = true)
    List<OrderDetail> findOrderDetailById(@Param("id") Long id);

    @Query(value = "select od.quantity, i.name, i.price from order_detail od " +
            "inner join item i " +
            "on od.item_id = i.id " +
            "where od.order_branch_id = :order_branch_id", nativeQuery = true)
    List<Object[]> selectOrderDetailWithItemByOrderBranchId(@Param("order_branch_id") Long order_branch_id);

}
