package com.p2f4.med_office.core;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.p2f4.med_office.domain.ClinicRepository;
import com.p2f4.med_office.domain.MedicalOfficeRepository;
import com.p2f4.med_office.domain.SpecialtyRepository;
import com.p2f4.med_office.dto.MedicalOfficeDTO;
import com.p2f4.med_office.dto.MedicalOfficeDataUpdatableDTO;
import com.p2f4.med_office.dto.MedicalOfficeParamsDTO;
import com.p2f4.med_office.dto.ScheduleDTO;
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
    private final ScheduleService scheduleService;

    public MedicalOfficeService(
        MedicalOfficeRepository medicalOfficeRepository,
        ClinicRepository clinicRepository,
        SpecialtyRepository specialtyRepository,
        MedicalOfficeMapper medicalOfficeMapper,
        ScheduleService scheduleService){

        this.medicalOfficeRepository = medicalOfficeRepository;
        this.clinicRepository = clinicRepository;
        this.medicalOfficeMapper = medicalOfficeMapper;
        this.specialtyRepository = specialtyRepository;
        this.scheduleService = scheduleService;
    }

    // Get all medical offices only with own parameters
    public List<MedicalOfficeParamsDTO> getAllMedicalOffices() {
        return medicalOfficeRepository.findAll().stream()
                .map(medicalOfficeMapper::toParamsDTO) // <-- usar el método válido del mapper
                .toList();
    }
    // Merge medical offices with schedules
    public List<MedicalOfficeDataUpdatableDTO> mergeMedicalOfficesWithSchedules(List<MedicalOfficeParamsDTO> medicalOffices, List<ScheduleDTO> schedules) {
        return medicalOffices.stream().map(medicalOffice -> {
            MedicalOfficeDataUpdatableDTO updatableDTO = new MedicalOfficeDataUpdatableDTO();
            updatableDTO.setOfficeNumber(medicalOffice.getOfficeNumber());
            updatableDTO.setClinicName(medicalOffice.getClinicName());
            updatableDTO.setSpecialtyName(medicalOffice.getSpecialtyName());
            updatableDTO.setStatus(medicalOffice.getStatus());

            // Find matching schedule
            ScheduleDTO matchingSchedule = schedules.stream()
                    .filter(schedule -> schedule.getIdOffice().equals(medicalOffice.getIdOffice()))
                    .findFirst()
                    .orElse(null);

            if (matchingSchedule != null) {
                updatableDTO.setStartDate(matchingSchedule.getStartDate());
                updatableDTO.setEndDate(matchingSchedule.getEndDate());
            }

            return updatableDTO;
        }).toList();
    }
    // Creates a medical office using the ids for the specialty and the clinic
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

    // Updates a medical office using a office number, the names for the specialty, the name for the clinic and the status
    public MedicalOfficeDTO updateMedicalOffice(Integer idMedicalOffice, Integer officeNumber, String clinicName, String specialtyName, String status, LocalDate startDate, LocalDate endDate) {

        //Verify medical office, clinic and specialty existence
        MedicalOffice oldMedicalOffice = medicalOfficeRepository.findById(idMedicalOffice).orElseThrow(MedicalOfficeNotFoundException::new);
        Clinic clinic = clinicRepository.findByNameIgnoreCase(clinicName).orElseThrow(ClinicNotFoundException::new);
        System.out.println("Buscando especialidad con nombre: [" + specialtyName + "]");
        Specialty specialty = specialtyRepository.findBySpecialtyNameIgnoreCase(specialtyName).orElseThrow(SpecialtyNotFoundException::new);
        // Check clinic status
        if (!"ACTIVE".equalsIgnoreCase(clinic.getStatus())) {throw new ClinicInactiveException();}

        // If status is MANTENIMIENTO, create maintenance schedule
        if(status.equalsIgnoreCase("MANTENIMIENTO")){
            scheduleService.createMaintenanceSchedule(
                0,
                "MANTENIMIENTO",
                oldMedicalOffice.getIdOffice(),
                startDate,
                endDate
            );
        }
        // Normalize status
        String normalizedStatus = status == null ? null : status.trim().toUpperCase();

    
        // Check for unique office number within the clinic
        boolean alreadyExists = medicalOfficeRepository.existsByIdClinicAndOfficeNumber(clinic.getIdClinic(), officeNumber);
        boolean changedNumber = oldMedicalOffice.getOfficeNumber() == officeNumber;
        if (alreadyExists && !changedNumber) {
            throw new OfficeNumberDuplicateException();
        }
        // Update medical office details
        oldMedicalOffice.setOfficeNumber(officeNumber);
        oldMedicalOffice.setIdClinic(clinic.getIdClinic());
        oldMedicalOffice.setIdSpecialty(specialty.getIdSpecialty());
        oldMedicalOffice.setStatus(normalizedStatus);

        var updatedEntity = medicalOfficeRepository.save(oldMedicalOffice); 
        return medicalOfficeMapper.toDTO(updatedEntity);
    }
}
