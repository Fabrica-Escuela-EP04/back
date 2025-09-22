package com.p2f4.med_office.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_schedule")
    private Long idSchedule;

    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "id_office", nullable = false)
    private Integer idOffice;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    // Relations

    /*
    @ManyToOne
    @JoinColumn(name ="id_user", insertable = false, updatable = false)
    private User user; */

    @ManyToOne
    @JoinColumn(name = "id_office", insertable = false, updatable = false)
    private MedicalOffice medicalOffice;

}
