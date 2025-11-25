package com.p2f4.med_office.mapper;

import com.p2f4.med_office.entity.MedicalOfficeUpdate;
import com.p2f4.med_office.dto.MedicalOfficeUpdateDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MedicalOfficeUpdateMapper {
    MedicalOfficeUpdateMapper INSTANCE = Mappers.getMapper(MedicalOfficeUpdateMapper.class);
    @Mapping(target = "idOffice", ignore = true)
    @Mapping(target = "idUser", ignore = true)
    MedicalOfficeUpdate toEntity(MedicalOfficeUpdateDTO dto);

    @Mapping(target = "idOffice", source = "idOffice.idOffice")
    @Mapping(target = "idUser", source = "idUser.idUser")
    MedicalOfficeUpdateDTO toDTO(MedicalOfficeUpdate entity);
}
