package com.p2f4.med_office.core;

import org.springframework.stereotype.Service;
import com.p2f4.med_office.domain.MedicalOfficeRepository;

@Service
public class MedicalOfficeService {
    private final MedicalOfficeRepository medicalOfficeRepository;

    public MedicalOfficeService(MedicalOfficeRepository medicalOfficeRepository) {
        this.medicalOfficeRepository = medicalOfficeRepository;
    }

    
}
