package com.canhchim.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "PRD_Categories")
@Getter
@Setter
public class PrdCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;

    @Column(name = "category_code", nullable = false)
    private Long categoryCode;

    @Column(name = "parent_category_code", nullable = false)
    private Long parentCategoryCode;

    @Column(name = "category_group_id", nullable = false)
    private Long categoryGroupId;

    @Column(name = "category_comment", nullable = false, length = 100)
    private String categoryComment;

    @OneToMany(mappedBy = "prdCategories")
    private Set<PrdProduct> prdProducts = new LinkedHashSet<>();

    //TODO Reverse Engineering! Migrate other columns to the entity
}
