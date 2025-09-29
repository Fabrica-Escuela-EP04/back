package com.p2f4.med_office.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.p2f4.med_office.core.UserService;
import com.p2f4.med_office.dto.UserDTO;
import com.p2f4.med_office.dto.CredentialsDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createMedicalOffice(
           @Valid @RequestBody UserDTO request) {

        UserDTO created = userService.createUser(
                request.getName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                request.getDocument(),
                request.getDocumentType(),
                request.getPhoneNumber(),
                request.getIdRole()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/init-session")
    public ResponseEntity<UserDTO> initSession(@Valid @RequestBody CredentialsDTO request){

        UserDTO userDTO = userService.validateUser(request.getEmail(), request.getPassword());

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

}
