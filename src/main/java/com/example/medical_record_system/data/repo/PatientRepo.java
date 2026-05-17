package com.example.medical_record_system.data.repo;

import com.example.medical_record_system.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient, Long> {
}
