package com.p2f4.med_office.mapper;

import com.p2f4.med_office.entity.Clinic;
import com.p2f4.med_office.dto.ClinicDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel ="spring")
public interface ClinicMapper {
    ClinicMapper INSTANCE = Mappers.getMapper(ClinicMapper.class);
    ClinicDTO toDTO(Clinic clinic);
    Clinic toEntity(ClinicDTO clinicDTO);
    
}
