package com.p2f4.med_office.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalInformationDTO {
    private List<ClinicDTO> clinics;
    private List<SpecialtyDTO> specialties;

    public MedicalInformationDTO() {
    }

    public MedicalInformationDTO(List<ClinicDTO> clinics, List<SpecialtyDTO> specialties) {
        this.clinics = clinics;
        this.specialties = specialties;
    }

}
