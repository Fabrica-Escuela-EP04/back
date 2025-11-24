package com.p2f4.med_office.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Schedule Entity Tests")
class ScheduleEntityTest {

    @Test
    @DisplayName("Should create Schedule with no-args constructor")
    void testNoArgsConstructor() {
        // Arrange & Act
        Schedule schedule = new Schedule();

        // Assert
        assertNotNull(schedule);
        assertNull(schedule.getIdSchedule());
    }

    @Test
    @DisplayName("Should set and get idSchedule")
    void testIdScheduleGetterSetter() {
        // Arrange
        Schedule schedule = new Schedule();

        // Act
        schedule.setIdSchedule(1);

        // Assert
        assertEquals(1, schedule.getIdSchedule());
    }

    @Test
    @DisplayName("Should set and get idUser")
    void testIdUserGetterSetter() {
        // Arrange
        Schedule schedule = new Schedule();

        // Act
        schedule.setIdUser(25);

        // Assert
        assertEquals(25, schedule.getIdUser());
    }

    @Test
    @DisplayName("Should set and get type")
    void testTypeGetterSetter() {
        // Arrange
        Schedule schedule = new Schedule();

        // Act
        schedule.setType("Consultation");

        // Assert
        assertEquals("Consultation", schedule.getType());
    }

    @Test
    @DisplayName("Should set and get idOffice")
    void testIdOfficeGetterSetter() {
        // Arrange
        Schedule schedule = new Schedule();

        // Act
        schedule.setIdOffice(15);

        // Assert
        assertEquals(15, schedule.getIdOffice());
    }

    @Test
    @DisplayName("Should set and get startDate")
    void testStartDateGetterSetter() {
        // Arrange
        Schedule schedule = new Schedule();
        LocalDate startDate = LocalDate.of(2025, 11, 10);

        // Act
        schedule.setStartDate(startDate);

        // Assert
        assertEquals(startDate, schedule.getStartDate());
    }

    @Test
    @DisplayName("Should set and get endDate")
    void testEndDateGetterSetter() {
        // Arrange
        Schedule schedule = new Schedule();
        LocalDate endDate = LocalDate.of(2025, 12, 31);

        // Act
        schedule.setEndDate(endDate);

        // Assert
        assertEquals(endDate, schedule.getEndDate());
    }

    @Test
    @DisplayName("Should set and get user relationship")
    void testUserGetterSetter() {
        // Arrange
        Schedule schedule = new Schedule();
        User user = new User();

        // Act
        schedule.setUser(user);

        // Assert
        assertNotNull(schedule.getUser());
    }

    @Test
    @DisplayName("Should set and get medicalOffice relationship")
    void testMedicalOfficeGetterSetter() {
        // Arrange
        Schedule schedule = new Schedule();
        MedicalOffice office = new MedicalOffice();
        office.setIdOffice(100);

        // Act
        schedule.setMedicalOffice(office);

        // Assert
        assertNotNull(schedule.getMedicalOffice());
        assertEquals(100, schedule.getMedicalOffice().getIdOffice());
    }

    @Test
    @DisplayName("Should handle null type")
    void testNullType() {
        // Arrange
        Schedule schedule = new Schedule();

        // Act
        schedule.setType(null);

        // Assert
        assertNull(schedule.getType());
    }

    @Test
    @DisplayName("Should handle null dates")
    void testNullDates() {
        // Arrange
        Schedule schedule = new Schedule();

        // Act
        schedule.setStartDate(null);
        schedule.setEndDate(null);

        // Assert
        assertNull(schedule.getStartDate());
        assertNull(schedule.getEndDate());
    }

    @Test
    @DisplayName("Should handle same start and end date")
    void testSameStartEndDate() {
        // Arrange
        Schedule schedule = new Schedule();
        LocalDate sameDate = LocalDate.of(2025, 11, 15);

        // Act
        schedule.setStartDate(sameDate);
        schedule.setEndDate(sameDate);

        // Assert
        assertEquals(sameDate, schedule.getStartDate());
        assertEquals(sameDate, schedule.getEndDate());
        assertEquals(schedule.getStartDate(), schedule.getEndDate());
    }

    @Test
    @DisplayName("Should handle null user relationship")
    void testNullUser() {
        // Arrange
        Schedule schedule = new Schedule();

        // Act
        schedule.setUser(null);

        // Assert
        assertNull(schedule.getUser());
    }

    @Test
    @DisplayName("Should handle null medicalOffice relationship")
    void testNullMedicalOffice() {
        // Arrange
        Schedule schedule = new Schedule();

        // Act
        schedule.setMedicalOffice(null);

        // Assert
        assertNull(schedule.getMedicalOffice());
    }

    @Test
    @DisplayName("Should update values multiple times")
    void testMultipleUpdates() {
        // Arrange
        Schedule schedule = new Schedule();

        // Act
        schedule.setType("Consultation");
        schedule.setType("Surgery");
        schedule.setType("Maintenance");

        // Assert
        assertEquals("Maintenance", schedule.getType());
    }
}
