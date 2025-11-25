package com.p2f4.med_office.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p2f4.med_office.core.*;
import com.p2f4.med_office.dto.ScheduleDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/create")
    public ResponseEntity<ScheduleDTO> createSchedule(
           @Valid @RequestBody ScheduleDTO request) {

        ScheduleDTO created = scheduleService.createMaintenanceSchedule(
                request.getIdUser(),
                request.getIdOffice(),
                request.getStartDate(),
                request.getEndDate()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
