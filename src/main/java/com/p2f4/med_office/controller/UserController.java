package com.p2f4.med_office.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.p2f4.med_office.core.UserService;
import com.p2f4.med_office.dto.UserDTO;
import com.p2f4.med_office.dto.CredentialsDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createMedicalOffice(
           @Valid @RequestBody UserDTO request) {

        UserDTO newUser = userService.newUser(request.getEmail(), request.getPassword(), request.getIdRole());
        
        newUser = userService.createUser(
                newUser,
                request.getName(),
                request.getLastName(),
                request.getDocument(),
                request.getDocumentType(),
                request.getPhoneNumber()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/init-session")
    public ResponseEntity<UserDTO> initSession(@Valid @RequestBody CredentialsDTO request){

        UserDTO userDTO = userService.validateUser(request.getEmail(), request.getPassword());

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

}
