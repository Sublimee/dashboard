package com.dashboard.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "models")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Model {

    @Id
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Brand brand;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "tank_capacity_in_liters", nullable = false)
    private Double tankCapacityInLiters;

    @Column(name = "load_capacity_int_tons", nullable = false)
    private Double loadCapacityInTons;

    @Column(nullable = false)
    private int seats;
}