package com.example.medical_record_system.service.impl;

import com.example.medical_record_system.data.entity.Doctor;
import com.example.medical_record_system.data.entity.Patient;
import com.example.medical_record_system.data.repo.DoctorRepo;
import com.example.medical_record_system.data.repo.PatientRepo;
import com.example.medical_record_system.dto.CreatePatientDto;
import com.example.medical_record_system.dto.PatientDto;
import com.example.medical_record_system.service.PatientService;
import com.example.medical_record_system.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;
    private final MapperUtil mapperUtil;

    @Override
    public CreatePatientDto createPatient(CreatePatientDto patient) {
        Patient patient1 = mapperUtil.getModelMapper().map(patient, Patient.class);
        patient1.setGp(
                this.doctorRepo.findById(patient.getGpId()).orElseThrow(()-> new RuntimeException("Cant Find Doctor"))
        );
        return mapperUtil.getModelMapper().map(this.patientRepo.save(patient1), CreatePatientDto.class);
    }

    @Override
    public List<PatientDto> getPatients() {
        return mapperUtil.mapList(
                this.patientRepo.findAll(), PatientDto.class
        );
    }

    @Override
    public PatientDto getPatient(long id) {
        return mapperUtil.getModelMapper().map(
                this.patientRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("There is not a patient with given id")), PatientDto.class
        );
    }

    @Override
    public Patient updatePatient(PatientDto patient, long id) {
        return this.patientRepo.findById(id).map(patient1 -> {
            patient1.setName(patient.getName());
            patient1.setUcn(patient.getUcn());
            patient1.setIsInsured(patient.getIsInsured());
            Doctor gp = doctorRepo.findById(patient.getGpId())
                        .orElseThrow(() -> new RuntimeException("Doctor not found"));
                patient1.setGp(gp);
            return this.patientRepo.save(patient1);
        }).orElseGet(() -> this.patientRepo.save(this.mapperUtil.getModelMapper().map(
                patient, Patient.class
        )));
    }

    @Override
    public void deletePatient(long id) {
        this.patientRepo.deleteById(id);
    }
}
