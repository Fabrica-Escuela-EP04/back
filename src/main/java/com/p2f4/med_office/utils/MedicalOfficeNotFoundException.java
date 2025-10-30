package com.p2f4.med_office.utils;

import org.springframework.http.HttpStatus;

public class MedicalOfficeNotFoundException extends BusinessException {
  public MedicalOfficeNotFoundException() {
    super(HttpStatus.NOT_FOUND, "CLINIC_NOT_FOUND", "La Sede no existe");
  }
}
