package com.p2f4.med_office.utils;

import org.springframework.http.HttpStatus;

public class InsecurePasswordException extends BusinessException {
  
    public InsecurePasswordException(String message) {
        super(HttpStatus.BAD_REQUEST, "INSECURE_PASSWORD", message);
    }
    
}
