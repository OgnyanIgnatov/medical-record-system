package com.example.medical_record_system.service.impl;

import com.example.medical_record_system.data.repo.PatientRepo;
import com.example.medical_record_system.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;
}
