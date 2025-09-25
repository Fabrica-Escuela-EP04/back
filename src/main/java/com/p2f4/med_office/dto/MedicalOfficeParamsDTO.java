package com.p2f4.med_office.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalOfficeParamsDTO {
    private String clinicName;
    private String specialtyName;
    private Integer officeNumber;
    private String status;

    public MedicalOfficeParamsDTO() {
    }

    public MedicalOfficeParamsDTO(String clinicName, String specialtyName, Integer officeNumber, String status) {
        this.clinicName = clinicName;
        this.specialtyName = specialtyName;
        this.officeNumber = officeNumber;
        this.status = status;
    }

}
