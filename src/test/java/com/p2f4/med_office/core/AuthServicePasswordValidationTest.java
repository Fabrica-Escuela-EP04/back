package com.p2f4.med_office.core;

import com.p2f4.med_office.domain.UserRepository;
import com.p2f4.med_office.domain.UserRoleRepository;
import com.p2f4.med_office.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("AuthService - Password Validation Tests")
class AuthServicePasswordValidationTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private UserRoleRepository userRoleRepository;
    
    @Mock
    private UserMapper userMapper;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private JwtService jwtService;
    
    private AuthService authService;
    
    @BeforeEach
    void setUp() {
        authService = new AuthService(
            userRepository,
            userRoleRepository,
            userMapper,
            passwordEncoder,
            authenticationManager,
            jwtService
        );
        
        // Mock común para que las validaciones previas pasen
        lenient().when(userRepository.existsByEmail(anyString())).thenReturn(false);
        lenient().when(userRoleRepository.existsById((short) 1)).thenReturn(true);
        lenient().when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
    }

    @Nested
    @DisplayName("Contraseñas vacías o nulas")
    class EmptyPasswordTests {
        
        @Test
        @DisplayName("Debe rechazar contraseña nula")
        void testNullPassword() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    null,
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña no puede estar vacía", exception.getMessage());
        }
        
        @Test
        @DisplayName("Debe rechazar contraseña vacía")
        void testEmptyPassword() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    "",
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña no puede estar vacía", exception.getMessage());
        }
        
        @Test
        @DisplayName("Debe rechazar contraseña solo con espacios")
        void testWhitespaceOnlyPassword() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    "   ",
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña no puede estar vacía", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Longitud de contraseña")
    class PasswordLengthTests {
        
        @Test
        @DisplayName("Debe rechazar contraseña con menos de 8 caracteres")
        void testShortPassword() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    "Test1!",
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña debe tener al menos 8 caracteres", exception.getMessage());
        }
        
        @Test
        @DisplayName("Debe rechazar contraseña de 7 caracteres")
        void testSevenCharPassword() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    "Test1!a",
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña debe tener al menos 8 caracteres", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Requisitos de mayúsculas")
    class UppercaseRequirementTests {
        
        @Test
        @DisplayName("Debe rechazar contraseña sin mayúsculas")
        void testNoUppercase() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    "test1234!",
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña debe contener al menos una letra mayúscula", exception.getMessage());
        }
        
        @Test
        @DisplayName("Debe rechazar contraseña solo con minúsculas y números")
        void testOnlyLowercaseAndDigits() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    "abcd1234!@",
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña debe contener al menos una letra mayúscula", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Requisitos de minúsculas")
    class LowercaseRequirementTests {
        
        @Test
        @DisplayName("Debe rechazar contraseña sin minúsculas")
        void testNoLowercase() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    "TEST1234!",
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña debe contener al menos una letra minúscula", exception.getMessage());
        }
        
        @Test
        @DisplayName("Debe rechazar contraseña solo con mayúsculas y números")
        void testOnlyUppercaseAndDigits() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    "ABCD1234!@",
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña debe contener al menos una letra minúscula", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Requisitos de números")
    class DigitRequirementTests {
        
        @Test
        @DisplayName("Debe rechazar contraseña sin números")
        void testNoDigits() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    "TestTest!",
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña debe contener al menos un número", exception.getMessage());
        }
        
        @Test
        @DisplayName("Debe rechazar contraseña solo con letras y símbolos")
        void testOnlyLettersAndSymbols() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    "TestTest!@#",
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña debe contener al menos un número", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Requisitos de caracteres especiales")
    class SpecialCharacterRequirementTests {
        
        @Test
        @DisplayName("Debe rechazar contraseña sin caracteres especiales")
        void testNoSpecialCharacters() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    "Test1234",
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña debe contener al menos un carácter especial (!@#$%^&*()_+-=[]{}...)", exception.getMessage());
        }
        
        @Test
        @DisplayName("Debe rechazar contraseña alfanumérica sin símbolos")
        void testAlphanumericOnly() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    "TestPass1234",
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña debe contener al menos un carácter especial (!@#$%^&*()_+-=[]{}...)", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Contraseñas comunes y débiles")
    class WeakPasswordTests {
        
        @Test
        @DisplayName("Debe rechazar contraseña 'password' aunque cumpla otros requisitos")
        void testPasswordVariant() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    "Password123!",
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña es demasiado común o predecible", exception.getMessage());
        }
        
        @Test
        @DisplayName("Debe rechazar contraseña '12345678' con letras")
        void testCommonNumericPattern() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    "Aa12345678!",
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña es demasiado común o predecible", exception.getMessage());
        }
        
        @Test
        @DisplayName("Debe rechazar contraseña 'qwerty123' aunque cumpla requisitos")
        void testQwertyPattern() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    "Qwerty123!",
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña es demasiado común o predecible", exception.getMessage());
        }
        
        @Test
        @DisplayName("Debe rechazar contraseña 'admin123' variante")
        void testAdminPattern() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    "Admin123!",
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña es demasiado común o predecible", exception.getMessage());
        }
        
        @Test
        @DisplayName("Debe rechazar contraseña 'password1' variante")
        void testPassword1Variant() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.createUser(
                    "test@example.com",
                    "Password1!",
                    (short) 1,
                    "Test",
                    "User",
                    "12345678",
                    "CC",
                    "3001234567"
                )
            );
            assertEquals("La contraseña es demasiado común o predecible", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Contraseñas válidas - casos exitosos")
    class ValidPasswordTests {
        
        @Test
        @DisplayName("Debe aceptar contraseña válida fuerte")
        void testValidStrongPassword() {
            assertDoesNotThrow(() -> authService.createUser(
                "test@example.com",
                "MyStr0ng!Pass",
                (short) 1,
                "Test",
                "User",
                "12345678",
                "CC",
                "3001234567"
            ));
        }
        
        @Test
        @DisplayName("Debe aceptar contraseña con múltiples símbolos")
        void testValidPasswordWithMultipleSymbols() {
            assertDoesNotThrow(() -> authService.createUser(
                "test@example.com",
                "C0mpl3x!@#Pass",
                (short) 1,
                "Test",
                "User",
                "12345678",
                "CC",
                "3001234567"
            ));
        }
        
        @Test
        @DisplayName("Debe aceptar contraseña larga y compleja")
        void testValidLongPassword() {
            assertDoesNotThrow(() -> authService.createUser(
                "test@example.com",
                "MyV3ry!Str0ng&SecureP@ssw0rd",
                (short) 1,
                "Test",
                "User",
                "12345678",
                "CC",
                "3001234567"
            ));
        }
    }
}
