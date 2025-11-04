package com.p2f4.med_office.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalOfficeDataUpdatableDTO {
    private Integer officeNumber;
    private String ClinicName;
    private String SpecialtyName;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;


    public MedicalOfficeDataUpdatableDTO() {
    }

    public MedicalOfficeDataUpdatableDTO(Integer officeNumber, String ClinicName, String SpecialtyName, String status, LocalDate startDate, LocalDate endDate) {
        this.officeNumber = officeNumber;
        this.ClinicName = ClinicName;
        this.SpecialtyName = SpecialtyName;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
