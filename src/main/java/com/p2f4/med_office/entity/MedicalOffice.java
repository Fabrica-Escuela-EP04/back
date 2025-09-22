package com.p2f4.med_office.entity;

import lombok.Data;
import jakarta.persistence.*;

@Table (name = "medical_office")
@Entity
@Data
public class MedicalOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_office", nullable = false)
    private Integer idOffice;

    @Column(name = "id_clinic", nullable = false)
    private Integer idClinic;

    @Column(name = "id_specialty", nullable = false)
    private Integer idSpecialty;

    @Column(name = "office_number", nullable = false)
    private Integer officeNumber;

    @Column(name = "status", nullable = false)
    private String status;

    /*
     * RELATIONSHIPS
     */
    @ManyToOne
    @JoinColumn(name = "id_clinic", insertable = false, updatable = false)
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "id_specialty", insertable = false, updatable = false)
    private Specialty specialty;
}
