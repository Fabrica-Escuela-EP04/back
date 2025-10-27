package com.p2f4.med_office.utils;

import org.springframework.http.HttpStatus;

public class DuplicatedUserException extends BusinessException {
  
    public DuplicatedUserException() {
        super(HttpStatus.CONFLICT, "DUPLICATED_USER", "Ya existe un usuario registrado con ese correo");
    }

}
