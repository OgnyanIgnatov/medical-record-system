package com.example.medical_record_system.service;

import com.example.medical_record_system.data.entity.Patient;
import com.example.medical_record_system.dto.PatientDto;

import java.util.List;

public interface PatientService {

    PatientDto createPatient(PatientDto patient);

    List<PatientDto> getPatients();

    PatientDto getPatient(long id);

    Patient updatePatient(Patient patient, long id);

    void deletePatient(long id);
}
