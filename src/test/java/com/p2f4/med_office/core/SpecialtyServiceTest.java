package com.p2f4.med_office.core;

import com.p2f4.med_office.domain.SpecialtyRepository;
import com.p2f4.med_office.dto.SpecialtyDTO;
import com.p2f4.med_office.entity.Specialty;
import com.p2f4.med_office.mapper.SpecialtyMapper;
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
 * Unit tests for SpecialtyService using JUnit 5 and Mockito.
 * Tests follow AAA (Arrange-Act-Assert) pattern.
 * 
 * @author Test Team
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("SpecialtyService Tests")
class SpecialtyServiceTest {

    @Mock
    private SpecialtyRepository specialtyRepository;

    @Mock
    private SpecialtyMapper specialtyMapper;

    @InjectMocks
    private SpecialtyService specialtyService;

    @Nested
    @DisplayName("Get All Specialties Tests")
    class GetAllSpecialtiesTests {

        @Test
        @DisplayName("Should return list of specialties when specialties exist")
        void shouldReturnListOfSpecialtiesWhenSpecialtiesExist() {
            // Arrange
            Specialty specialty1 = new Specialty();
            specialty1.setIdSpecialty(1);
            specialty1.setSpecialtyName("Cardiology");

            Specialty specialty2 = new Specialty();
            specialty2.setIdSpecialty(2);
            specialty2.setSpecialtyName("Neurology");

            SpecialtyDTO specialtyDTO1 = new SpecialtyDTO();
            specialtyDTO1.setIdSpecialty(1);
            specialtyDTO1.setSpecialtyName("Cardiology");

            SpecialtyDTO specialtyDTO2 = new SpecialtyDTO();
            specialtyDTO2.setIdSpecialty(2);
            specialtyDTO2.setSpecialtyName("Neurology");

            List<Specialty> specialties = Arrays.asList(specialty1, specialty2);

            when(specialtyRepository.findAll()).thenReturn(specialties);
            when(specialtyMapper.toDTO(specialty1)).thenReturn(specialtyDTO1);
            when(specialtyMapper.toDTO(specialty2)).thenReturn(specialtyDTO2);

            // Act
            List<SpecialtyDTO> result = specialtyService.getAllSpecialties();

            // Assert
            assertNotNull(result, "Result should not be null");
            assertEquals(2, result.size(), "Should return 2 specialties");
            assertEquals("Cardiology", result.get(0).getSpecialtyName(), "First specialty name should match");
            assertEquals("Neurology", result.get(1).getSpecialtyName(), "Second specialty name should match");

            verify(specialtyRepository, times(1)).findAll();
            verify(specialtyMapper, times(2)).toDTO(any(Specialty.class));
        }

        @Test
        @DisplayName("Should return empty list when no specialties exist")
        void shouldReturnEmptyListWhenNoSpecialtiesExist() {
            // Arrange
            when(specialtyRepository.findAll()).thenReturn(Collections.emptyList());

            // Act
            List<SpecialtyDTO> result = specialtyService.getAllSpecialties();

            // Assert
            assertNotNull(result, "Result should not be null");
            assertTrue(result.isEmpty(), "Result should be empty");

            verify(specialtyRepository, times(1)).findAll();
            verify(specialtyMapper, never()).toDTO(any(Specialty.class));
        }

        @Test
        @DisplayName("Should handle single specialty correctly")
        void shouldHandleSingleSpecialtyCorrectly() {
            // Arrange
            Specialty specialty = new Specialty();
            specialty.setIdSpecialty(1);
            specialty.setSpecialtyName("Dermatology");

            SpecialtyDTO specialtyDTO = new SpecialtyDTO();
            specialtyDTO.setIdSpecialty(1);
            specialtyDTO.setSpecialtyName("Dermatology");

            when(specialtyRepository.findAll()).thenReturn(Collections.singletonList(specialty));
            when(specialtyMapper.toDTO(specialty)).thenReturn(specialtyDTO);

            // Act
            List<SpecialtyDTO> result = specialtyService.getAllSpecialties();

            // Assert
            assertNotNull(result, "Result should not be null");
            assertEquals(1, result.size(), "Should return 1 specialty");
            assertEquals("Dermatology", result.get(0).getSpecialtyName(), "Specialty name should match");

            verify(specialtyRepository, times(1)).findAll();
            verify(specialtyMapper, times(1)).toDTO(specialty);
        }

        @Test
        @DisplayName("Should handle multiple specialties with different names")
        void shouldHandleMultipleSpecialtiesWithDifferentNames() {
            // Arrange
            Specialty specialty1 = new Specialty();
            specialty1.setIdSpecialty(1);
            specialty1.setSpecialtyName("Pediatrics");

            Specialty specialty2 = new Specialty();
            specialty2.setIdSpecialty(2);
            specialty2.setSpecialtyName("Orthopedics");

            Specialty specialty3 = new Specialty();
            specialty3.setIdSpecialty(3);
            specialty3.setSpecialtyName("Psychiatry");

            SpecialtyDTO specialtyDTO1 = new SpecialtyDTO();
            specialtyDTO1.setIdSpecialty(1);
            specialtyDTO1.setSpecialtyName("Pediatrics");

            SpecialtyDTO specialtyDTO2 = new SpecialtyDTO();
            specialtyDTO2.setIdSpecialty(2);
            specialtyDTO2.setSpecialtyName("Orthopedics");

            SpecialtyDTO specialtyDTO3 = new SpecialtyDTO();
            specialtyDTO3.setIdSpecialty(3);
            specialtyDTO3.setSpecialtyName("Psychiatry");

            List<Specialty> specialties = Arrays.asList(specialty1, specialty2, specialty3);

            when(specialtyRepository.findAll()).thenReturn(specialties);
            when(specialtyMapper.toDTO(specialty1)).thenReturn(specialtyDTO1);
            when(specialtyMapper.toDTO(specialty2)).thenReturn(specialtyDTO2);
            when(specialtyMapper.toDTO(specialty3)).thenReturn(specialtyDTO3);

            // Act
            List<SpecialtyDTO> result = specialtyService.getAllSpecialties();

            // Assert
            assertNotNull(result, "Result should not be null");
            assertEquals(3, result.size(), "Should return 3 specialties");
            assertEquals("Pediatrics", result.get(0).getSpecialtyName(), "First specialty name should match");
            assertEquals("Orthopedics", result.get(1).getSpecialtyName(), "Second specialty name should match");
            assertEquals("Psychiatry", result.get(2).getSpecialtyName(), "Third specialty name should match");

            verify(specialtyRepository, times(1)).findAll();
            verify(specialtyMapper, times(3)).toDTO(any(Specialty.class));
        }
    }
}
