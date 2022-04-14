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
@NamedEntityGraph(
        name = "graph.category.product",
        attributeNodes = {@NamedAttributeNode("prdProducts")}
)
public class PrdCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;

    @Column(name = "category_code", nullable = false)
    private String categoryCode;

    @Column(name = "parent_category_code", nullable = false)
    private Long parentCategoryCode;

    @Column(name = "category_comment", nullable = false, length = 100)
    private String categoryComment;

    @OneToMany(mappedBy = "prdCategories")
    private Set<PrdProduct> prdProducts = new LinkedHashSet<>();

    @Column(name = "category_image")
    private String categoryImage;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shopId", nullable = false)
    private ShpShop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_group_id")
    private PrdCategoryGroup categoryGroup;

    //TODO Reverse Engineering! Migrate other columns to the entity
}
