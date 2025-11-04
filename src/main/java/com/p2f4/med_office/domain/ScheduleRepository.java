package com.p2f4.med_office.domain;

import com.p2f4.med_office.entity.Schedule;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

}