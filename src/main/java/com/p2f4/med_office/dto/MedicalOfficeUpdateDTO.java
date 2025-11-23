package com.p2f4.med_office.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalOfficeUpdateDTO {
    // Fields
    private Integer idModification;
    @NotNull @Positive private Integer idOffice;
    @NotNull @Positive private Integer idUser;
    @NotNull private LocalDateTime modificationDate;
    @NotNull private String previousClinic;
    @NotNull private String previousSpecialty;
    @NotNull @Positive private Integer previousOfficeNumber;
    @NotNull private String previousStatus;
    @NotNull private String newClinic;
    @NotNull private String newSpecialty;
    @NotNull @Positive private Integer newOfficeNumber;
    @NotNull private String newStatus;

    // Constructors
    public MedicalOfficeUpdateDTO() {
    }
    public MedicalOfficeUpdateDTO(
        Integer idModification,
        Integer idOffice,
        Integer idUser,
        LocalDateTime modificationDate,
        String previousClinic,
        String previousSpecialty,
        Integer previousOfficeNumber,
        String previousStatus,
        String newClinic,
        String newSpecialty,
        Integer newOfficeNumber,
        String newStatus) {
            
        this.idModification = idModification;
        this.idOffice = idOffice;
        this.idUser = idUser;
        this.modificationDate = modificationDate;
        this.previousClinic = previousClinic;
        this.previousSpecialty = previousSpecialty;
        this.previousOfficeNumber = previousOfficeNumber;
        this.previousStatus = previousStatus;
        this.newClinic = newClinic;
        this.newSpecialty = newSpecialty;
        this.newOfficeNumber = newOfficeNumber;
        this.newStatus = newStatus;
    }
}

