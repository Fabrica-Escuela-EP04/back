package com.p2f4.med_office.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.p2f4.med_office.config.TestSecurityConfig;
import com.p2f4.med_office.core.ScheduleService;
import com.p2f4.med_office.dto.ScheduleDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for ScheduleController
 * Tests schedule management endpoints
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
@DisplayName("Schedule Controller Tests")
class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockBean
    private ScheduleService scheduleService;

    private ScheduleDTO scheduleDTO;

    @BeforeEach
    void setUp() {
        // Setup ObjectMapper with JavaTimeModule for LocalDate serialization
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        // Setup common test data
        scheduleDTO = new ScheduleDTO();
        scheduleDTO.setIdSchedule(1);
        scheduleDTO.setIdUser(1);
        scheduleDTO.setIdOffice(101);
        scheduleDTO.setType("MAINTENANCE");
        scheduleDTO.setStartDate(LocalDate.now());
        scheduleDTO.setEndDate(LocalDate.now().plusDays(7));
    }

    @Test
    @DisplayName("Should create maintenance schedule successfully")
    void shouldCreateMaintenanceScheduleSuccessfully() throws Exception {
        // Arrange
        when(scheduleService.createMaintenanceSchedule(
            anyInt(), anyString(), anyInt(), any(LocalDate.class), any(LocalDate.class)
        )).thenReturn(scheduleDTO);

        // Act & Assert
        mockMvc.perform(post("/schedules/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scheduleDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idSchedule").value(1))
                .andExpect(jsonPath("$.idUser").value(1))
                .andExpect(jsonPath("$.idOffice").value(101))
                .andExpect(jsonPath("$.type").value("MAINTENANCE"));
    }

    @Test
    @DisplayName("Should create schedule with valid date range")
    void shouldCreateScheduleWithValidDateRange() throws Exception {
        // Arrange
        LocalDate startDate = LocalDate.of(2025, 11, 15);
        LocalDate endDate = LocalDate.of(2025, 11, 22);
        
        scheduleDTO.setStartDate(startDate);
        scheduleDTO.setEndDate(endDate);
        
        when(scheduleService.createMaintenanceSchedule(
            anyInt(), anyString(), anyInt(), any(LocalDate.class), any(LocalDate.class)
        )).thenReturn(scheduleDTO);

        // Act & Assert
        mockMvc.perform(post("/schedules/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scheduleDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idSchedule").value(1))
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.endDate").exists());
    }

    @Test
    @DisplayName("Should create schedule for specific user and office")
    void shouldCreateScheduleForSpecificUserAndOffice() throws Exception {
        // Arrange
        scheduleDTO.setIdUser(5);
        scheduleDTO.setIdOffice(202);
        
        when(scheduleService.createMaintenanceSchedule(
            anyInt(), anyString(), anyInt(), any(LocalDate.class), any(LocalDate.class)
        )).thenReturn(scheduleDTO);

        // Act & Assert
        mockMvc.perform(post("/schedules/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scheduleDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUser").value(5))
                .andExpect(jsonPath("$.idOffice").value(202));
    }
}
