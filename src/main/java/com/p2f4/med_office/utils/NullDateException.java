package com.p2f4.med_office.utils;

import org.springframework.http.HttpStatus;

public class NullDateException extends BusinessException {

    public NullDateException(){
        super(HttpStatus.BAD_REQUEST, "NULL_DATE", "Fechas no pueden ser nulas");
    }

}
