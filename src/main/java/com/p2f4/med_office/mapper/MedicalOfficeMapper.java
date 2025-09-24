package com.p2f4.med_office.mapper;

import com.p2f4.med_office.entity.MedicalOffice;
import com.p2f4.med_office.dto.MedicalOfficeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring")
public interface MedicalOfficeMapper {
    @Mapping(target = "clinic", ignore = true)
    @Mapping(target = "specialty", ignore = true)
    MedicalOffice toEntity(MedicalOfficeDTO dto);

    // Mapping only the foreign key IDs
    @Mapping(target = "idClinic", source = "clinic.idClinic")
    @Mapping(target = "idSpecialty", source = "specialty.idSpecialty")
    MedicalOfficeDTO toDTO(MedicalOffice entity);
}
