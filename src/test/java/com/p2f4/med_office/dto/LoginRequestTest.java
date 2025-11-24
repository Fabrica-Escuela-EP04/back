package com.p2f4.med_office.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    @Test
    void testDefaultConstructor() {
        // Act
        LoginRequest loginRequest = new LoginRequest();
        
        // Assert
        assertNotNull(loginRequest);
        assertNull(loginRequest.getEmail());
        assertNull(loginRequest.getPassword());
    }

    @Test
    void testParameterizedConstructor() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        
        // Act
        LoginRequest loginRequest = new LoginRequest(email, password);
        
        // Assert
        assertNotNull(loginRequest);
        assertEquals(email, loginRequest.getEmail());
        assertEquals(password, loginRequest.getPassword());
    }

    @Test
    void testSetters() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        String email = "user@test.com";
        String password = "pass456";
        
        // Act
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        
        // Assert
        assertEquals(email, loginRequest.getEmail());
        assertEquals(password, loginRequest.getPassword());
    }
}
