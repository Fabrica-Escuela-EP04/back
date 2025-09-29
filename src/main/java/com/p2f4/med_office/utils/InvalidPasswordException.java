package com.p2f4.med_office.utils;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends BusinessException {
  
    public InvalidPasswordException() {
        super(HttpStatus.BAD_REQUEST, "INVALID_PASSWORD", "Nombre de usuario o contraseña erroneos");
    }
    
}
