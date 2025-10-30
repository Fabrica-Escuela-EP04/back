package com.p2f4.med_office.core;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.p2f4.med_office.dto.ScheduleDTO;

//import com.p2f4.med_office.domain.ScheduleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ScheduleService {
   /*  private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    } */
    // Creates a schedule
    public ScheduleDTO createSchedule(Long idUser, String type, int idOffice, LocalDateTime startDate, LocalDateTime endDate) {
        // Business logic for creating a schedule
        return new ScheduleDTO();
    }

}
