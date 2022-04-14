package com.canhchim.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "SHP_zone")
public class ShpZone
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "zone_name", nullable = false, length = 20)
    private String zoneName;

    @OneToMany(mappedBy = "zone", fetch = FetchType.EAGER)
    private Set<ShpTable> shpTables = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    @JsonIgnore
    private ShpShop shop;
}
