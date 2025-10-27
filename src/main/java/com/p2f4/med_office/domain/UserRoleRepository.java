package com.p2f4.med_office.domain;

import com.p2f4.med_office.entity.UserRole;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Short> {

}


