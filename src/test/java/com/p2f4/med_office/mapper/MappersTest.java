package com.p2f4.med_office.mapper;

import com.p2f4.med_office.dto.ClinicDTO;
import com.p2f4.med_office.dto.SpecialtyDTO;
import com.p2f4.med_office.entity.Clinic;
import com.p2f4.med_office.entity.Specialty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Mappers Tests")
class MappersTest {

    private final ClinicMapper clinicMapper = Mappers.getMapper(ClinicMapper.class);
    private final SpecialtyMapper specialtyMapper = Mappers.getMapper(SpecialtyMapper.class);

    // ====== CLINIC MAPPER TESTS ======

    @Test
    @DisplayName("Should map Clinic entity to ClinicDTO")
    void testClinicToDTO() {
        // Arrange
        Clinic clinic = new Clinic("Hospital Central", "Public", "Downtown", "123456789", "Active");
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
}
