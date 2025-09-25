package com.p2f4.med_office.core;

import org.springframework.stereotype.Service;

import com.p2f4.med_office.domain.ClinicRepository;
import com.p2f4.med_office.domain.MedicalOfficeRepository;
import com.p2f4.med_office.domain.SpecialtyRepository;
import com.p2f4.med_office.dto.MedicalOfficeDTO;
import com.p2f4.med_office.entity.MedicalOffice;
import com.p2f4.med_office.entity.Clinic;
import com.p2f4.med_office.entity.Specialty;
import com.p2f4.med_office.mapper.MedicalOfficeMapper;
import com.p2f4.med_office.utils.*;

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
    // Creates a medical office using the names for the specialty and the name for the clinic
    public MedicalOfficeDTO createMedicalOffice(Integer officeNumber, String clinicName, String specialtyName, String status){

        Clinic clinic = clinicRepository.findByName(clinicName).orElseThrow(ClinicNotFoundException::new);
        Specialty specialty = specialtyRepository.findBySpecialtyName(specialtyName).orElseThrow(SpecialtyNotFoundException::new);

        if (!"ACTIVE".equalsIgnoreCase(clinic.getStatus())) {
            throw new ClinicInactiveException();
        }

        Integer idClinic = clinic.getIdClinic();
        Integer idSpecialty = specialty.getIdSpecialty();
        System.out.println("id clinic: "+Integer.toString(idClinic));
        System.out.println("id specialty: "+Integer.toString(idSpecialty));

        if (medicalOfficeRepository.existsByIdClinicAndOfficeNumber(idClinic, officeNumber)){
            throw new OfficeNumberDuplicateException();
        }

        MedicalOffice entity = new MedicalOffice();
        entity.setOfficeNumber(officeNumber);
        entity.setIdClinic(idClinic);
        entity.setIdSpecialty(idSpecialty);
        entity.setStatus(status);

        var savedEntity = medicalOfficeRepository.save(entity);

        return medicalOfficeMapper.toDTO(savedEntity);
    } 
    
}
