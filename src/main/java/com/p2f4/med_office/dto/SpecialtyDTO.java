package com.p2f4.med_office.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecialtyDTO {
    private Integer idSpecialty;
    private String specialtyName;
    
    public SpecialtyDTO() {
    }

    public SpecialtyDTO(Integer idSpecialty, String specialtyName) {
        this.idSpecialty = idSpecialty;
        this.specialtyName = specialtyName;
    }

}
