package com.p2f4.med_office.entity;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Table(name = "user_role")
@Entity
@Data

public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role", nullable = false)
    private Integer idRole;

    @Column(name = "name", nullable = false)
    private String name;

}
