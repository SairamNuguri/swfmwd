package com.example.swfwmd_demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermitRepository extends JpaRepository<Permit, Long> {
    // No extra methods needed for basic CRUD
}

