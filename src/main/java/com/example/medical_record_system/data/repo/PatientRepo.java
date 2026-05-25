package com.example.medical_record_system.data.repo;

import com.example.medical_record_system.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepo extends JpaRepository<Patient, Long> {
    List<Patient> findAllByGpId(Long gpId);
    Patient findByUsername(String username);
}
