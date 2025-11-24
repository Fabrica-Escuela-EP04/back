package com.p2f4.med_office.mapper;

import com.p2f4.med_office.entity.MedicalOfficeUpdate;
import com.p2f4.med_office.dto.MedicalOfficeUpdateDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicalOfficeUpdateMapper {
    @Mapping(target = "idOffice", ignore = true)
    @Mapping(target = "idUser", ignore = true)
    MedicalOfficeUpdate toEntity(MedicalOfficeUpdateDTO dto);

    @Mapping(target = "idOffice", source = "idOffice.idOffice")
    @Mapping(target = "idUser", source = "idUser.idUser")
    MedicalOfficeUpdateDTO toDTO(MedicalOfficeUpdate entity);
}
