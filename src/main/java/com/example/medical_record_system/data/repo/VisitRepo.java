package com.example.medical_record_system.data.repo;

import com.example.medical_record_system.data.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface VisitRepo extends JpaRepository<Visit, Long> {
    List<Visit> findAllByPatientId(Long patientId);
    List<Visit> findAllByDoctorId(Long doctorId);
    List<Visit> findAllByDateBetween(Date startDate, Date endDate);
    List<Visit> findAllByDoctorIdAndDateBetween(Long doctorId, Date startDate, Date endDate);
    List<Visit> findAllByDiagnosisContainsIgnoreCase(String diagnosis);
    List<Visit> findAllByPatientIsInsuredFalse();
    List<Visit> findAllByDoctorIdAndPatientIsInsuredFalse(Long doctorId);

}
