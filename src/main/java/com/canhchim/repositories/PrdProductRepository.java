package com.canhchim.repositories;

import com.canhchim.models.PrdProduct;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PrdProductRepository extends JpaRepository<PrdProduct, Long>
{
    @Query("""
            select p from PrdProduct p
            where p.productName like concat('%', ?1, '%')
            or p.prdCategories.categoryName like concat('%', ?1, '%')""")
    Page<PrdProduct> searchByNameOrCategory(String productName, Pageable pageable);

    @Query("select p from PrdProduct p " +
            "where p.shpShop.id = ?1 and p.productCode = ?2")
    Optional<PrdProduct> searchByProductCode(long shopId, String productCode);

    @Override
    @NotNull
    Page<PrdProduct> findAll(@NotNull Pageable pageable);

    @Query("select p from PrdProduct p where p.prdCategories.id = ?1 and p.shpShop.id = ?2")
    List<PrdProduct> findProductByCatAndShop(Long id, Long id1);

    @Query("select p from PrdProduct p where p.productCode = ?1")
    Optional<PrdProduct> findByCode(String productCode);

    @Query("select p from PrdProduct p where p.prdCategories.id = ?1")
    Page<PrdProduct> findByCategory(Long id, Pageable pageable);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "shpShop", "prdCategories"
            }
    )
    @Query("select p from PrdProduct p where p.shpShop.id = ?1")
    Page<PrdProduct> findByShopId(Long id, Pageable pageable);

    @Query("select count(p) from PrdProduct p where p.prdCategories.shop.id = ?1")
    int countByShopId(Long id);

    @Query("select count(p) from PrdProduct p where p.prdCategories.id = ?1 and p.prdCategories.shop.id = ?2")
    int countByCatAndShop(Long id, Long id1);

    @Query("select (count(p) > 0) from PrdProduct p where p.productName = ?1 and p.shpShop.id = ?2")
    boolean existsByProductName(String productName, long shopId);


}
