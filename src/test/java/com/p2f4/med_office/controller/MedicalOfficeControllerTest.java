package com.p2f4.med_office.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.p2f4.med_office.config.TestSecurityConfig;
import com.p2f4.med_office.core.ClinicService;
import com.p2f4.med_office.core.MedicalOfficeService;
import com.p2f4.med_office.core.ScheduleService;
import com.p2f4.med_office.core.SpecialtyService;
import com.p2f4.med_office.dto.*;
import com.p2f4.med_office.entity.Clinic;
import com.p2f4.med_office.entity.MedicalOffice;
import com.p2f4.med_office.entity.Specialty;
import com.p2f4.med_office.utils.EnumStatus;

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

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for MedicalOfficeController
 * Tests all medical office management endpoints
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
@DisplayName("Medical Office Controller Tests")
class MedicalOfficeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MedicalOfficeService medicalOfficeService;

    @MockBean
    private SpecialtyService specialtyService;

    @MockBean
    private ClinicService clinicService;

    @MockBean
    private ScheduleService scheduleService;

    private ClinicDTO clinicDTO;
    private SpecialtyDTO specialtyDTO;
    private MedicalOfficeDTO medicalOfficeDTO;
    private MedicalOfficeParamsDTO medicalOfficeParamsDTO;

    @BeforeEach
    void setUp() {
        // Setup common test data
        clinicDTO = new ClinicDTO();
        clinicDTO.setIdClinic(1);
        clinicDTO.setName("Clinica Central");
        clinicDTO.setStatus("ACTIVE");

        specialtyDTO = new SpecialtyDTO();
        specialtyDTO.setIdSpecialty(1);
        specialtyDTO.setSpecialtyName("Cardiologia");

        medicalOfficeDTO = new MedicalOfficeDTO();
        medicalOfficeDTO.setIdOffice(1);
        medicalOfficeDTO.setOfficeNumber(101);
        medicalOfficeDTO.setIdClinic(1);
        medicalOfficeDTO.setIdSpecialty(1);
        medicalOfficeDTO.setStatus("ACTIVE");

        medicalOfficeParamsDTO = new MedicalOfficeParamsDTO();
        medicalOfficeParamsDTO.setIdOffice(1);
        medicalOfficeParamsDTO.setOfficeNumber(101);
        medicalOfficeParamsDTO.setClinicName("Clinica Central");
        medicalOfficeParamsDTO.setSpecialtyName("Cardiologia");
        medicalOfficeParamsDTO.setStatus("ACTIVE");
    }

    @Test
    @DisplayName("Should return clinics and specialties successfully")
    void shouldReturnClinicsAndSpecialtiesSuccessfully() throws Exception {
        // Arrange
        List<ClinicDTO> clinics = Arrays.asList(clinicDTO);
        List<SpecialtyDTO> specialties = Arrays.asList(specialtyDTO);
        
        when(clinicService.getAllClinics()).thenReturn(clinics);
        when(specialtyService.getAllSpecialties()).thenReturn(specialties);

        // Act & Assert
        mockMvc.perform(get("/api/medical-offices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clinics").isArray())
                .andExpect(jsonPath("$.clinics[0].idClinic").value(1))
                .andExpect(jsonPath("$.specialties").isArray())
                .andExpect(jsonPath("$.specialties[0].idSpecialty").value(1));
    }

    @Test
    @DisplayName("Should create medical office successfully")
    void shouldCreateMedicalOfficeSuccessfully() throws Exception {
        // Arrange
        when(medicalOfficeService.createMedicalOffice(anyInt(), anyInt(), anyInt(), anyString()))
            .thenReturn(medicalOfficeDTO);

        // Act & Assert
        mockMvc.perform(post("/api/medical-offices/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medicalOfficeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idOffice").value(1))
                .andExpect(jsonPath("$.officeNumber").value(101));
    }

    @Test
    @DisplayName("Should create medical office by name successfully")
    void shouldCreateMedicalOfficeByNameSuccessfully() throws Exception {
        // Arrange
        when(medicalOfficeService.createMedicalOffice(anyInt(), anyString(), anyString(), anyString()))
            .thenReturn(medicalOfficeDTO);

        // Act & Assert
        mockMvc.perform(post("/api/medical-offices/create-by-name")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medicalOfficeParamsDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idOffice").value(1));
    }

    @Test
    @DisplayName("Should deactivate medical office successfully")
    void shouldDeactivateMedicalOfficeSuccessfully() throws Exception {
        // Arrange
        MedicalOffice office = new MedicalOffice();
        office.setIdOffice(1);
        office.setOfficeNumber(101);
        office.setStatus(EnumStatus.INACTIVO);
        
        Clinic clinic = new Clinic();
        clinic.setName("Clinica Central");
        office.setClinic(clinic);
        
        Specialty specialty = new Specialty();
        specialty.setSpecialtyName("Cardiologia");
        office.setSpecialty(specialty);
        
        when(medicalOfficeService.deactivateMedicalOffice(1)).thenReturn(office);

        // Act & Assert
        mockMvc.perform(delete("/api/medical-offices/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idOffice").value(1))
                .andExpect(jsonPath("$.officeNumber").value(101));
    }
}
