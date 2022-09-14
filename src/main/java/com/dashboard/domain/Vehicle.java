package com.dashboard.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.*;


@Entity
@Getter
@Setter
@Table(name = "vehicles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "model_id", referencedColumnName = "id", nullable = false)
    private Model model;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enterprise_id", referencedColumnName = "id", nullable = false)
    private Enterprise enterprise;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle")
    private Set<Driver> drivers;

    @Column(nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime purchaseDateTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle")
    private Set<GPSPoint> points;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle")
    private Set<Trip> trips;
}