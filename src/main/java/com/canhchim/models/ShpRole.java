package com.canhchim.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "SHP_Role")
@Setter
@Getter
public class ShpRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "role_name", nullable = false, length = 20)
    private String roleName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "function_code", length = 10)
    private String functionCode;


//    @ManyToMany
//    @JoinTable(name = "SHP_Function_of_role",
//            joinColumns = @JoinColumn(name = "role_id"),
//            inverseJoinColumns = @JoinColumn(name = "func_id"))
//    private Set<ShpFunction> shpFunctions = new LinkedHashSet<>();


    //TODO Reverse Engineering! Migrate other columns to the entity
}
