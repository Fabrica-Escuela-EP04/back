package com.p2f4.med_office.core;

import com.p2f4.med_office.domain.ClinicRepository;
import com.p2f4.med_office.dto.ClinicDTO;
import com.p2f4.med_office.entity.Clinic;
import com.p2f4.med_office.mapper.ClinicMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ClinicService using JUnit 5 and Mockito.
 * Tests follow AAA (Arrange-Act-Assert) pattern.
 * 
 * @author Test Team
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ClinicService Tests")
class ClinicServiceTest {

    @Mock
    private ClinicRepository clinicRepository;

    @Mock
    private ClinicMapper clinicMapper;

    @InjectMocks
    private ClinicService clinicService;

    @Nested
    @DisplayName("Get All Clinics Tests")
    class GetAllClinicsTests {

        @Test
        @DisplayName("Should return list of clinics when clinics exist")
        void shouldReturnListOfClinicsWhenClinicsExist() {
            // Arrange
            Clinic clinic1 = new Clinic();
            clinic1.setIdClinic(1);
            clinic1.setName("Clinic 1");
            clinic1.setLocation("Location 1");

            Clinic clinic2 = new Clinic();
            clinic2.setIdClinic(2);
            clinic2.setName("Clinic 2");
            clinic2.setLocation("Location 2");

            ClinicDTO clinicDTO1 = new ClinicDTO();
            clinicDTO1.setIdClinic(1);
            clinicDTO1.setName("Clinic 1");
            clinicDTO1.setLocation("Location 1");

            ClinicDTO clinicDTO2 = new ClinicDTO();
            clinicDTO2.setIdClinic(2);
            clinicDTO2.setName("Clinic 2");
            clinicDTO2.setLocation("Location 2");

            List<Clinic> clinics = Arrays.asList(clinic1, clinic2);

            when(clinicRepository.findAll()).thenReturn(clinics);
            when(clinicMapper.toDTO(clinic1)).thenReturn(clinicDTO1);
            when(clinicMapper.toDTO(clinic2)).thenReturn(clinicDTO2);

            // Act
            List<ClinicDTO> result = clinicService.getAllClinics();

            // Assert
            assertNotNull(result, "Result should not be null");
            assertEquals(2, result.size(), "Should return 2 clinics");
            assertEquals("Clinic 1", result.get(0).getName(), "First clinic name should match");
            assertEquals("Clinic 2", result.get(1).getName(), "Second clinic name should match");

            verify(clinicRepository, times(1)).findAll();
            verify(clinicMapper, times(2)).toDTO(any(Clinic.class));
        }

        @Test
        @DisplayName("Should return empty list when no clinics exist")
        void shouldReturnEmptyListWhenNoClinicsExist() {
            // Arrange
            when(clinicRepository.findAll()).thenReturn(Collections.emptyList());

            // Act
            List<ClinicDTO> result = clinicService.getAllClinics();

            // Assert
            assertNotNull(result, "Result should not be null");
            assertTrue(result.isEmpty(), "Result should be empty");

            verify(clinicRepository, times(1)).findAll();
            verify(clinicMapper, never()).toDTO(any(Clinic.class));
        }

        @Test
        @DisplayName("Should handle single clinic correctly")
        void shouldHandleSingleClinicCorrectly() {
            // Arrange
            Clinic clinic = new Clinic();
            clinic.setIdClinic(1);
            clinic.setName("Single Clinic");
            clinic.setLocation("Single Location");

            ClinicDTO clinicDTO = new ClinicDTO();
            clinicDTO.setIdClinic(1);
            clinicDTO.setName("Single Clinic");
            clinicDTO.setLocation("Single Location");

            when(clinicRepository.findAll()).thenReturn(Collections.singletonList(clinic));
            when(clinicMapper.toDTO(clinic)).thenReturn(clinicDTO);

            // Act
            List<ClinicDTO> result = clinicService.getAllClinics();

            // Assert
            assertNotNull(result, "Result should not be null");
            assertEquals(1, result.size(), "Should return 1 clinic");
            assertEquals("Single Clinic", result.get(0).getName(), "Clinic name should match");

            verify(clinicRepository, times(1)).findAll();
            verify(clinicMapper, times(1)).toDTO(clinic);
        }
    }
}
