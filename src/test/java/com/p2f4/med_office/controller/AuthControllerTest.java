package com.p2f4.med_office.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.p2f4.med_office.config.TestSecurityConfig;
import com.p2f4.med_office.core.AuthService;
import com.p2f4.med_office.dto.LoggedUserDTO;
import com.p2f4.med_office.dto.LoginRequest;
import com.p2f4.med_office.dto.LoginResponse;
import com.p2f4.med_office.dto.RefreshTokenRequest;
import com.p2f4.med_office.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for AuthController
 * Tests authentication endpoints including register, login, refresh token and logout
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
@TestPropertySource(properties = {
    "spring.application.security.jwt.expiration=3600000",
    "spring.application.security.jwt.refresh-token.expiration=86400000",
    "spring.application.security.cookies.auth-cookie.name=access_token",
    "spring.application.security.cookies.refresh-cookie.name=refresh_token"
})
@DisplayName("Auth Controller Tests")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    private UserDTO userDTO;
    private LoginRequest loginRequest;
    private LoginResponse loginResponse;
    private LoggedUserDTO loggedUserDTO;

    @BeforeEach
    void setUp() {
        // Setup common test data
        userDTO = new UserDTO();
        userDTO.setIdUser(1);
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password123");
        userDTO.setIdRole((short) 1);
        userDTO.setName("Test");
        userDTO.setLastName("User");
        userDTO.setDocument("1234567890");
        userDTO.setDocumentType("CC");
        userDTO.setPhoneNumber("3001234567");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        loggedUserDTO = new LoggedUserDTO();
        loggedUserDTO.setIdUser(1);
        loggedUserDTO.setEmail("test@example.com");
        loggedUserDTO.setName("Test");
        loggedUserDTO.setLastName("User");
        loggedUserDTO.setUserRole("ADMIN");

        loginResponse = new LoginResponse();
        loginResponse.setToken("mock.jwt.token");
        loginResponse.setRefreshToken("mock.refresh.token");
        loginResponse.setLoggedUserDTO(loggedUserDTO);
    }

    @Test
    @DisplayName("Should register a new user successfully")
    void shouldRegisterNewUserSuccessfully() throws Exception {
        // Arrange
        when(authService.createUser(
            anyString(), anyString(), anyShort(), anyString(), 
            anyString(), anyString(), anyString(), anyString()
        )).thenReturn(userDTO);

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUser").value(1))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.lastName").value("User"));
    }

    @Test
    @DisplayName("Should login successfully with valid credentials")
    void shouldLoginSuccessfully() throws Exception {
        // Arrange
        when(authService.Login(anyString(), anyString())).thenReturn(loginResponse);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUser").value(1))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(header().exists("Set-Cookie"));
    }

    @Test
    @DisplayName("Should refresh token successfully")
    void shouldRefreshTokenSuccessfully() throws Exception {
        // Arrange
        RefreshTokenRequest refreshRequest = new RefreshTokenRequest();
        refreshRequest.setRefreshToken("valid.refresh.token");
        
        when(authService.refreshToken(anyString())).thenReturn(loginResponse);

        // Act & Assert
        mockMvc.perform(post("/api/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(refreshRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUser").value(1))
                .andExpect(header().exists("Set-Cookie"));
    }

    @Test
    @DisplayName("Should logout successfully")
    void shouldLogoutSuccessfully() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/auth/logout")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(header().exists("Set-Cookie"));
    }
}
