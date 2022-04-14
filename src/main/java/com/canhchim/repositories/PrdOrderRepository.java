package com.canhchim.repositories;

import com.canhchim.models.PrdOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PrdOrderRepository extends JpaRepository<PrdOrder, Long>
{
    @Query("select p from PrdOrder p where p.shop.id = ?1 and p.orderDate1 >= ?2 and p.orderDate1 <= ?3")
    List<PrdOrder> findByDate(Long shopId, LocalDateTime fromDate, LocalDateTime toDate);
}
