package com.p2f4.med_office.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Clinic Entity Tests")
class ClinicEntityTest {

    @Test
    @DisplayName("Should create Clinic with no-args constructor")
    void testNoArgsConstructor() {
        // Arrange & Act
        Clinic clinic = new Clinic();

        // Assert
        assertNotNull(clinic);
        assertNull(clinic.getIdClinic());
    }

    @Test
    @DisplayName("Should create Clinic with all-args constructor")
    void testAllArgsConstructor() {
        // Arrange & Act
        Clinic clinic = new Clinic("Hospital Central", "Public", "Downtown", "123456789", "Active");

        // Assert
        assertNotNull(clinic);
        assertEquals("Hospital Central", clinic.getName());
        assertEquals("Public", clinic.getType());
        assertEquals("Downtown", clinic.getLocation());
        assertEquals("123456789", clinic.getPhoneNumber());
        assertEquals("Active", clinic.getStatus());
    }

    @Test
    @DisplayName("Should set and get idClinic")
    void testIdClinicGetterSetter() {
        // Arrange
        Clinic clinic = new Clinic();

        // Act
        clinic.setIdClinic(100);

        // Assert
        assertEquals(100, clinic.getIdClinic());
    }

    @Test
    @DisplayName("Should set and get name")
    void testNameGetterSetter() {
        // Arrange
        Clinic clinic = new Clinic();

        // Act
        clinic.setName("Clinic XYZ");

        // Assert
        assertEquals("Clinic XYZ", clinic.getName());
    }

    @Test
    @DisplayName("Should set and get type")
    void testTypeGetterSetter() {
        // Arrange
        Clinic clinic = new Clinic();

        // Act
        clinic.setType("Private");

        // Assert
        assertEquals("Private", clinic.getType());
    }

    @Test
    @DisplayName("Should set and get location")
    void testLocationGetterSetter() {
        // Arrange
        Clinic clinic = new Clinic();

        // Act
        clinic.setLocation("Uptown");

        // Assert
        assertEquals("Uptown", clinic.getLocation());
    }

    @Test
    @DisplayName("Should set and get phoneNumber")
    void testPhoneNumberGetterSetter() {
        // Arrange
        Clinic clinic = new Clinic();

        // Act
        clinic.setPhoneNumber("987654321");

        // Assert
        assertEquals("987654321", clinic.getPhoneNumber());
    }

    @Test
    @DisplayName("Should set and get status")
    void testStatusGetterSetter() {
        // Arrange
        Clinic clinic = new Clinic();

        // Act
        clinic.setStatus("Inactive");

        // Assert
        assertEquals("Inactive", clinic.getStatus());
    }

    @Test
    @DisplayName("Should handle null values in setters")
    void testNullValues() {
        // Arrange
        Clinic clinic = new Clinic();

        // Act
        clinic.setName(null);
        clinic.setType(null);
        clinic.setLocation(null);
        clinic.setPhoneNumber(null);
        clinic.setStatus(null);

        // Assert
        assertNull(clinic.getName());
        assertNull(clinic.getType());
        assertNull(clinic.getLocation());
        assertNull(clinic.getPhoneNumber());
        assertNull(clinic.getStatus());
    }

    @Test
    @DisplayName("Should update values multiple times")
    void testMultipleUpdates() {
        // Arrange
        Clinic clinic = new Clinic();

        // Act
        clinic.setName("First Name");
        clinic.setName("Second Name");
        clinic.setName("Final Name");

        // Assert
        assertEquals("Final Name", clinic.getName());
    }
}
