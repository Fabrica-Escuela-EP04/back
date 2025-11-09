package com.p2f4.med_office.core;

import com.p2f4.med_office.domain.ScheduleRepository;
import com.p2f4.med_office.dto.ScheduleDTO;
import com.p2f4.med_office.entity.Schedule;
import com.p2f4.med_office.utils.NonCoungruentDatesException;
import com.p2f4.med_office.utils.NullDateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ScheduleService using JUnit 5 and Mockito.
 * Tests follow AAA (Arrange-Act-Assert) pattern.
 * 
 * @author Test Team
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ScheduleService Tests")
class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    @Nested
    @DisplayName("Get All Schedules Tests")
    class GetAllSchedulesTests {

        @Test
        @DisplayName("Should return list of schedules when schedules exist")
        void shouldReturnListOfSchedulesWhenSchedulesExist() {
            // Arrange
            Schedule schedule1 = new Schedule();
            schedule1.setIdSchedule(1);
            schedule1.setIdUser(1);
            schedule1.setType("MAINTENANCE");
            schedule1.setStartDate(LocalDate.of(2025, 1, 1));
            schedule1.setEndDate(LocalDate.of(2025, 1, 10));

            Schedule schedule2 = new Schedule();
            schedule2.setIdSchedule(2);
            schedule2.setIdUser(2);
            schedule2.setType("VACATION");
            schedule2.setStartDate(LocalDate.of(2025, 2, 1));
            schedule2.setEndDate(LocalDate.of(2025, 2, 15));

            List<Schedule> schedules = Arrays.asList(schedule1, schedule2);
            when(scheduleRepository.findAll()).thenReturn(schedules);

            // Act
            List<ScheduleDTO> result = scheduleService.getAllSchedules();

            // Assert
            assertNotNull(result, "Result should not be null");
            assertEquals(2, result.size(), "Should return 2 schedules");
            verify(scheduleRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Should return empty list when no schedules exist")
        void shouldReturnEmptyListWhenNoSchedulesExist() {
            // Arrange
            when(scheduleRepository.findAll()).thenReturn(Collections.emptyList());

            // Act
            List<ScheduleDTO> result = scheduleService.getAllSchedules();

            // Assert
            assertNotNull(result, "Result should not be null");
            assertTrue(result.isEmpty(), "Result should be empty");
            verify(scheduleRepository, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("Create Maintenance Schedule Tests")
    class CreateMaintenanceScheduleTests {

        @Test
        @DisplayName("Should create maintenance schedule with valid dates")
        void shouldCreateMaintenanceScheduleWithValidDates() {
            // Arrange
            Integer idUser = 1;
            String type = "MAINTENANCE";
            Integer idOffice = 1;
            LocalDate startDate = LocalDate.of(2025, 1, 1);
            LocalDate endDate = LocalDate.of(2025, 1, 10);

            Schedule savedSchedule = new Schedule();
            savedSchedule.setIdSchedule(1);
            savedSchedule.setIdUser(idUser);
            savedSchedule.setType(type);
            savedSchedule.setIdOffice(idOffice);
            savedSchedule.setStartDate(startDate);
            savedSchedule.setEndDate(endDate);

            when(scheduleRepository.save(any(Schedule.class))).thenReturn(savedSchedule);

            // Act
            ScheduleDTO result = scheduleService.createMaintenanceSchedule(idUser, type, idOffice, startDate, endDate);

            // Assert
            assertNotNull(result, "Result should not be null");
            assertEquals(idUser, result.getIdUser(), "User ID should match");
            assertEquals(type, result.getType(), "Type should match");
            assertEquals(startDate, result.getStartDate(), "Start date should match");
            assertEquals(endDate, result.getEndDate(), "End date should match");

            verify(scheduleRepository, times(1)).save(any(Schedule.class));
        }

        @Test
        @DisplayName("Should throw NullDateException when startDate is null")
        void shouldThrowNullDateExceptionWhenStartDateIsNull() {
            // Arrange
            Integer idUser = 1;
            String type = "MAINTENANCE";
            Integer idOffice = 1;
            LocalDate startDate = null;
            LocalDate endDate = LocalDate.of(2025, 1, 10);

            // Act & Assert
            assertThrows(NullDateException.class, () -> {
                scheduleService.createMaintenanceSchedule(idUser, type, idOffice, startDate, endDate);
            }, "Should throw NullDateException when startDate is null");

            verify(scheduleRepository, never()).save(any(Schedule.class));
        }

        @Test
        @DisplayName("Should throw NullDateException when endDate is null")
        void shouldThrowNullDateExceptionWhenEndDateIsNull() {
            // Arrange
            Integer idUser = 1;
            String type = "MAINTENANCE";
            Integer idOffice = 1;
            LocalDate startDate = LocalDate.of(2025, 1, 1);
            LocalDate endDate = null;

            // Act & Assert
            assertThrows(NullDateException.class, () -> {
                scheduleService.createMaintenanceSchedule(idUser, type, idOffice, startDate, endDate);
            }, "Should throw NullDateException when endDate is null");

            verify(scheduleRepository, never()).save(any(Schedule.class));
        }

        @Test
        @DisplayName("Should throw NullDateException when both dates are null")
        void shouldThrowNullDateExceptionWhenBothDatesAreNull() {
            // Arrange
            Integer idUser = 1;
            String type = "MAINTENANCE";
            Integer idOffice = 1;
            LocalDate startDate = null;
            LocalDate endDate = null;

            // Act & Assert
            assertThrows(NullDateException.class, () -> {
                scheduleService.createMaintenanceSchedule(idUser, type, idOffice, startDate, endDate);
            }, "Should throw NullDateException when both dates are null");

            verify(scheduleRepository, never()).save(any(Schedule.class));
        }

        @Test
        @DisplayName("Should throw NonCoungruentDatesException when endDate is before startDate")
        void shouldThrowNonCoungruentDatesExceptionWhenEndDateIsBeforeStartDate() {
            // Arrange
            Integer idUser = 1;
            String type = "MAINTENANCE";
            Integer idOffice = 1;
            LocalDate startDate = LocalDate.of(2025, 1, 10);
            LocalDate endDate = LocalDate.of(2025, 1, 1);

            // Act & Assert
            assertThrows(NonCoungruentDatesException.class, () -> {
                scheduleService.createMaintenanceSchedule(idUser, type, idOffice, startDate, endDate);
            }, "Should throw NonCoungruentDatesException when endDate is before startDate");

            verify(scheduleRepository, never()).save(any(Schedule.class));
        }

        @Test
        @DisplayName("Should create schedule when startDate equals endDate")
        void shouldCreateScheduleWhenStartDateEqualsEndDate() {
            // Arrange
            Integer idUser = 1;
            String type = "MAINTENANCE";
            Integer idOffice = 1;
            LocalDate sameDate = LocalDate.of(2025, 1, 5);

            Schedule savedSchedule = new Schedule();
            savedSchedule.setIdSchedule(1);
            savedSchedule.setIdUser(idUser);
            savedSchedule.setType(type);
            savedSchedule.setIdOffice(idOffice);
            savedSchedule.setStartDate(sameDate);
            savedSchedule.setEndDate(sameDate);

            when(scheduleRepository.save(any(Schedule.class))).thenReturn(savedSchedule);

            // Act
            ScheduleDTO result = scheduleService.createMaintenanceSchedule(idUser, type, idOffice, sameDate, sameDate);

            // Assert
            assertNotNull(result, "Result should not be null");
            assertEquals(sameDate, result.getStartDate(), "Start date should match");
            assertEquals(sameDate, result.getEndDate(), "End date should match");

            verify(scheduleRepository, times(1)).save(any(Schedule.class));
        }

        @Test
        @DisplayName("Should create schedule with long date range")
        void shouldCreateScheduleWithLongDateRange() {
            // Arrange
            Integer idUser = 1;
            String type = "VACATION";
            Integer idOffice = 1;
            LocalDate startDate = LocalDate.of(2025, 1, 1);
            LocalDate endDate = LocalDate.of(2025, 12, 31);

            Schedule savedSchedule = new Schedule();
            savedSchedule.setIdSchedule(1);
            savedSchedule.setIdUser(idUser);
            savedSchedule.setType(type);
            savedSchedule.setIdOffice(idOffice);
            savedSchedule.setStartDate(startDate);
            savedSchedule.setEndDate(endDate);

            when(scheduleRepository.save(any(Schedule.class))).thenReturn(savedSchedule);

            // Act
            ScheduleDTO result = scheduleService.createMaintenanceSchedule(idUser, type, idOffice, startDate, endDate);

            // Assert
            assertNotNull(result, "Result should not be null");
            assertEquals(startDate, result.getStartDate(), "Start date should match");
            assertEquals(endDate, result.getEndDate(), "End date should match");

            verify(scheduleRepository, times(1)).save(any(Schedule.class));
        }
    }
}
