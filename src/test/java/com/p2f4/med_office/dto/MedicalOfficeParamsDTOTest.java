package com.p2f4.med_office.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MedicalOfficeParamsDTOTest {

    @Test
    void testDefaultConstructor() {
        // Act
        MedicalOfficeParamsDTO dto = new MedicalOfficeParamsDTO();
        
        // Assert
        assertNotNull(dto);
        assertNull(dto.getIdOffice());
        assertNull(dto.getClinicName());
        assertNull(dto.getSpecialtyName());
        assertNull(dto.getOfficeNumber());
        assertNull(dto.getStatus());
    }

    @Test
    void testParameterizedConstructor() {
        // Arrange
        Integer idOffice = 1;
        String clinicName = "Clinic A";
        String specialtyName = "Cardiology";
        Integer officeNumber = 101;
        String status = "Active";
        
        // Act
        MedicalOfficeParamsDTO dto = new MedicalOfficeParamsDTO(
            idOffice, clinicName, specialtyName, officeNumber, status
        );
        
        // Assert
        assertNotNull(dto);
        assertEquals(idOffice, dto.getIdOffice());
        assertEquals(clinicName, dto.getClinicName());
        assertEquals(specialtyName, dto.getSpecialtyName());
        assertEquals(officeNumber, dto.getOfficeNumber());
        assertEquals(status, dto.getStatus());
    }

    @Test
    void testSetters() {
        // Arrange
        MedicalOfficeParamsDTO dto = new MedicalOfficeParamsDTO();
        Integer idOffice = 2;
        String clinicName = "Clinic B";
        String specialtyName = "Neurology";
        Integer officeNumber = 202;
        String status = "Inactive";
        
        // Act
        dto.setIdOffice(idOffice);
        dto.setClinicName(clinicName);
        dto.setSpecialtyName(specialtyName);
        dto.setOfficeNumber(officeNumber);
        dto.setStatus(status);
        
        // Assert
        assertEquals(idOffice, dto.getIdOffice());
        assertEquals(clinicName, dto.getClinicName());
        assertEquals(specialtyName, dto.getSpecialtyName());
        assertEquals(officeNumber, dto.getOfficeNumber());
        assertEquals(status, dto.getStatus());
    }
}
