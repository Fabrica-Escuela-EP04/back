package com.p2f4.med_office.entity;

import com.p2f4.med_office.utils.EnumStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "medical_offices")
@Getter @Setter
public class MedicalOffice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_office", nullable = false)
  private Integer idOffice;

  @Column(name = "id_clinic", nullable = false)
  private Integer idClinic;

  @Column(name = "id_specialty", nullable = false)
  private Integer idSpecialty;

  @ManyToOne
  @JoinColumn(name = "id_clinic", insertable = false, updatable = false)
  private Clinic clinic;

  @ManyToOne
  @JoinColumn(name = "id_specialty", insertable = false, updatable = false)
  private Specialty specialty;

  @Column(name = "office_number", nullable = false)
  private Integer officeNumber;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private EnumStatus status;
}

