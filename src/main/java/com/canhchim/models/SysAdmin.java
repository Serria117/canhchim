package com.canhchim.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "SYS_Admin")
public class SysAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //TODO Reverse Engineering! Migrate other columns to the entity
}
