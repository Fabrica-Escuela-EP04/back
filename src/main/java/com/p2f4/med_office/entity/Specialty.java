package com.p2f4.med_office.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "specialties")
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_specialty")
    private Integer idSpecialty;

    @Column(name="specialty_name", nullable = false)
    private String specialtyName;

    // Relationships
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "specialties")
    private Set<User> users;

}
