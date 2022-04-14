package com.canhchim.repositories;

import com.canhchim.models.PrdCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrdCategoryRepository extends JpaRepository<PrdCategory, Long>
{
    @Query("select p from PrdCategory p where p.categoryName like concat('%', ?1, '%')")
    List<PrdCategory> searchByCategoryName(String categoryName);

    @Query("select p from PrdCategory p where p.shop.id = ?1")
    Page<PrdCategory> findCategoryByShopId(Long id, Pageable pageable);



    @Query("select p from PrdCategory p where p.categoryName like concat('%', ?1, '%')")
    Page<PrdCategory> findByName(String categoryName, Pageable pageable);

    @Query("select (count(p) > 0) from PrdCategory p where p.categoryName = ?1")
    boolean existsByCategoryName (String categoryName);

    @Query("""
            select (count(p) > 0) from PrdCategory p
            where upper(p.categoryName) = upper(?1) and upper(p.shop.id) = upper(?2)""")
    boolean existsByCategoryNameAndShop(String categoryName, Long id);



    @Query("select count(p) from PrdCategory p where p.shop.id = ?1")
    int countByShop_Id (Long id);



}
