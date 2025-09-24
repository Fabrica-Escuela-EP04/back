package com.p2f4.med_office.domain;

import com.p2f4.med_office.entity.MedicalOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalOfficeRepository extends JpaRepository<MedicalOffice, Integer> {
    // Custom query to check for unique office number within a clinic
  boolean existsByIdClinicAndOfficeNumber(Integer idClinic, Integer officeNumber);
}

