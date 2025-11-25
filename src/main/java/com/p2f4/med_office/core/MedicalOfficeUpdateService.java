package com.p2f4.med_office.core;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.p2f4.med_office.domain.MedicalOfficeRepository;
import com.p2f4.med_office.domain.MedicalOfficeUpdateRepository;
import com.p2f4.med_office.domain.UserRepository;
import com.p2f4.med_office.entity.MedicalOfficeUpdate;


import jakarta.transaction.Transactional;

@Service
@Transactional
public class MedicalOfficeUpdateService {
    private final MedicalOfficeUpdateRepository medicalOfficeUpdateRepository;
    private final UserRepository userRepository;
    private final MedicalOfficeRepository medicalOfficeRepository;

    
public MedicalOfficeUpdateService(MedicalOfficeUpdateRepository medicalOfficeUpdateRepository, UserRepository userRepository, MedicalOfficeRepository medicalOfficeRepository) {
    this.medicalOfficeUpdateRepository = medicalOfficeUpdateRepository;
    this.userRepository = userRepository;
    this.medicalOfficeRepository = medicalOfficeRepository;
}

// Insert log of medical office updates
public void insertUpdateLog(
    Integer idUser,
    Integer idOffice,
    String previousClinic,
    String newClinic,
    String previousSpecialty,
    String newSpecialty,
    String previousStatus,
    String newStatus,
    Integer previousOfficeNumber,
    Integer newOfficeNumber) {

    MedicalOfficeUpdate updateLog = new MedicalOfficeUpdate();
    updateLog.setIdUser(userRepository.findById(idUser).orElse(null));
    updateLog.setIdOffice(medicalOfficeRepository.findById(idOffice).orElse(null));
    updateLog.setPreviousClinic(previousClinic);
    updateLog.setNewClinic(newClinic);
    updateLog.setPreviousSpecialty(previousSpecialty);
    updateLog.setNewSpecialty(newSpecialty);
    updateLog.setPreviousStatus(previousStatus);
    updateLog.setNewStatus(newStatus);
    updateLog.setPreviousOfficeNumber(previousOfficeNumber);
    updateLog.setNewOfficeNumber(newOfficeNumber);
    updateLog.setModificationDate(LocalDateTime.now());
    
    medicalOfficeUpdateRepository.save(updateLog);
}
}