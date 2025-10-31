package com.p2f4.med_office.mapper;

import com.p2f4.med_office.entity.Schedule;
import com.p2f4.med_office.dto.ScheduleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;




@Mapper(componentModel ="spring")
public interface ScheduleMapper {
    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);
    @Mapping(target = "medicalOffice", ignore = true)
    @Mapping(target = "user", ignore = true)
    Schedule toEntity(ScheduleDTO scheduleDTO);

    ScheduleDTO toDTO(Schedule schedule);
}
