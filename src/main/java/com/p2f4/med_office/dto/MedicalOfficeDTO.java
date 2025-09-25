package com.p2f4.med_office.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MedicalOfficeDTO {
    // Fields
    private Integer idOffice;
    @NotNull @Positive private Integer idClinic;
    @NotNull @Positive private Integer idSpecialty;
    @NotNull @Positive private Integer officeNumber;
    @NotBlank private String status;

    // Constructors
    public MedicalOfficeDTO() {
    }
    public MedicalOfficeDTO(
        Integer idOffice, Integer idClinic, Integer idSpecialty, Integer officeNumber, String status) {
            
        this.idOffice = idOffice;
        this.idClinic = idClinic;
        this.idSpecialty = idSpecialty;
        this.officeNumber = officeNumber;
        this.status = status;
    }

    // Getters and Setters
    public Integer getIdOffice() {
        return idOffice;
    }

    public void setIdOffice(Integer idOffice) {
        this.idOffice = idOffice;
    }

    public Integer getIdClinic() {
        return idClinic;
    }

    public void setIdClinic(Integer idClinic) {
        this.idClinic = idClinic;
    }

    public Integer getIdSpecialty() {
        return idSpecialty;
    }

    public void setIdSpecialty(Integer idSpecialty) {
        this.idSpecialty = idSpecialty;
    }

    public Integer getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(Integer officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
