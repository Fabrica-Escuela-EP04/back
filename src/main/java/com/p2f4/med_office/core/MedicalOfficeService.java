package com.p2f4.med_office.core;

import org.springframework.stereotype.Service;

import com.p2f4.med_office.domain.ClinicRepository;
import com.p2f4.med_office.domain.MedicalOfficeRepository;
import com.p2f4.med_office.domain.SpecialtyRepository;
import com.p2f4.med_office.dto.MedicalOfficeDTO;
import com.p2f4.med_office.entity.Clinic;
import com.p2f4.med_office.mapper.MedicalOfficeMapper;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class MedicalOfficeService {
    private final MedicalOfficeRepository medicalOfficeRepository;
    private final ClinicRepository clinicRepository;
    private final SpecialtyRepository specialtyRepository;
    private final MedicalOfficeMapper medicalOfficeMapper;
    

    public MedicalOfficeService(
        MedicalOfficeRepository medicalOfficeRepository,
        ClinicRepository clinicRepository,
        SpecialtyRepository specialtyRepository,
        MedicalOfficeMapper medicalOfficeMapper) {

        this.medicalOfficeRepository = medicalOfficeRepository;
        this.clinicRepository = clinicRepository;
        this.medicalOfficeMapper = medicalOfficeMapper;
        this.specialtyRepository = specialtyRepository;
    }

    public MedicalOfficeDTO createMedicalOffice(Integer officeNumber, Integer idClinic, Integer idSpecialty, String status) {
        // Validate clinic and specialty existence and status
        Clinic clinic = clinicRepository.findById(idClinic)
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found"));
        specialtyRepository.findById(idSpecialty).orElseThrow(() -> new IllegalArgumentException("Specialty not found"));
        if (!"ACTIVE".equals(clinic.getStatus())) {
            throw new IllegalArgumentException("Clinic is not active");
        }
        // Check for unique office number within the clinic
        if (medicalOfficeRepository.existsByIdClinicAndOfficeNumber(idClinic, officeNumber)) {
            throw new IllegalArgumentException("Office number already exists in the clinic");
        }
        // Validate required fields
        if (officeNumber == null || idClinic == null || idSpecialty == null || status == null) {
            throw new IllegalArgumentException("Missing required fields");
        }
        
        MedicalOfficeDTO dto = new MedicalOfficeDTO();
        dto.setOfficeNumber(officeNumber);
        dto.setIdClinic(idClinic);
        dto.setIdSpecialty(idSpecialty);
        dto.setStatus(status);

        var entity = medicalOfficeMapper.toEntity(dto);
        var savedEntity = medicalOfficeRepository.save(entity);

        return medicalOfficeMapper.toDTO(savedEntity);
    }
    
}
