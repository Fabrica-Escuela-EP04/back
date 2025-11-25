package com.p2f4.med_office.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ScheduleDTO.
 * Tests follow AAA (Arrange-Act-Assert) pattern.
 * 
 * @author Test Team
 * @version 1.0
 */
@DisplayName("ScheduleDTO Tests")
class ScheduleDTOTest {

    @Test
    @DisplayName("Should create ScheduleDTO with no-args constructor")
    void shouldCreateScheduleDTOWithNoArgsConstructor() {
        // Arrange & Act
        ScheduleDTO dto = new ScheduleDTO();

        // Assert
        assertNotNull(dto);
        assertNull(dto.getIdSchedule());
        assertNull(dto.getIdUser());
        assertNull(dto.getType());
        assertNull(dto.getIdOffice());
        assertNull(dto.getStartDate());
        assertNull(dto.getEndDate());
    }

    @Test
    @DisplayName("Should create ScheduleDTO with all-args constructor")
    void shouldCreateScheduleDTOWithAllArgsConstructor() {
        // Arrange
        Integer expectedIdSchedule = 1;
        Integer expectedIdUser = 100;
        String expectedType = "MAINTENANCE";
        Integer expectedIdOffice = 201;
        LocalDate expectedStartDate = LocalDate.of(2025, 1, 15);
        LocalDate expectedEndDate = LocalDate.of(2025, 1, 20);

        String expectedStatus = "ACTIVO";

        // Act
        ScheduleDTO dto = new ScheduleDTO(expectedIdSchedule, expectedIdUser, expectedType,
                                          expectedIdOffice, expectedStartDate, expectedEndDate, expectedStatus);
        // Assert
        assertNotNull(dto);
        assertEquals(expectedIdSchedule, dto.getIdSchedule());
        assertEquals(expectedIdUser, dto.getIdUser());
        assertEquals(expectedType, dto.getType());
        assertEquals(expectedIdOffice, dto.getIdOffice());
        assertEquals(expectedStartDate, dto.getStartDate());
        assertEquals(expectedEndDate, dto.getEndDate());
        assertEquals(expectedStatus, dto.getStatus());
    }

    @Test
    @DisplayName("Should set and get idSchedule correctly")
    void shouldSetAndGetIdScheduleCorrectly() {
        // Arrange
        ScheduleDTO dto = new ScheduleDTO();
        Integer expectedId = 42;

        // Act
        dto.setIdSchedule(expectedId);

        // Assert
        assertEquals(expectedId, dto.getIdSchedule());
    }

    @Test
    @DisplayName("Should set and get idUser correctly")
    void shouldSetAndGetIdUserCorrectly() {
        // Arrange
        ScheduleDTO dto = new ScheduleDTO();
        Integer expectedUserId = 99;

        // Act
        dto.setIdUser(expectedUserId);

        // Assert
        assertEquals(expectedUserId, dto.getIdUser());
    }

    @Test
    @DisplayName("Should set and get type correctly")
    void shouldSetAndGetTypeCorrectly() {
        // Arrange
        ScheduleDTO dto = new ScheduleDTO();
        String expectedType = "VACATION";

        // Act
        dto.setType(expectedType);

        // Assert
        assertEquals(expectedType, dto.getType());
    }

    @Test
    @DisplayName("Should set and get idOffice correctly")
    void shouldSetAndGetIdOfficeCorrectly() {
        // Arrange
        ScheduleDTO dto = new ScheduleDTO();
        Integer expectedOfficeId = 305;

        // Act
        dto.setIdOffice(expectedOfficeId);

        // Assert
        assertEquals(expectedOfficeId, dto.getIdOffice());
    }

    @Test
    @DisplayName("Should set and get startDate correctly")
    void shouldSetAndGetStartDateCorrectly() {
        // Arrange
        ScheduleDTO dto = new ScheduleDTO();
        LocalDate expectedDate = LocalDate.of(2025, 6, 1);

        // Act
        dto.setStartDate(expectedDate);

        // Assert
        assertEquals(expectedDate, dto.getStartDate());
    }

    @Test
    @DisplayName("Should set and get endDate correctly")
    void shouldSetAndGetEndDateCorrectly() {
        // Arrange
        ScheduleDTO dto = new ScheduleDTO();
        LocalDate expectedDate = LocalDate.of(2025, 12, 31);

        // Act
        dto.setEndDate(expectedDate);

        // Assert
        assertEquals(expectedDate, dto.getEndDate());
    }

    @Test
    @DisplayName("Should handle null values in setters")
    void shouldHandleNullValuesInSetters() {
        // Arrange
        ScheduleDTO dto = new ScheduleDTO(1, 100, "MAINTENANCE", 201,
                                          LocalDate.now(), LocalDate.now().plusDays(5), "ACTIVO");

        // Act
        dto.setIdSchedule(null);
        dto.setIdUser(null);
        dto.setType(null);
        dto.setIdOffice(null);
        dto.setStartDate(null);
        dto.setEndDate(null);
        dto.setStatus(null);

        // Assert
        assertNull(dto.getIdSchedule());
        assertNull(dto.getIdUser());
        assertNull(dto.getType());
        assertNull(dto.getIdOffice());
        assertNull(dto.getStartDate());
        assertNull(dto.getEndDate());
        assertNull(dto.getStatus());
    }

    @Test
    @DisplayName("Should handle same startDate and endDate")
    void shouldHandleSameStartDateAndEndDate() {
        // Arrange
        LocalDate sameDate = LocalDate.of(2025, 3, 15);
        ScheduleDTO dto = new ScheduleDTO();

        // Act
        dto.setStartDate(sameDate);
        dto.setEndDate(sameDate);

        // Assert
        assertEquals(sameDate, dto.getStartDate());
        assertEquals(sameDate, dto.getEndDate());
        assertEquals(dto.getStartDate(), dto.getEndDate());
    }

    @Test
    @DisplayName("Should allow updating all values multiple times")
    void shouldAllowUpdatingAllValuesMultipleTimes() {
        // Arrange
        ScheduleDTO dto = new ScheduleDTO();

        // Act
        dto.setIdSchedule(1);
        dto.setIdUser(10);
        dto.setType("MAINTENANCE");
        dto.setIdOffice(100);
        dto.setStartDate(LocalDate.of(2025, 1, 1));
        dto.setEndDate(LocalDate.of(2025, 1, 10));

        dto.setIdSchedule(2);
        dto.setIdUser(20);
        dto.setType("VACATION");
        dto.setIdOffice(200);
        dto.setStartDate(LocalDate.of(2025, 6, 1));
        dto.setEndDate(LocalDate.of(2025, 6, 15));

        // Assert
        assertEquals(2, dto.getIdSchedule());
        assertEquals(20, dto.getIdUser());
        assertEquals("VACATION", dto.getType());
        assertEquals(200, dto.getIdOffice());
        assertEquals(LocalDate.of(2025, 6, 1), dto.getStartDate());
        assertEquals(LocalDate.of(2025, 6, 15), dto.getEndDate());
    }
}
