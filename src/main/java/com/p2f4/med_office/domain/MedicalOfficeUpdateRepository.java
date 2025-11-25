package com.p2f4.med_office.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.p2f4.med_office.entity.MedicalOfficeUpdate;

@Repository
public interface MedicalOfficeUpdateRepository extends JpaRepository<MedicalOfficeUpdate, Integer> {

}
