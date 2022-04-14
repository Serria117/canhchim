package com.canhchim.models;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "PRD_Category_group")
public class PrdCategoryGroup
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "categoryGroupName", nullable = false, length = 20)
    private String categoryGroupName;

    @OneToMany(mappedBy = "categoryGroup")
    private Set<PrdCategory> prdCategories = new LinkedHashSet<>();
}
