package com.example.medical_record_system.data.repo;

import com.example.medical_record_system.data.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepo extends JpaRepository<Doctor, Long> {
}
