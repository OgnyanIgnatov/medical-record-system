package com.example.medical_record_system.data.repo;

import com.example.medical_record_system.data.entity.SickNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SickNoteRepo extends JpaRepository<SickNote, Long> {
    List<SickNote> findAllByVisitDoctorId(Long doctorId);
}
