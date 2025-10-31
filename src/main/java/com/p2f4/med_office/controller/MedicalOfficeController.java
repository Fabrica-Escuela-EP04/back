package com.p2f4.med_office.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.p2f4.med_office.core.*;

import com.p2f4.med_office.dto.MedicalOfficeDTO;
import com.p2f4.med_office.dto.MedicalOfficeParamsDTO;
import com.p2f4.med_office.dto.MedicalInformationDTO;
import com.p2f4.med_office.dto.ClinicDTO;
import com.p2f4.med_office.dto.SpecialtyDTO;

import java.util.List;

import jakarta.validation.Valid;

import static com.p2f4.med_office.config.ApiConfiguration.API_BASE_PATH;

@RestController
@RequestMapping(API_BASE_PATH + "/medical-offices")
public class MedicalOfficeController {
    
    private final MedicalOfficeService medicalOfficeService;
    private final SpecialtyService specialtyService;
    private final ClinicService clinicService;

    public MedicalOfficeController(MedicalOfficeService medicalOfficeService, SpecialtyService specialtyService, ClinicService clinicService) {
        this.medicalOfficeService = medicalOfficeService;
        this.clinicService = clinicService;
        this.specialtyService = specialtyService;
    }

    @GetMapping
    public ResponseEntity<MedicalInformationDTO> findClinicsAndSpecialties(){ 
       
        List<ClinicDTO> clinics = clinicService.getAllClinics();
        List<SpecialtyDTO> specialties = specialtyService.getAllSpecialties();

        MedicalInformationDTO medicalInformationDTO = new MedicalInformationDTO();
        medicalInformationDTO.setClinics(clinics);
        medicalInformationDTO.setSpecialties(specialties);

        return ResponseEntity.status(HttpStatus.OK).body(medicalInformationDTO);
        
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
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

    @PostMapping("/create-by-name")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<MedicalOfficeDTO> createMedicalOfficeByName(
           @Valid @RequestBody MedicalOfficeParamsDTO request) {

        MedicalOfficeDTO created = medicalOfficeService.createMedicalOffice(
                request.getOfficeNumber(),
                request.getClinicName(),
                request.getSpecialtyName(),
                request.getStatus()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/update/{idOffice}")
    public ResponseEntity<MedicalOfficeDTO> updateMedicalOffice(
            @PathVariable Integer idOffice,
            @Valid @RequestBody MedicalOfficeParamsDTO request) {

        MedicalOfficeDTO updated = medicalOfficeService.updateMedicalOffice(
                idOffice,
                request.getOfficeNumber(),
                request.getClinicName(),
                request.getSpecialtyName(),
                request.getStatus()
        );

        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

}
