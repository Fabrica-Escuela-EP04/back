package com.p2f4.med_office.entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "medical_offices_update")
@Getter @Setter
public class MedicalOfficeUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modification", nullable = false)
    private Integer idModification;

    @ManyToOne
    @JoinColumn(name = "id_office", insertable = false, updatable = false)
    private MedicalOffice idOffice;

    @ManyToOne
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    private User idUser;
    
    @Column(name = "modification_date", nullable = false)
    private LocalDateTime modificationDate;

    @Column(name = "previous_clinic", nullable = false)
    private String previousClinic;

    @Column(name = "previous_specialty", nullable = false)
    private String previousSpecialty;

    @Column(name = "previous_office_number", nullable = false)
    private Integer previousOfficeNumber;

    @Column(name = "previous_status", nullable = false)
    private String previousStatus;

    @Column(name = "new_clinic", nullable = false)
    private String newClinic;

    @Column(name = "new_specialty", nullable = false)
    private String newSpecialty;

    @Column(name = "new_office_number", nullable = false)
    private Integer newOfficeNumber;

    @Column(name = "new_status", nullable = false)
    private String newStatus;
}
