package com.p2f4.med_office.utils;

import org.springframework.http.HttpStatus;

public class ActiveScheduleException extends BusinessException {
    public ActiveScheduleException(){
        super(HttpStatus.CONFLICT, "ACTIVE_SCHEDULE", "Ya existe un mantenimiento activo");
    }
}
