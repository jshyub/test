package com.posco.poscoproject.repository;

import com.posco.poscoproject.dto.OrderDetailWithItemDTO;
import com.posco.poscoproject.entity.Branch;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.annotation.Native;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findByEmail(String email);

    // 당일 매출 합계 -  본사
    @Query("select sum(o.payment) from OrderBranch o where o.orderDate =:date")
    Long getDailySalesAll(@Param("date")Date date);

    // 이번주 매출 합계 - 본사
    @Query(nativeQuery = true, value="SELECT SUM(o.payment) FROM order_branch o WHERE o.order_date BETWEEN DATE_ADD(NOW(), INTERVAL -1 WEEK ) AND NOW()")
    Long getWeekSalesAll();

    // 지난주 매출 합계 - 본사
    @Query(nativeQuery=true, value="SELECT sum(o.payment) FROM order_branch o WHERE o.order_date BETWEEN DATE_ADD(NOW(), INTERVAL -2 WEEK ) AND DATE_ADD(NOW(), INTERVAL -1 WEEK )")
    Long getLastWeekSalesAll();

    // 이번달 매출 합계 - 본사
    @Query(nativeQuery = true, value="SELECT SUM(o.payment) FROM order_branch o WHERE o.order_date BETWEEN DATE_ADD(NOW(), INTERVAL -1 MONTH ) AND NOW()")
    Long getMonthSalesAll();

    // 이번달 매출 합계 - 본사
    @Query(nativeQuery = true, value="SELECT SUM(o.payment) FROM order_branch o WHERE o.order_date BETWEEN DATE_ADD(NOW(), INTERVAL -2 MONTH ) AND DATE_ADD(NOW(), INTERVAL -1 MONTH )")
    Long getLastMonthSalesAll();

    // 모든 지점 가져오기 - 본사
    @Query("select count(b.id) from Branch b")
    int getBranchCount();

    // 일별 매출 - 본사
    @Query(value = "SELECT DATE(o.order_date) as orderDate, SUM(o.payment) as totalSales " +
            "FROM order_branch o " +
            "WHERE o.order_date BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(o.order_date) " +
            "ORDER BY DATE(o.order_date) ASC",
            nativeQuery = true)
    List<Object[]> findDailySalesForLast7Days(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    // 가장 많이 팔린 메뉴 top5
    @Query("SELECT i.name as itemName, SUM(od.quantity) as totalSold " +
            "FROM OrderDetail od " +
            "JOIN od.item i " +
            "GROUP BY i.name " +
            "ORDER BY totalSold DESC")
    List<Object[]> findTop5SoldItems(Pageable pageable);

    // 각 월별 매출액 합계 - 본사
    @Query(nativeQuery = true, value="SELECT MONTH(o.order_date) as month, SUM(o.payment) as totalSales " +
            "FROM order_branch o " +
            "WHERE YEAR(o.order_date) = YEAR(CURRENT_DATE()) " + // 현재 년도의 데이터만 조회합니다.
            "GROUP BY MONTH(o.order_date) " + // 월별로 그룹화합니다.
            "ORDER BY month") // 월별로 정렬합니다.
    List<Object[]> getMonthlySales();

    // 지점 매출 순위 top10
    @Query("SELECT ob.branch.id, ob.branch.name, SUM(ob.payment) " +
            "FROM OrderBranch ob " +
            "GROUP BY ob.branch.id, ob.branch.name " +
            "ORDER BY SUM(ob.payment) DESC")
    List<Object[]> findTop10SalesBranches(Pageable pageable);

    // 일별 매출 - 지점
    @Query(value = "SELECT DATE(o.order_date) AS orderDate, SUM(o.payment) AS totalSales " +
            "FROM order_branch o " +
            "WHERE o.order_date BETWEEN :startDate AND :endDate " +
            "AND o.branch_id = :branchId " +
            "GROUP BY DATE(o.order_date) " +
            "ORDER BY DATE(o.order_date) ASC",
            nativeQuery = true)
    List<Object[]> findDailySalesForLast7DaysByBranch(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("branchId") Long branchId);

    // 가장 많이 팔린 메뉴 top5 - 지점
    @Query("SELECT i.name as itemName, SUM(od.quantity) as totalSold " +
            "FROM OrderDetail od " +
            "JOIN od.item i " +
            "JOIN od.orderBranch ob " +
            "WHERE ob.branch.id = :branchId " + // 특정 지점의 주문만 고려
            "GROUP BY i.name " +
            "ORDER BY totalSold DESC")
    List<Object[]> findTop5SoldItemsByBranch(@Param("branchId") Long branchId, Pageable pageable);


    // 각 월별 매출액 합계 - 지점
    @Query(nativeQuery = true, value="SELECT MONTH(o.order_date) as month, SUM(o.payment) as totalSales " +
            "FROM order_branch o " +
            "WHERE YEAR(o.order_date) = YEAR(CURRENT_DATE()) " + // 현재 년도의 데이터만 조회합니다.
            "AND o.branch_id = :branchId " + // 본인 소속 지점의 데이터만 조회합니다.
            "GROUP BY MONTH(o.order_date) " + // 월별로 그룹화합니다.
            "ORDER BY month") // 월별로 정렬합니다.
    List<Object[]> getMonthlySalesByBranch(@Param("branchId") Long branchId);

    // 당일 매출 합계 -  지점
    @Query("select sum(o.payment) from OrderBranch o where o.orderDate =:date and o.branch.id =:id")
    Long getDailySalesById(@Param("date")Date date, @Param("id")Long id);

    // 이번주 매출 합계 - 지점
    @Query(nativeQuery = true, value="SELECT SUM(o.payment) FROM order_branch o WHERE o.order_date BETWEEN DATE_ADD(NOW(), INTERVAL -1 WEEK ) AND NOW() AND o.branch_id =:id")
    Long getWeekSalesById(@Param("id")Long id);

    // 지난주 매출 합계 - 지점
    @Query(nativeQuery=true, value="SELECT sum(o.payment) FROM order_branch o WHERE o.order_date BETWEEN DATE_ADD(NOW(), INTERVAL -2 WEEK ) AND DATE_ADD(NOW(), INTERVAL -1 WEEK ) and o.branch_id = :id")
    Long getLastWeekSalesById(@Param("id")Long id);

    // 이번달 매출 합계 - 지점
    @Query(nativeQuery = true, value="SELECT SUM(o.payment) FROM order_branch o WHERE o.order_date BETWEEN DATE_ADD(NOW(), INTERVAL -1 MONTH ) AND NOW() and o.branch_id = :id")
    Long getMonthSalesById(@Param("id")Long id);

    // 지난달 매출 합계 - 지점
    @Query(nativeQuery = true, value="SELECT SUM(o.payment) FROM order_branch o WHERE o.order_date BETWEEN DATE_ADD(NOW(), INTERVAL -2 MONTH ) AND DATE_ADD(NOW(), INTERVAL -1 MONTH ) and o.branch_id = :id")
    Long getLastMonthSalesById(@Param("id")Long id);

    // 일일 주문량 - 지점
    @Query(nativeQuery = true, value="SELECT count(o.order_date) FROM order_branch o WHERE date_format(o.order_date,'%Y-%m-%d') = date_format(Now(), '%Y-%m-%d') and o.branch_id = :id")
    int getOrderCountById(@Param("id") Long id);

}
