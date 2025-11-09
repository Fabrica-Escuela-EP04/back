package com.p2f4.med_office.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SpecialtyDTO.
 * Tests follow AAA (Arrange-Act-Assert) pattern.
 * 
 * @author Test Team
 * @version 1.0
 */
@DisplayName("SpecialtyDTO Tests")
class SpecialtyDTOTest {

    @Test
    @DisplayName("Should create SpecialtyDTO with no-args constructor")
    void shouldCreateSpecialtyDTOWithNoArgsConstructor() {
        // Arrange & Act
        SpecialtyDTO dto = new SpecialtyDTO();

        // Assert
        assertNotNull(dto);
        assertNull(dto.getIdSpecialty());
        assertNull(dto.getSpecialtyName());
    }

    @Test
    @DisplayName("Should create SpecialtyDTO with all-args constructor")
    void shouldCreateSpecialtyDTOWithAllArgsConstructor() {
        // Arrange
        Integer expectedId = 5;
        String expectedName = "Cardiology";

        // Act
        SpecialtyDTO dto = new SpecialtyDTO(expectedId, expectedName);

        // Assert
        assertNotNull(dto);
        assertEquals(expectedId, dto.getIdSpecialty());
        assertEquals(expectedName, dto.getSpecialtyName());
    }

    @Test
    @DisplayName("Should set and get idSpecialty correctly")
    void shouldSetAndGetIdSpecialtyCorrectly() {
        // Arrange
        SpecialtyDTO dto = new SpecialtyDTO();
        Integer expectedId = 10;

        // Act
        dto.setIdSpecialty(expectedId);

        // Assert
        assertEquals(expectedId, dto.getIdSpecialty());
    }

    @Test
    @DisplayName("Should set and get specialtyName correctly")
    void shouldSetAndGetSpecialtyNameCorrectly() {
        // Arrange
        SpecialtyDTO dto = new SpecialtyDTO();
        String expectedName = "Neurology";

        // Act
        dto.setSpecialtyName(expectedName);

        // Assert
        assertEquals(expectedName, dto.getSpecialtyName());
    }

    @Test
    @DisplayName("Should handle null values in setters")
    void shouldHandleNullValuesInSetters() {
        // Arrange
        SpecialtyDTO dto = new SpecialtyDTO(1, "Pediatrics");

        // Act
        dto.setIdSpecialty(null);
        dto.setSpecialtyName(null);

        // Assert
        assertNull(dto.getIdSpecialty());
        assertNull(dto.getSpecialtyName());
    }

    @Test
    @DisplayName("Should handle empty string in specialtyName")
    void shouldHandleEmptyStringInSpecialtyName() {
        // Arrange
        SpecialtyDTO dto = new SpecialtyDTO();
        String emptyName = "";

        // Act
        dto.setSpecialtyName(emptyName);

        // Assert
        assertEquals(emptyName, dto.getSpecialtyName());
        assertTrue(dto.getSpecialtyName().isEmpty());
    }

    @Test
    @DisplayName("Should allow updating values multiple times")
    void shouldAllowUpdatingValuesMultipleTimes() {
        // Arrange
        SpecialtyDTO dto = new SpecialtyDTO(1, "Cardiology");

        // Act
        dto.setIdSpecialty(2);
        dto.setSpecialtyName("Dermatology");
        dto.setIdSpecialty(3);
        dto.setSpecialtyName("Orthopedics");

        // Assert
        assertEquals(3, dto.getIdSpecialty());
        assertEquals("Orthopedics", dto.getSpecialtyName());
    }
}
