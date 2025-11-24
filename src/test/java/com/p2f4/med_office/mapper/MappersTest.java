package com.p2f4.med_office.mapper;

import com.p2f4.med_office.dto.*;
import com.p2f4.med_office.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import com.p2f4.med_office.utils.EnumStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Mappers Tests")
class MappersTest {

    private final ClinicMapper clinicMapper = Mappers.getMapper(ClinicMapper.class);
    private final SpecialtyMapper specialtyMapper = Mappers.getMapper(SpecialtyMapper.class);
    private final ScheduleMapper scheduleMapper = Mappers.getMapper(ScheduleMapper.class);
    private final MedicalOfficeMapper medicalOfficeMapper = Mappers.getMapper(MedicalOfficeMapper.class);
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    // ====== CLINIC MAPPER TESTS ======

    @Test
    @DisplayName("Should map Clinic entity to ClinicDTO")
    void testClinicToDTO() {
        // Arrange
        Clinic clinic = new Clinic("Hospital Central", "Public", "Downtown", "123456789", EnumStatus.ACTIVO);
        clinic.setIdClinic(1);

        // Act
        ClinicDTO dto = clinicMapper.toDTO(clinic);

        // Assert
        assertNotNull(dto);
        assertEquals(1, dto.getIdClinic());
        assertEquals("Hospital Central", dto.getName());
        assertEquals("Public", dto.getType());
        assertEquals("Downtown", dto.getLocation());
        assertEquals("123456789", dto.getPhoneNumber());
        assertEquals("Active", dto.getStatus());
    }

    @Test
    @DisplayName("Should map ClinicDTO to Clinic entity")
    void testDTOToClinic() {
        // Arrange
        ClinicDTO dto = new ClinicDTO(2, "Clinic XYZ", "Private", "Uptown", "987654321", "Inactive");

        // Act
        Clinic clinic = clinicMapper.toEntity(dto);

        // Assert
        assertNotNull(clinic);
        assertEquals(2, clinic.getIdClinic());
        assertEquals("Clinic XYZ", clinic.getName());
        assertEquals("Private", clinic.getType());
        assertEquals("Uptown", clinic.getLocation());
        assertEquals("987654321", clinic.getPhoneNumber());
        assertEquals("Inactive", clinic.getStatus());
    }

    @Test
    @DisplayName("Should handle null Clinic in toDTO")
    void testClinicToDTOWithNull() {
        // Act
        ClinicDTO dto = clinicMapper.toDTO(null);

        // Assert
        assertNull(dto);
    }

    @Test
    @DisplayName("Should handle null ClinicDTO in toEntity")
    void testDTOToClinicWithNull() {
        // Act
        Clinic clinic = clinicMapper.toEntity(null);

        // Assert
        assertNull(clinic);
    }

    @Test
    @DisplayName("Should map Clinic with null fields")
    void testClinicToDTOWithNullFields() {
        // Arrange
        Clinic clinic = new Clinic();
        clinic.setIdClinic(10);

        // Act
        ClinicDTO dto = clinicMapper.toDTO(clinic);

        // Assert
        assertNotNull(dto);
        assertEquals(10, dto.getIdClinic());
        assertNull(dto.getName());
        assertNull(dto.getType());
    }

    // ====== SPECIALTY MAPPER TESTS ======

    @Test
    @DisplayName("Should map Specialty entity to SpecialtyDTO")
    void testSpecialtyToDTO() {
        // Arrange
        Specialty specialty = new Specialty();
        specialty.setIdSpecialty(1);
        specialty.setSpecialtyName("Cardiology");

        // Act
        SpecialtyDTO dto = specialtyMapper.toDTO(specialty);

        // Assert
        assertNotNull(dto);
        assertEquals(1, dto.getIdSpecialty());
        assertEquals("Cardiology", dto.getSpecialtyName());
    }

    @Test
    @DisplayName("Should map SpecialtyDTO to Specialty entity")
    void testDTOToSpecialty() {
        // Arrange
        SpecialtyDTO dto = new SpecialtyDTO(2, "Neurology");

        // Act
        Specialty specialty = specialtyMapper.toEntity(dto);

        // Assert
        assertNotNull(specialty);
        assertEquals(2, specialty.getIdSpecialty());
        assertEquals("Neurology", specialty.getSpecialtyName());
        assertNull(specialty.getUsers()); // users should be ignored
    }

    @Test
    @DisplayName("Should handle null Specialty in toDTO")
    void testSpecialtyToDTOWithNull() {
        // Act
        SpecialtyDTO dto = specialtyMapper.toDTO(null);

        // Assert
        assertNull(dto);
    }

    @Test
    @DisplayName("Should handle null SpecialtyDTO in toEntity")
    void testDTOToSpecialtyWithNull() {
        // Act
        Specialty specialty = specialtyMapper.toEntity(null);

        // Assert
        assertNull(specialty);
    }

