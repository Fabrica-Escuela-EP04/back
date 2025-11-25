package com.p2f4.med_office.core;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.p2f4.med_office.dto.ScheduleDTO;
import com.p2f4.med_office.mapper.ScheduleMapper;
import com.p2f4.med_office.utils.EnumStatusSchedule;
import com.p2f4.med_office.utils.NonCoungruentDatesException;
import com.p2f4.med_office.utils.NullDateException;
import com.p2f4.med_office.domain.ScheduleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ScheduleService {
     private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    } 

    // Get all schedules
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleMapper.INSTANCE::toDTO)
                .toList();
    }
    // Creates a maintenance schedule
    public ScheduleDTO createMaintenanceSchedule(Integer idUser, Integer idOffice, LocalDate startDate, LocalDate endDate) {

        // Validation Date not null and startDate before endDate
        if (startDate == null || endDate == null) {
            throw new NullDateException();
        }
        if (endDate.isBefore(startDate)) {
            throw new NonCoungruentDatesException();
        }
        // Set maintenance schedule data
        String type = "MANTENIMIENTO";
        String status = EnumStatusSchedule.PROGRAMADO.name();

        // Create and return the ScheduleDTO
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setIdUser(idUser);
        scheduleDTO.setType(type);
        scheduleDTO.setIdOffice(idOffice);
        scheduleDTO.setStartDate(startDate);
        scheduleDTO.setEndDate(endDate);
        scheduleDTO.setStatus(status);
        //Mapping DTO to Entity
        var entity = ScheduleMapper.INSTANCE.toEntity(scheduleDTO);
        scheduleRepository.save(entity);

        return ScheduleMapper.INSTANCE.toDTO(entity);
    }
}
