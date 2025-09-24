package com.p2f4.med_office.mapper;

import com.p2f4.med_office.entity.MedicalOffice;
import com.p2f4.med_office.dto.MedicalOfficeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface MedicalOfficeMapper {
    MedicalOfficeMapper INSTANCE = Mappers.getMapper(MedicalOfficeMapper.class);
    MedicalOfficeDTO toDTO(MedicalOffice medicalOffice);
    MedicalOffice toEntity(MedicalOfficeDTO medicalOfficeDTO);
}
