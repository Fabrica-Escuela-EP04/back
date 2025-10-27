package com.p2f4.med_office.core;

import com.p2f4.med_office.dto.UserDTO;
import com.p2f4.med_office.entity.User;
import com.p2f4.med_office.domain.UserRepository;
import com.p2f4.med_office.domain.UserRoleRepository;
import com.p2f4.med_office.mapper.UserMapper;
import com.p2f4.med_office.utils.UserNotFoundException;
import com.p2f4.med_office.utils.UserRoleNotFoundException;
import com.p2f4.med_office.utils.InvalidPasswordException;
import com.p2f4.med_office.utils.DuplicatedUserException;
import com.p2f4.med_office.utils.Encryption;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.ScheduledTaskObservationDocumentation;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository,UserRoleRepository userRoleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userMapper = userMapper;
    }

    //Creation of users (For creation only via Postman, no frontEnd in the module)
    //Initial step, set up email and password
    public UserDTO newUser(String email, String password, Short idRole){
        //Not registered users with the same email
        if (userRepository.existsByEmail(email)){
            throw new DuplicatedUserException();
        }
        // Validates correct role
        if (!userRoleRepository.existsById(idRole)){
            throw new UserRoleNotFoundException();
        }

        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setEmail(email);
        newUserDTO.setPassword(Encryption.encryptWord(password));
        newUserDTO.setIdRole(idRole);

        return newUserDTO;

    }

    // Adding user details and save into the database
    public UserDTO createUser(UserDTO newUser, String name, String lastName, String document, String documentType, String phoneNumber){
        
        
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user.setDocument(document);
        user.setDocumentType(documentType);
        user.setPhoneNumber(phoneNumber);
        user.setIdRole(newUser.getIdRole());

        var savedEntity = userRepository.save(user);

        return userMapper.toDTO(savedEntity);

    }

    // Authentication
    public UserDTO validateUser(String userEmail, String userPasword){
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        if(!Encryption.checkPassword(userPasword, user.getPassword())){
            throw new InvalidPasswordException();
        } else{
            return userMapper.toDTO(user);
        }
 
    }
    
}
