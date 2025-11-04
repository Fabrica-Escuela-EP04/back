package com.p2f4.med_office.utils;

import org.springframework.http.HttpStatus;

public class NonCoungruentDatesException extends BusinessException{
    
    public NonCoungruentDatesException(){
        super(HttpStatus.CONFLICT, "NON_CONGRUENT_DATE", "La fecha de finalización debe ser posterior a la de incio");
    }
}
