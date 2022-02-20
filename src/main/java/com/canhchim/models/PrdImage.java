package com.canhchim.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PRD_Images")
@Getter
@Setter
public class PrdImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private PrdProduct product;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "image_group", length = 100)
    private String imageGroup;

     //TODO Reverse Engineering! Migrate other columns to the entity
}
