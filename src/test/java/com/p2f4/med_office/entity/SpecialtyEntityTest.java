package com.p2f4.med_office.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Specialty Entity Tests")
class SpecialtyEntityTest {

    @Test
    @DisplayName("Should create Specialty with no-args constructor")
    void testNoArgsConstructor() {
        // Arrange & Act
        Specialty specialty = new Specialty();

        // Assert
        assertNotNull(specialty);
        assertNull(specialty.getIdSpecialty());
        assertNull(specialty.getSpecialtyName());
    }

    @Test
    @DisplayName("Should set and get idSpecialty")
    void testIdSpecialtyGetterSetter() {
        // Arrange
        Specialty specialty = new Specialty();

        // Act
        specialty.setIdSpecialty(50);

        // Assert
        assertEquals(50, specialty.getIdSpecialty());
    }

    @Test
    @DisplayName("Should set and get specialtyName")
    void testSpecialtyNameGetterSetter() {
        // Arrange
        Specialty specialty = new Specialty();

        // Act
        specialty.setSpecialtyName("Cardiology");

        // Assert
        assertEquals("Cardiology", specialty.getSpecialtyName());
    }

    @Test
    @DisplayName("Should set and get users collection")
    void testUsersGetterSetter() {
        // Arrange
        Specialty specialty = new Specialty();
        java.util.Set<User> users = new java.util.HashSet<>();

        // Act
        specialty.setUsers(users);

        // Assert
        assertNotNull(specialty.getUsers());
        assertEquals(0, specialty.getUsers().size());
    }

    @Test
    @DisplayName("Should handle null specialtyName")
    void testNullSpecialtyName() {
        // Arrange
        Specialty specialty = new Specialty();

        // Act
        specialty.setSpecialtyName(null);

        // Assert
        assertNull(specialty.getSpecialtyName());
    }

    @Test
    @DisplayName("Should handle empty string specialtyName")
    void testEmptySpecialtyName() {
        // Arrange
        Specialty specialty = new Specialty();

        // Act
        specialty.setSpecialtyName("");

        // Assert
        assertEquals("", specialty.getSpecialtyName());
    }

    @Test
    @DisplayName("Should update specialtyName multiple times")
    void testMultipleUpdates() {
        // Arrange
        Specialty specialty = new Specialty();

        // Act
        specialty.setSpecialtyName("Cardiology");
        specialty.setSpecialtyName("Neurology");
        specialty.setSpecialtyName("Oncology");

        // Assert
        assertEquals("Oncology", specialty.getSpecialtyName());
    }

    @Test
    @DisplayName("Should handle null users collection")
    void testNullUsersCollection() {
        // Arrange
        Specialty specialty = new Specialty();

        // Act
        specialty.setUsers(null);

        // Assert
        assertNull(specialty.getUsers());
    }
}
