package com.p2f4.med_office.utils;

import org.springframework.http.HttpStatus;

public class SpecialtyNotFoundException extends BusinessException {
  public SpecialtyNotFoundException() {
    super(HttpStatus.NOT_FOUND, "SPECIALTY_NOT_FOUND", "La Especialidad no existe");
  }
}
