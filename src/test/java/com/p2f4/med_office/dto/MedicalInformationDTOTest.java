package com.p2f4.med_office.dto;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MedicalInformationDTOTest {

    @Test
    void testDefaultConstructor() {
        // Act
        MedicalInformationDTO dto = new MedicalInformationDTO();
        
        // Assert
        assertNotNull(dto);
        assertNull(dto.getClinics());
        assertNull(dto.getSpecialties());
    }

    @Test
    void testParameterizedConstructor() {
        // Arrange
        List<ClinicDTO> clinics = Arrays.asList(
            new ClinicDTO(1, "Clinic A", "Address A", "123-456", "email@clinic.com", "Active")
        );
        List<SpecialtyDTO> specialties = Arrays.asList(
            new SpecialtyDTO(1, "Cardiology")
        );
        
        // Act
        MedicalInformationDTO dto = new MedicalInformationDTO(clinics, specialties);
        
        // Assert
        assertNotNull(dto);
        assertEquals(clinics, dto.getClinics());
        assertEquals(specialties, dto.getSpecialties());
        assertEquals(1, dto.getClinics().size());
        assertEquals(1, dto.getSpecialties().size());
    }

    @Test
    void testSetters() {
        // Arrange
        MedicalInformationDTO dto = new MedicalInformationDTO();
        List<ClinicDTO> clinics = Arrays.asList(
            new ClinicDTO(2, "Clinic B", "Address B", "789-012", "info@clinic.com", "Inactive")
        );
        List<SpecialtyDTO> specialties = Arrays.asList(
            new SpecialtyDTO(2, "Neurology")
        );
        
        // Act
        dto.setClinics(clinics);
        dto.setSpecialties(specialties);
        
        // Assert
        assertEquals(clinics, dto.getClinics());
        assertEquals(specialties, dto.getSpecialties());
    }
}
