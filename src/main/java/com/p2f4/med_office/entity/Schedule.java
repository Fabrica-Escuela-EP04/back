package com.p2f4.med_office.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import com.p2f4.med_office.utils.EnumStatusSchedule;

@Getter
@Setter
@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_schedule")
    private Integer idSchedule;

    @Column(name = "id_user", nullable = false)
    private Integer idUser;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "id_office", nullable = false)
    private Integer idOffice;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumStatusSchedule status;

    // Relations

    @ManyToOne
    @JoinColumn(name ="id_user", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_office", insertable = false, updatable = false)
    private MedicalOffice medicalOffice;

}
