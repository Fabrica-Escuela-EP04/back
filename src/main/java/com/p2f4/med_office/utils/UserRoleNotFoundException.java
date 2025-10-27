package com.p2f4.med_office.utils;

import org.springframework.http.HttpStatus;

public class UserRoleNotFoundException extends BusinessException {
  
    public UserRoleNotFoundException() {
        super(HttpStatus.NOT_FOUND, "ROLE_NOT_FOUND", "Rol de usuario no válido");
    }
}
