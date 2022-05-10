package com.canhchim.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Entity
@Table(name = "SHP_table", indexes = {
        @Index(name = "zone_id", columnList = "zone_id")
})
public class ShpTable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "table_number", nullable = false, length = 50)
    private String tableName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zone_id", nullable = false)
    @JsonIgnore
    private ShpZone zone;

    @Column(name = "number_of_seat")
    private Integer numberOfSeat;
}
