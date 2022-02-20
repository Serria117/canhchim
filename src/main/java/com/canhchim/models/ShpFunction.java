package com.canhchim.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "SHP_Function")
@Getter
@Setter
public class ShpFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "function_name", nullable = false, length = 20)
    private String functionName;

    @Column(name = "function_description", length = 100)
    private String functionDescription;

    @ManyToMany(mappedBy = "shpFunctions")
    @JsonIgnore
    private Set<ShpRole> shpRoles = new LinkedHashSet<>();

    //TODO Reverse Engineering! Migrate other columns to the entity
}
