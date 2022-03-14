package com.canhchim.repositories;

import com.canhchim.models.PrdCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrdCategoryRepository extends JpaRepository<PrdCategory, Long>
{
    @Query("select p from PrdCategory p where p.categoryName like concat('%', ?1, '%')")
    List<PrdCategory> searchByCategoryName(String categoryName);
}
