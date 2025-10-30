package com.p2f4.med_office.domain;

import com.p2f4.med_office.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
    //Custom query, find by clinic name
    public Optional<Clinic> findByName(String clinicName);
    public Optional<Clinic> findByNameIgnoreCase(String clinicName);
}
