package com.posco.poscoproject.repository;

import com.posco.poscoproject.dto.ItemDTO;
import com.posco.poscoproject.entity.OrderBranch;
import com.posco.poscoproject.entity.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface OrderBranchRepository extends JpaRepository<OrderBranch, Long>{
    // id 기준 해당 지점의 모든 주문 정보 가져오기 => orderDate 내림차순
//    @Query("select o from OrderBranch o where o.branch.id = :id order by o.orderDate desc ")
//    List<OrderBranch> getBranchOrderListById(@Param("id") Long id, Pageable pageable);
//
//    // id 기준 해당 지점의 모든 주문정보의 갯수 가져오기
//    @Query("select count(o) from OrderBranch o where o.branch.id = :id")
//    Long orderCount(@Param("id") Long id);

    // 지점 기준 모든 주문 정보 가져오기 + 날짜기준 검색
    @Query("SELECT o FROM OrderBranch o WHERE o.orderDate BETWEEN :start AND :end AND o.id=:id order by o.orderDate desc" )
    List<OrderBranch> searchByOrderDate(Pageable pageable, @Param("id")Long id, @Param("start") Date start, @Param("end") Date end);

    // id 기준 해당 지점의 모든 주문정보의 갯수 가져오기
    @Query("SELECT count(o) FROM OrderBranch o WHERE o.orderDate BETWEEN :start AND :end AND o.id=:id" )
    int orderCount(@Param("id")Long id, @Param("start") Date start, @Param("end") Date end);

//    // 본사 기준 모든 지점의 모든 주문 정보 가져오기
//    @Query("select o from OrderBranch o order by o.orderDate desc")
//    List<OrderBranch> getBranchOrderAll(Pageable pageable);
//
//    // 본사 기준 모든 주문정보의 갯수 가져오기
//    @Query("select count(o) from OrderBranch o")
//    int orderCountAll();

    // 본사 기준 모든 주문 정보 가져오기 + 날짜기준 검색
    @Query("SELECT o FROM OrderBranch o WHERE o.orderDate BETWEEN :start AND :end order by o.orderDate desc")
    List<OrderBranch> searchAllByOrderDate(Pageable pageable, @Param("start") Date start, @Param("end") Date end);

    @Query("SELECT count(o) FROM OrderBranch o WHERE o.orderDate BETWEEN :start AND :end")
    int orderCountAll(@Param("start") Date start, @Param("end") Date end);



}
