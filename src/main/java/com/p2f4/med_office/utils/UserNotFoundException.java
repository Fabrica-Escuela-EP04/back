package com.p2f4.med_office.utils;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BusinessException {
  
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "Usuario no registrado");
    }

}

