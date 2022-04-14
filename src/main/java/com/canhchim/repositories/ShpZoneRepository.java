package com.canhchim.repositories;

import com.canhchim.models.ShpZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShpZoneRepository extends JpaRepository<ShpZone, Long>
{
    @Query("select s from ShpZone s where s.shop.id = ?1")
    List<ShpZone> findAllZone(Long shopId);

    @Query("select (count(s) > 0) from ShpZone s where s.zoneName = ?1 and s.shop.id = ?2")
    boolean existsByZoneName(String zoneName, Long shopId);

}
