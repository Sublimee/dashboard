package com.dashboard.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "enterprises")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enterprise {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String city;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enterprise")
    private Set<Driver> drivers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enterprise")
    private Set<Vehicle> vehicles;
    @ManyToMany(mappedBy = "enterprises")
    private Set<Manager> managers;
    @Column(nullable = false)
    private TimeZone timeZone;
}