    @Test
    @DisplayName("Should map Specialty with null fields")
    void testSpecialtyToDTOWithNullFields() {
        // Arrange
        Specialty specialty = new Specialty();
        specialty.setIdSpecialty(10);

        // Act
        SpecialtyDTO dto = specialtyMapper.toDTO(specialty);

        // Assert
        assertNotNull(dto);
        assertEquals(10, dto.getIdSpecialty());
        assertNull(dto.getSpecialtyName());
    }

    @Test
    @DisplayName("ClinicMapper INSTANCE should not be null")
    void testClinicMapperInstanceNotNull() {
        // Assert
        assertNotNull(ClinicMapper.INSTANCE);
    }

    @Test
    @DisplayName("SpecialtyMapper INSTANCE should not be null")
    void testSpecialtyMapperInstanceNotNull() {
        // Assert
        assertNotNull(SpecialtyMapper.INSTANCE);
    }

    // ====== SCHEDULE MAPPER TESTS ======

    @Test
    @DisplayName("Should map Schedule entity to ScheduleDTO")
    void testScheduleToDTO() {
        // Arrange
        Schedule schedule = new Schedule();
        schedule.setIdSchedule(1);
        schedule.setIdUser(100);
        schedule.setType("MAINTENANCE");
        schedule.setIdOffice(200);
        schedule.setStartDate(LocalDate.of(2025, 1, 1));
        schedule.setEndDate(LocalDate.of(2025, 1, 10));

        // Act
        ScheduleDTO dto = scheduleMapper.toDTO(schedule);

        // Assert
        assertNotNull(dto);
        assertEquals(1, dto.getIdSchedule());
        assertEquals(100, dto.getIdUser());
        assertEquals("MAINTENANCE", dto.getType());
        assertEquals(200, dto.getIdOffice());
        assertEquals(LocalDate.of(2025, 1, 1), dto.getStartDate());
        assertEquals(LocalDate.of(2025, 1, 10), dto.getEndDate());
    }

    @Test
    @DisplayName("Should map ScheduleDTO to Schedule entity")
    void testDTOToSchedule() {
        // Arrange
        ScheduleDTO dto = new ScheduleDTO();
        dto.setIdSchedule(2);
        dto.setIdUser(101);
        dto.setType("VACATION");
        dto.setIdOffice(201);
        dto.setStartDate(LocalDate.of(2025, 2, 1));
        dto.setEndDate(LocalDate.of(2025, 2, 15));

        // Act
        Schedule schedule = scheduleMapper.toEntity(dto);

        // Assert
        assertNotNull(schedule);
        assertEquals(2, schedule.getIdSchedule());
        assertEquals(101, schedule.getIdUser());
        assertEquals("VACATION", schedule.getType());
        assertEquals(201, schedule.getIdOffice());
        assertEquals(LocalDate.of(2025, 2, 1), schedule.getStartDate());
        assertEquals(LocalDate.of(2025, 2, 15), schedule.getEndDate());
    }

    @Test
    @DisplayName("Should handle null Schedule in toDTO")
    void testScheduleToDTOWithNull() {
        // Act
        ScheduleDTO dto = scheduleMapper.toDTO(null);

        // Assert
        assertNull(dto);
    }

    @Test
    @DisplayName("Should handle null ScheduleDTO in toEntity")
    void testDTOToScheduleWithNull() {
        // Act
        Schedule schedule = scheduleMapper.toEntity(null);

        // Assert
        assertNull(schedule);
    }

    @Test
    @DisplayName("ScheduleMapper INSTANCE should not be null")
    void testScheduleMapperInstanceNotNull() {
        // Assert
        assertNotNull(ScheduleMapper.INSTANCE);
    }

    // ====== MEDICAL OFFICE MAPPER TESTS ======

    @Test
    @DisplayName("Should map MedicalOffice entity to MedicalOfficeDTO")
    void testMedicalOfficeToDTO() {
        // Arrange
        MedicalOffice office = new MedicalOffice();
        office.setIdOffice(1);
        office.setIdClinic(10);
        office.setIdSpecialty(20);
        office.setOfficeNumber(101);
        office.setStatus(EnumStatus.ACTIVO);

        // Act
        MedicalOfficeDTO dto = medicalOfficeMapper.toDTO(office);

        // Assert
        assertNotNull(dto);
        assertEquals(1, dto.getIdOffice());
        assertEquals(10, dto.getIdClinic());
        assertEquals(20, dto.getIdSpecialty());
        assertEquals(101, dto.getOfficeNumber());
        assertEquals("ACTIVE", dto.getStatus());
    }

