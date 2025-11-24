package com.p2f4.med_office.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ClinicDTO.
 * Tests follow AAA (Arrange-Act-Assert) pattern.
 * 
 * @author Test Team
 * @version 1.0
 */
@DisplayName("ClinicDTO Tests")
class ClinicDTOTest {

    @Test
    @DisplayName("Should create ClinicDTO with no-args constructor")
    void shouldCreateClinicDTOWithNoArgsConstructor() {
        // Arrange & Act
        ClinicDTO dto = new ClinicDTO();

        // Assert
        assertNotNull(dto);
        assertNull(dto.getIdClinic());
        assertNull(dto.getName());
        assertNull(dto.getType());
        assertNull(dto.getLocation());
        assertNull(dto.getPhoneNumber());
        assertNull(dto.getStatus());
    }

    @Test
    @DisplayName("Should create ClinicDTO with all-args constructor")
    void shouldCreateClinicDTOWithAllArgsConstructor() {
        // Arrange
        Integer expectedId = 1;
        String expectedName = "Central Clinic";
        String expectedType = "General";
        String expectedLocation = "Downtown";
        String expectedPhone = "555-1234";
        String expectedStatus = "ACTIVE";

        // Act
        ClinicDTO dto = new ClinicDTO(expectedId, expectedName, expectedType, 
                                      expectedLocation, expectedPhone, expectedStatus);

        // Assert
        assertNotNull(dto);
        assertEquals(expectedId, dto.getIdClinic());
        assertEquals(expectedName, dto.getName());
        assertEquals(expectedType, dto.getType());
        assertEquals(expectedLocation, dto.getLocation());
        assertEquals(expectedPhone, dto.getPhoneNumber());
        assertEquals(expectedStatus, dto.getStatus());
    }

    @Test
    @DisplayName("Should set and get idClinic correctly")
    void shouldSetAndGetIdClinicCorrectly() {
        // Arrange
        ClinicDTO dto = new ClinicDTO();
        Integer expectedId = 42;

        // Act
        dto.setIdClinic(expectedId);

        // Assert
        assertEquals(expectedId, dto.getIdClinic());
    }

    @Test
    @DisplayName("Should set and get name correctly")
    void shouldSetAndGetNameCorrectly() {
        // Arrange
        ClinicDTO dto = new ClinicDTO();
        String expectedName = "Medical Center";

        // Act
        dto.setName(expectedName);

        // Assert
        assertEquals(expectedName, dto.getName());
    }

    @Test
    @DisplayName("Should set and get type correctly")
    void shouldSetAndGetTypeCorrectly() {
        // Arrange
        ClinicDTO dto = new ClinicDTO();
        String expectedType = "Specialized";

        // Act
        dto.setType(expectedType);

        // Assert
        assertEquals(expectedType, dto.getType());
    }

    @Test
    @DisplayName("Should set and get location correctly")
    void shouldSetAndGetLocationCorrectly() {
        // Arrange
        ClinicDTO dto = new ClinicDTO();
        String expectedLocation = "North District";

        // Act
        dto.setLocation(expectedLocation);

        // Assert
        assertEquals(expectedLocation, dto.getLocation());
    }

    @Test
    @DisplayName("Should set and get phoneNumber correctly")
    void shouldSetAndGetPhoneNumberCorrectly() {
        // Arrange
        ClinicDTO dto = new ClinicDTO();
        String expectedPhone = "555-9876";

        // Act
        dto.setPhoneNumber(expectedPhone);

        // Assert
        assertEquals(expectedPhone, dto.getPhoneNumber());
    }

    @Test
    @DisplayName("Should set and get status correctly")
    void shouldSetAndGetStatusCorrectly() {
        // Arrange
        ClinicDTO dto = new ClinicDTO();
        String expectedStatus = "INACTIVE";

        // Act
        dto.setStatus(expectedStatus);

        // Assert
        assertEquals(expectedStatus, dto.getStatus());
    }

    @Test
    @DisplayName("Should handle null values in setters")
    void shouldHandleNullValuesInSetters() {
        // Arrange
        ClinicDTO dto = new ClinicDTO(1, "Test", "Type", "Location", "Phone", "Status");

        // Act
        dto.setIdClinic(null);
        dto.setName(null);
        dto.setType(null);
        dto.setLocation(null);
        dto.setPhoneNumber(null);
        dto.setStatus(null);

        // Assert
        assertNull(dto.getIdClinic());
        assertNull(dto.getName());
        assertNull(dto.getType());
        assertNull(dto.getLocation());
        assertNull(dto.getPhoneNumber());
        assertNull(dto.getStatus());
    }
}
