package com.p2f4.med_office.mapper;

import com.p2f4.med_office.entity.MedicalOffice;
import com.p2f4.med_office.dto.MedicalOfficeDTO;
import com.p2f4.med_office.dto.MedicalOfficeParamsDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring")
public interface MedicalOfficeMapper {
    @Mapping(target = "clinic", ignore = true)
    @Mapping(target = "specialty", ignore = true)
    MedicalOffice toEntity(MedicalOfficeDTO dto);


    MedicalOfficeDTO toDTO(MedicalOffice entity);

    // Mapping to MedicalOfficeParamsDTO
    @Mapping(target = "clinicName", source = "clinic.name")
    @Mapping(target = "specialtyName", source = "specialty.specialtyName")
    MedicalOfficeParamsDTO toParamsDTO(MedicalOffice entity);

}
