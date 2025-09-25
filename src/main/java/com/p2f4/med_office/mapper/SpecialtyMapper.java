package com.p2f4.med_office.mapper;

import com.p2f4.med_office.entity.Specialty;
import com.p2f4.med_office.dto.SpecialtyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel ="spring")
public interface SpecialtyMapper {
    SpecialtyMapper INSTANCE = Mappers.getMapper(SpecialtyMapper.class);
    SpecialtyDTO toDTO(Specialty specialty);
    
    @Mapping(target = "users", ignore = true)
    Specialty toEntity(SpecialtyDTO specialtyDTO);

}
