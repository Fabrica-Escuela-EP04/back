package com.p2f4.med_office.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.p2f4.med_office.core.MedicalOfficeService;
import com.p2f4.med_office.dto.MedicalOfficeDTO;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/medical-offices")

public class MedicalOfficeController {
    
    private final MedicalOfficeService medicalOfficeService;

    public MedicalOfficeController(MedicalOfficeService medicalOfficeService) {
        this.medicalOfficeService = medicalOfficeService;
    }

    @PostMapping("/create")
    public ResponseEntity<MedicalOfficeDTO> createMedicalOffice(
           @Valid @RequestBody MedicalOfficeDTO request) {

        MedicalOfficeDTO created = medicalOfficeService.createMedicalOffice(
                request.getOfficeNumber(),
                request.getIdClinic(),
                request.getIdSpecialty(),
                request.getStatus()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
