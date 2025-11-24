package com.p2f4.med_office.core;

import com.p2f4.med_office.dto.UserDTO;
import com.p2f4.med_office.entity.User;
import com.p2f4.med_office.domain.UserRepository;
import com.p2f4.med_office.domain.UserRoleRepository;
import com.p2f4.med_office.mapper.UserMapper;
import com.p2f4.med_office.dto.LoggedUserDTO;
import com.p2f4.med_office.dto.LoginResponse;
import com.p2f4.med_office.utils.UserNotFoundException;
import com.p2f4.med_office.utils.UserRoleNotFoundException;
import com.p2f4.med_office.utils.DuplicatedUserException;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthService(UserRepository userRepository,
                            UserRoleRepository userRoleRepository, 
                            UserMapper userMapper, 
                            PasswordEncoder passwordEnconder, 
                            AuthenticationManager authenticationManager,
                            JwtService jwtService) {
        
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEnconder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    //Creation of users (For creation only via Postman, no frontEnd in the module)
    //Initial step, set up email and password
    public UserDTO createUser(String email, String password, Short idRole, String name, String lastName, String document, String documentType, String phoneNumber){
        //Not registered users with the same email
        if (userRepository.existsByEmail(email)){
            throw new DuplicatedUserException();
        }
        // Validates correct role
        if (!userRoleRepository.existsById(idRole)){
            throw new UserRoleNotFoundException();
        }
        
        // Validate password strength
        validatePasswordStrength(password);

        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setDocument(document);
        user.setDocumentType(documentType);
        user.setPhoneNumber(phoneNumber);
        user.setIdRole(idRole);

        var savedEntity = userRepository.save(user);
        return userMapper.toDTO(savedEntity);

    }
    
    /**
     * Validates password strength according to security best practices
     * @param password The password to validate
     * @throws IllegalArgumentException if password doesn't meet requirements
     */
    private void validatePasswordStrength(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        
        if (password.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }
        
        // Check for at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos una letra mayúscula");
        }
        
        // Check for at least one lowercase letter
        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos una letra minúscula");
        }
        
        // Check for at least one digit
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos un número");
        }
        
        // Check for at least one special character
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos un carácter especial (!@#$%^&*()_+-=[]{}...)");
        }
        
        // Check for common weak passwords
        String[] weakPasswords = {"password", "12345678", "qwerty123", "admin123", "password1"};
        String lowerPassword = password.toLowerCase();
        for (String weak : weakPasswords) {
            if (lowerPassword.contains(weak)) {
                throw new IllegalArgumentException("La contraseña es demasiado común o predecible");
            }
        }
    }

    // Authentication
    public LoginResponse Login(String userEmail, String userPasword){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userEmail, userPasword)
        );

        var user = userRepository.findByEmail(userEmail).orElseThrow(()->new UserNotFoundException());
        
        LoggedUserDTO loggedUser = new LoggedUserDTO();
        loggedUser.setIdUser(user.getIdUser());
        loggedUser.setName(user.getName());
        loggedUser.setLastName(user.getLastName());
        loggedUser.setEmail(user.getEmail());
        loggedUser.setUserRole(user.getUserRole().getName());

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new LoginResponse(token, refreshToken, loggedUser);
 
    }

    // Refresh Token
    public LoginResponse refreshToken(String refreshToken){
        
        String userEmail = jwtService.extractUserEmail(refreshToken);

        if(userEmail == null){
            throw new IllegalArgumentException("Invalid Refresh Token");
        }
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        if (!jwtService.isTokenValid(refreshToken, user)){
            throw new IllegalArgumentException("Invalid Refresh Token");
        }

        LoggedUserDTO loggedUser = new LoggedUserDTO();
        loggedUser.setIdUser(user.getIdUser());
        loggedUser.setName(user.getName());
        loggedUser.setLastName(user.getLastName());
        loggedUser.setEmail(user.getEmail());
        loggedUser.setUserRole(user.getUserRole().getName());

        String accessToken = jwtService.generateToken(user);
        return new LoginResponse(accessToken, refreshToken, loggedUser);

    }
    
}
