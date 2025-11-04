package com.p2f4.med_office.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalOfficeParamsDTO {
    private Integer idOffice;
    private String clinicName;
    private String specialtyName;
    private Integer officeNumber;
    private String status;

    public MedicalOfficeParamsDTO() {
    }

    public MedicalOfficeParamsDTO(Integer idOffice, String clinicName, String specialtyName, Integer officeNumber, String status) {
        this.idOffice = idOffice;
        this.clinicName = clinicName;
        this.specialtyName = specialtyName;
        this.officeNumber = officeNumber;
        this.status = status;
    }

}
