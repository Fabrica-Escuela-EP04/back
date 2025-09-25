package com.p2f4.med_office.domain;

import com.p2f4.med_office.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {
    // custom query finding an specialty by its name
    public Optional<Specialty> findBySpecialtyName(String specialtyName);
    
}