    @Test
    @DisplayName("Should map MedicalOfficeDTO to MedicalOffice entity")
    void testDTOToMedicalOffice() {
        // Arrange
        MedicalOfficeDTO dto = new MedicalOfficeDTO();
        dto.setIdOffice(2);
        dto.setIdClinic(11);
        dto.setIdSpecialty(21);
        dto.setOfficeNumber(102);
        dto.setStatus("INACTIVE");

        // Act
        MedicalOffice office = medicalOfficeMapper.toEntity(dto);

        // Assert
        assertNotNull(office);
        assertEquals(2, office.getIdOffice());
        assertEquals(11, office.getIdClinic());
        assertEquals(21, office.getIdSpecialty());
        assertEquals(102, office.getOfficeNumber());
        assertEquals("INACTIVE", office.getStatus());
    }

    @Test
    @DisplayName("Should map MedicalOffice to MedicalOfficeParamsDTO with clinic and specialty")
    void testMedicalOfficeToParamsDTO() {
        // Arrange
        Clinic clinic = new Clinic();
        clinic.setName("Central Hospital");

        Specialty specialty = new Specialty();
        specialty.setSpecialtyName("Cardiology");

        MedicalOffice office = new MedicalOffice();
        office.setIdOffice(1);
        office.setOfficeNumber(101);
        office.setStatus(EnumStatus.ACTIVO);
        office.setClinic(clinic);
        office.setSpecialty(specialty);

        // Act
        MedicalOfficeParamsDTO dto = medicalOfficeMapper.toParamsDTO(office);

        // Assert
        assertNotNull(dto);
        assertEquals(1, dto.getIdOffice());
        assertEquals("Central Hospital", dto.getClinicName());
        assertEquals("Cardiology", dto.getSpecialtyName());
        assertEquals(101, dto.getOfficeNumber());
        assertEquals("ACTIVE", dto.getStatus());
    }

    @Test
    @DisplayName("Should handle null MedicalOffice in toDTO")
    void testMedicalOfficeToDTOWithNull() {
        // Act
        MedicalOfficeDTO dto = medicalOfficeMapper.toDTO(null);

        // Assert
        assertNull(dto);
    }

    @Test
    @DisplayName("Should handle null MedicalOfficeDTO in toEntity")
    void testDTOToMedicalOfficeWithNull() {
        // Act
        MedicalOffice office = medicalOfficeMapper.toEntity(null);

        // Assert
        assertNull(office);
    }

    // ====== USER MAPPER TESTS ======

    @Test
    @DisplayName("Should map User entity to UserDTO")
    void testUserToDTO() {
        // Arrange
        User user = new User();
        user.setIdUser(1);
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setDocument("123456789");
        user.setDocumentType("CC");
        user.setPhoneNumber("3001234567");
        user.setIdRole((short) 2);

        // Act
        UserDTO dto = userMapper.toDTO(user);

        // Assert
        assertNotNull(dto);
        assertEquals(1, dto.getIdUser());
        assertEquals("John", dto.getName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("john.doe@example.com", dto.getEmail());
        assertEquals("123456789", dto.getDocument());
        assertEquals("CC", dto.getDocumentType());
        assertEquals("3001234567", dto.getPhoneNumber());
        assertEquals((short) 2, dto.getIdRole());
    }

    @Test
    @DisplayName("Should map UserDTO to User entity")
    void testDTOToUser() {
        // Arrange
        UserDTO dto = new UserDTO();
        dto.setIdUser(2);
        dto.setName("Jane");
        dto.setLastName("Smith");
        dto.setEmail("jane.smith@example.com");
        dto.setDocument("987654321");
        dto.setDocumentType("TI");
        dto.setPhoneNumber("3009876543");
        dto.setIdRole((short) 1);

        // Act
        User user = userMapper.toEntity(dto);

        // Assert
        assertNotNull(user);
        assertEquals(2, user.getIdUser());
        assertEquals("Jane", user.getName());
        assertEquals("Smith", user.getLastName());
        assertEquals("jane.smith@example.com", user.getEmail());
        assertEquals("987654321", user.getDocument());
        assertEquals("TI", user.getDocumentType());
        assertEquals("3009876543", user.getPhoneNumber());
        assertEquals((short) 1, user.getIdRole());
    }

    @Test
    @DisplayName("Should handle null User in toDTO")
    void testUserToDTOWithNull() {
        // Act
        UserDTO dto = userMapper.toDTO(null);

        // Assert
        assertNull(dto);
    }

    @Test
    @DisplayName("Should handle null UserDTO in toEntity")
    void testDTOToUserWithNull() {
        // Act
        User user = userMapper.toEntity(null);

        // Assert
        assertNull(user);
    }

    @Test
    @DisplayName("UserMapper INSTANCE should not be null")
    void testUserMapperInstanceNotNull() {
        // Assert
        assertNotNull(UserMapper.INSTANCE);
    }
}
