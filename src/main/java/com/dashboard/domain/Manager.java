package com.dashboard.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "managers")
public class Manager extends User {
    @ManyToMany
    @JoinTable(
            name = "manager_enterprise",
            joinColumns = @JoinColumn(name = "manager_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "enterprise_id", referencedColumnName = "id"))
    private Set<Enterprise> enterprises;
}