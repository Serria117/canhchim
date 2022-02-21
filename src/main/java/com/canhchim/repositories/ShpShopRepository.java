package com.canhchim.repositories;

import com.canhchim.models.ShpShop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShpShopRepository extends JpaRepository<ShpShop, Long> {
    @Query("select s from ShpShop s where s.shopName = ?1")
    Page<ShpShop> findByShopName(String shopName, Pageable pageable);
}
