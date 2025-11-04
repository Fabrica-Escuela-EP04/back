package com.p2f4.med_office.core;

import com.p2f4.med_office.dto.ClinicDTO;
import com.p2f4.med_office.domain.ClinicRepository;
import com.p2f4.med_office.mapper.ClinicMapper;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@Transactional
public class ClinicService {

    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;

    @Autowired
    public ClinicService(ClinicRepository clinicRepository, ClinicMapper clinicMapper) {
        this.clinicRepository = clinicRepository;
        this.clinicMapper = clinicMapper;
    }

    public List<ClinicDTO> getAllClinics(){
        return this.clinicRepository.findAll().stream()
        .map(this.clinicMapper::toDTO).toList();

    }

}
