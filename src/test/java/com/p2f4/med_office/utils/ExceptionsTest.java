package com.p2f4.med_office.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Custom Exceptions Tests")
class ExceptionsTest {

    @Test
    @DisplayName("Should create ActiveScheduleException with correct properties")
    void testActiveScheduleException() {
        // Act
        ActiveScheduleException exception = new ActiveScheduleException();

        // Assert
        assertNotNull(exception);
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertEquals("ACTIVE_SCHEDULE", exception.getCode());
        assertEquals("Ya existe un mantenimiento activo", exception.getMessage());
    }

    @Test
    @DisplayName("Should create ClinicInactiveException with correct properties")
    void testClinicInactiveException() {
        // Act
        ClinicInactiveException exception = new ClinicInactiveException();

        // Assert
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("CLINIC_INACTIVE", exception.getCode());
        assertEquals("La Sede está inactiva, no se pueden agregar consultorios", exception.getMessage());
    }

    @Test
    @DisplayName("Should create ClinicNotFoundException with correct properties")
    void testClinicNotFoundException() {
        // Act
        ClinicNotFoundException exception = new ClinicNotFoundException();

        // Assert
        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("CLINIC_NOT_FOUND", exception.getCode());
        assertEquals("La Sede no existe", exception.getMessage());
    }

    @Test
    @DisplayName("Should create DuplicatedUserException with correct properties")
    void testDuplicatedUserException() {
        // Act
        DuplicatedUserException exception = new DuplicatedUserException();

        // Assert
        assertNotNull(exception);
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertEquals("DUPLICATED_USER", exception.getCode());
        assertEquals("Ya existe un usuario registrado con ese correo", exception.getMessage());
    }

    @Test
    @DisplayName("Should create InvalidPasswordException with correct properties")
    void testInvalidPasswordException() {
        // Act
        InvalidPasswordException exception = new InvalidPasswordException();

        // Assert
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("INVALID_PASSWORD", exception.getCode());
        assertEquals("Nombre de usuario o contraseña erroneos", exception.getMessage());
    }

    @Test
    @DisplayName("Should create MedicalOfficeNotFoundException with correct properties")
    void testMedicalOfficeNotFoundException() {
        // Act
        MedicalOfficeNotFoundException exception = new MedicalOfficeNotFoundException();

        // Assert
        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("CLINIC_NOT_FOUND", exception.getCode());
        assertEquals("La Sede no existe", exception.getMessage());
    }

    @Test
    @DisplayName("Should create NonCoungruentDatesException with correct properties")
    void testNonCoungruentDatesException() {
        // Act
        NonCoungruentDatesException exception = new NonCoungruentDatesException();

        // Assert
        assertNotNull(exception);
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertEquals("NON_CONGRUENT_DATE", exception.getCode());
        assertEquals("La fecha de finalización debe ser posterior a la de incio", exception.getMessage());
    }

    @Test
    @DisplayName("Should create NullDateException with correct properties")
    void testNullDateException() {
        // Act
        NullDateException exception = new NullDateException();

        // Assert
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("NULL_DATE", exception.getCode());
        assertEquals("Fechas no pueden ser nulas", exception.getMessage());
    }

    @Test
    @DisplayName("Should create OfficeNumberDuplicateException with correct properties")
    void testOfficeNumberDuplicateException() {
        // Act
        OfficeNumberDuplicateException exception = new OfficeNumberDuplicateException();

        // Assert
        assertNotNull(exception);
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertEquals("OFFICE_DUPLICATE", exception.getCode());
        assertEquals("El número de consultorio ya existe en la Sede", exception.getMessage());
    }

    @Test
    @DisplayName("Should create SpecialtyNotFoundException with correct properties")
    void testSpecialtyNotFoundException() {
        // Act
        SpecialtyNotFoundException exception = new SpecialtyNotFoundException();

        // Assert
        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("SPECIALTY_NOT_FOUND", exception.getCode());
        assertEquals("La Especialidad no existe", exception.getMessage());
    }

    @Test
    @DisplayName("Should create UserNotFoundException with correct properties")
    void testUserNotFoundException() {
        // Act
        UserNotFoundException exception = new UserNotFoundException();

        // Assert
        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("USER_NOT_FOUND", exception.getCode());
        assertEquals("Usuario no registrado", exception.getMessage());
    }

    @Test
    @DisplayName("Should create UserRoleNotFoundException with correct properties")
    void testUserRoleNotFoundException() {
        // Act
        UserRoleNotFoundException exception = new UserRoleNotFoundException();

        // Assert
        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("ROLE_NOT_FOUND", exception.getCode());
        assertEquals("Rol de usuario no válido", exception.getMessage());
    }

    @Test
    @DisplayName("ActiveScheduleException should be instanceof BusinessException")
    void testActiveScheduleExceptionInheritance() {
        // Act
        ActiveScheduleException exception = new ActiveScheduleException();

        // Assert
        assertTrue(exception instanceof BusinessException);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("All exceptions should be throwable")
    void testExceptionsAreThrowable() {
        // Assert
        assertThrows(ActiveScheduleException.class, () -> {
            throw new ActiveScheduleException();
        });
        
        assertThrows(ClinicInactiveException.class, () -> {
            throw new ClinicInactiveException();
        });
        
        assertThrows(ClinicNotFoundException.class, () -> {
            throw new ClinicNotFoundException();
        });
        
        assertThrows(MedicalOfficeNotFoundException.class, () -> {
            throw new MedicalOfficeNotFoundException();
        });
        
        assertThrows(SpecialtyNotFoundException.class, () -> {
            throw new SpecialtyNotFoundException();
        });
    }

    @Test
    @DisplayName("BusinessException should store status and code")
    void testBusinessExceptionProperties() {
        // Arrange & Act
        BusinessException exception = new ClinicNotFoundException();

        // Assert
        assertNotNull(exception.getStatus());
        assertNotNull(exception.getCode());
        assertNotNull(exception.getMessage());
    }
}
