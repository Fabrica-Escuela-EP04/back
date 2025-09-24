package com.p2f4.med_office.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.util.Set;

@Table(name = "users")
@Entity
@Data

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Integer idUser;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "document", nullable = false)
    private String document;

    @Column(name = "document_type", nullable = false)
    private String documentType;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "id_role")
    private Short idRole;

    /*
     * RELATIONSHIPS
     */
    @ManyToOne
    @JoinColumn(name = "id_role", insertable = false, updatable = false)
    private UserRole userRole;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "doctor_specialty", 
        joinColumns = @JoinColumn(name ="id_user"),
        inverseJoinColumns = @JoinColumn(name = "id_specialty")
    )
    private Set<Specialty> specialties;

}
