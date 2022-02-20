package com.canhchim.repositories;

import com.canhchim.models.PrdProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PrdProductRepository extends PagingAndSortingRepository<PrdProduct, Long> {
    @Query("""
            select p from PrdProduct p
            where p.productName like concat('%', ?1, '%') or p.prdCategories.categoryName like concat('%', ?1, '%')""")
    Page<PrdProduct> searchByNameOrCategory(String productName, Pageable pageable);

    @Override
    Page<PrdProduct> findAll(Pageable pageable);

    @Query("select p from PrdProduct p where p.prdCategories.id = ?1")
    Page<PrdProduct> findByCategory(Long id, Pageable pageable);

    @Query("select p from PrdProduct p where p.shpShop.id = ?1")
    Page<PrdProduct> findByShopId(Long id, Pageable pageable);


}
