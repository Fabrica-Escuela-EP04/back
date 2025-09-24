package com.p2f4.med_office.utils;

import org.springframework.http.HttpStatus;

public class OfficeNumberDuplicateException extends BusinessException {
  public OfficeNumberDuplicateException() {
    super(HttpStatus.CONFLICT, "OFFICE_DUPLICATE", "El número de consultorio ya existe en la Sede");
  }
}