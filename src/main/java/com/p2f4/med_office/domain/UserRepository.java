package com.p2f4.med_office.domain;

import com.p2f4.med_office.entity.User;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Custom query, find by the user email
    public Optional<User> findByEmail(String email);

    // Custom query, validates if there is an user with some email
    public boolean existsByEmail(String email);
}
