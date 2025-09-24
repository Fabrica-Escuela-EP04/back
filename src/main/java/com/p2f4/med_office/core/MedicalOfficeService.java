package com.p2f4.med_office.core;

import org.springframework.stereotype.Service;

import com.p2f4.med_office.domain.ClinicRepository;
import com.p2f4.med_office.domain.MedicalOfficeRepository;
import com.p2f4.med_office.domain.SpecialtyRepository;
import com.p2f4.med_office.dto.MedicalOfficeDTO;
import com.p2f4.med_office.entity.Clinic;
import com.p2f4.med_office.entity.Specialty;
import com.p2f4.med_office.mapper.MedicalOfficeMapper;
import com.p2f4.med_office.utils.ClinicInactiveException;
import com.p2f4.med_office.utils.ClinicNotFoundException;
import com.p2f4.med_office.utils.OfficeNumberDuplicateException;
import com.p2f4.med_office.utils.SpecialtyNotFoundException;

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
        Clinic clinic = clinicRepository.findById(idClinic).orElseThrow(ClinicNotFoundException::new);
        Specialty specialty = specialtyRepository.findById(idSpecialty).orElseThrow(SpecialtyNotFoundException::new);
        if (!"ACTIVE".equalsIgnoreCase(clinic.getStatus())) {
            throw new ClinicInactiveException();
        }
        // Check for unique office number within the clinic
        if (medicalOfficeRepository.existsByIdClinicAndOfficeNumber(idClinic, officeNumber)) {
            throw new OfficeNumberDuplicateException();
        }
        
        MedicalOfficeDTO dto = new MedicalOfficeDTO();
        dto.setOfficeNumber(officeNumber);
        dto.setIdClinic(idClinic);
        dto.setIdSpecialty(idSpecialty);
        dto.setStatus(status);

        var entity = medicalOfficeMapper.toEntity(dto);
        entity.setClinic(clinic);  
        entity.setSpecialty(specialty);
        var savedEntity = medicalOfficeRepository.save(entity);

        return medicalOfficeMapper.toDTO(savedEntity);
    }
    
}
