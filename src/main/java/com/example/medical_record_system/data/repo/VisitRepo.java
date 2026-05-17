package com.example.medical_record_system.data.repo;

import com.example.medical_record_system.data.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepo extends JpaRepository<Visit, Long> {
}
