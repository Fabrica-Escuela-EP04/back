package com.p2f4.med_office.core;

import com.p2f4.med_office.dto.SpecialtyDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.p2f4.med_office.domain.SpecialtyRepository;
import com.p2f4.med_office.mapper.SpecialtyMapper;

@Service
@Transactional
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;
    
    @Autowired
    public SpecialtyService(SpecialtyRepository specialtyRepository, SpecialtyMapper specialtyMapper) {
        this.specialtyRepository = specialtyRepository;
        this.specialtyMapper = specialtyMapper;
    }

    public List<SpecialtyDTO> getAllSpecialties(){
        return specialtyRepository.findAll().stream()
        .map(specialtyMapper::toDTO).toList();
    }
    
}
