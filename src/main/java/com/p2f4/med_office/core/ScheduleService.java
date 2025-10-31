package com.p2f4.med_office.core;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.p2f4.med_office.dto.ScheduleDTO;
import com.p2f4.med_office.mapper.ScheduleMapper;
import com.p2f4.med_office.domain.ScheduleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ScheduleService {
     private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    } 
    // Creates a maintenance schedule
    public ScheduleDTO createMaintenanceSchedule(Integer idUser, String type, Integer idOffice, LocalDateTime startDate, LocalDateTime endDate) {
        // Validation Date not null and startDate before endDate
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("La fecha de finalización no puede ser anterior a la fecha de inicio");
        }

        // Create and return the ScheduleDTO
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setIdUser(idUser);
        scheduleDTO.setType(type);
        scheduleDTO.setIdOffice(idOffice);
        scheduleDTO.setStartDate(startDate);
        scheduleDTO.setEndDate(endDate);
        //Mapping DTO to Entity
        var entity = ScheduleMapper.INSTANCE.toEntity(scheduleDTO);
        scheduleRepository.save(entity);

        return ScheduleMapper.INSTANCE.toDTO(entity);
    }
}
