package com.canhchim.repositories;

import com.canhchim.models.ShpTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShpTableRepository extends JpaRepository<ShpTable, Long>
{
    @Query("select s from ShpTable s where s.tableName = ?1")
    ShpTable findByTableNumber(String tableNumber);

    @Query("select (count(s) > 0) from ShpTable s where s.tableName = ?1")
    boolean existsByTableNumber(String tableNumber);

    @Query("select (count(s) > 0) from ShpTable s where s.tableName = ?1 and s.zone.id = ?2")
    boolean existsByTableNumber(String tableNumber, Long zoneId);


}
