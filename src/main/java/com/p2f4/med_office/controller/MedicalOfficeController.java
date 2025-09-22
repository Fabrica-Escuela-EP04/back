package com.p2f4.med_office.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.p2f4.med_office.core.MedicalOfficeService;

@RestController
@RequestMapping("/medical-offices")
public class MedicalOfficeController {
    
    private final MedicalOfficeService medicalOfficeService;

    public MedicalOfficeController(MedicalOfficeService medicalOfficeService) {
        this.medicalOfficeService = medicalOfficeService;
    }

    
}
