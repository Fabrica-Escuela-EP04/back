package com.p2f4.med_office.entity;

import lombok.Getter;
import lombok.Setter;

import com.p2f4.med_office.utils.EnumStatus;

import jakarta.persistence.*;

@Table (name = "clinics")
@Entity
@Getter
@Setter
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clinic", nullable = false)
    private Integer idClinic;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumStatus status;

    public Clinic() {
    }
    public Clinic(String name, String type, String location, String phoneNumber, EnumStatus status) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }
}
