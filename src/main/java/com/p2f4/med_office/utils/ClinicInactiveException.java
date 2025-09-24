package com.p2f4.med_office.utils;

import org.springframework.http.HttpStatus;

public class ClinicInactiveException extends BusinessException {
  public ClinicInactiveException() {
    super(HttpStatus.BAD_REQUEST, "CLINIC_INACTIVE", "La Sede está inactiva, no se pueden agregar consultorios");
  }
}