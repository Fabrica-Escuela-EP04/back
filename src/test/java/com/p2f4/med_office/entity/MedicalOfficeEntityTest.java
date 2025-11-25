package com.p2f4.med_office.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.p2f4.med_office.utils.EnumStatus;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MedicalOffice Entity Tests")
class MedicalOfficeEntityTest {

    @Test
    @DisplayName("Should create MedicalOffice with no-args constructor")
    void testNoArgsConstructor() {
        // Arrange & Act
        MedicalOffice office = new MedicalOffice();

        // Assert
        assertNotNull(office);
        assertNull(office.getIdOffice());
    }

    @Test
    @DisplayName("Should set and get idOffice")
    void testIdOfficeGetterSetter() {
        // Arrange
        MedicalOffice office = new MedicalOffice();

        // Act
        office.setIdOffice(10);

        // Assert
        assertEquals(10, office.getIdOffice());
    }

    @Test
    @DisplayName("Should set and get idClinic")
    void testIdClinicGetterSetter() {
        // Arrange
        MedicalOffice office = new MedicalOffice();

        // Act
        office.setIdClinic(5);

        // Assert
        assertEquals(5, office.getIdClinic());
    }

    @Test
    @DisplayName("Should set and get idSpecialty")
    void testIdSpecialtyGetterSetter() {
        // Arrange
        MedicalOffice office = new MedicalOffice();

        // Act
        office.setIdSpecialty(3);

        // Assert
        assertEquals(3, office.getIdSpecialty());
    }

    @Test
    @DisplayName("Should set and get clinic relationship")
    void testClinicGetterSetter() {
        // Arrange
        MedicalOffice office = new MedicalOffice();
        Clinic clinic = new Clinic();
        clinic.setIdClinic(100);

        // Act
        office.setClinic(clinic);

        // Assert
        assertNotNull(office.getClinic());
        assertEquals(100, office.getClinic().getIdClinic());
    }

    @Test
    @DisplayName("Should set and get specialty relationship")
    void testSpecialtyGetterSetter() {
        // Arrange
        MedicalOffice office = new MedicalOffice();
        Specialty specialty = new Specialty();
        specialty.setIdSpecialty(50);

        // Act
        office.setSpecialty(specialty);

        // Assert
        assertNotNull(office.getSpecialty());
        assertEquals(50, office.getSpecialty().getIdSpecialty());
    }

    @Test
    @DisplayName("Should set and get officeNumber")
    void testOfficeNumberGetterSetter() {
        // Arrange
        MedicalOffice office = new MedicalOffice();

        // Act
        office.setOfficeNumber(201);

        // Assert
        assertEquals(201, office.getOfficeNumber());
    }

    @Test
    @DisplayName("Should set and get status")
    void testStatusGetterSetter() {
        // Arrange
        MedicalOffice office = new MedicalOffice();

        // Act
        office.setStatus(EnumStatus.ACTIVO);

        // Assert
        assertEquals("Active", office.getStatus());
    }

    @Test
    @DisplayName("Should handle null clinic relationship")
    void testNullClinic() {
        // Arrange
        MedicalOffice office = new MedicalOffice();

        // Act
        office.setClinic(null);

        // Assert
        assertNull(office.getClinic());
    }

    @Test
    @DisplayName("Should handle null specialty relationship")
    void testNullSpecialty() {
        // Arrange
        MedicalOffice office = new MedicalOffice();

        // Act
        office.setSpecialty(null);

        // Assert
        assertNull(office.getSpecialty());
    }

    @Test
    @DisplayName("Should handle null status")
    void testNullStatus() {
        // Arrange
        MedicalOffice office = new MedicalOffice();

        // Act
        office.setStatus(null);

        // Assert
        assertNull(office.getStatus());
    }

    @Test
    @DisplayName("Should update values multiple times")
    void testMultipleUpdates() {
        // Arrange
        MedicalOffice office = new MedicalOffice();

        // Act
        office.setOfficeNumber(100);
        office.setOfficeNumber(200);
        office.setOfficeNumber(300);

        // Assert
        assertEquals(300, office.getOfficeNumber());
    }
}
