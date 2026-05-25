package com.example.medical_record_system.service.impl;

import com.example.medical_record_system.data.entity.Doctor;
import com.example.medical_record_system.data.entity.Patient;
import com.example.medical_record_system.data.repo.DoctorRepo;
import com.example.medical_record_system.data.repo.PatientRepo;
import com.example.medical_record_system.dto.CreatePatientDto;
import com.example.medical_record_system.dto.PatientDto;
import com.example.medical_record_system.exception.DoctorNotFoundException;
import com.example.medical_record_system.exception.PatientNotFoundException;
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
        Patient patientEntity = mapperUtil.getModelMapper().map(patient, Patient.class);

        Doctor gp = this.doctorRepo.findById(patient.getGpId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with id=" + patient.getGpId() + " not found!"));

        patientEntity.setGp(gp);

        return mapperUtil.getModelMapper().map(
                this.patientRepo.save(patientEntity),
                CreatePatientDto.class
        );
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
                        .orElseThrow(() -> new PatientNotFoundException("Patient with id=" + id + " not found!")),
                PatientDto.class
        );
    }

    @Override
    public Patient updatePatient(PatientDto patient, long id) {
        return this.patientRepo.findById(id)
                .map(patientEntity -> {
                    Doctor gp = doctorRepo.findById(patient.getGpId())
                            .orElseThrow(() -> new DoctorNotFoundException("Doctor with id=" + patient.getGpId() + " not found!"));

                    patientEntity.setName(patient.getName());
                    patientEntity.setUcn(patient.getUcn());
                    patientEntity.setUsername(patient.getUsername());
                    patientEntity.setIsInsured(patient.getIsInsured());
                    patientEntity.setGp(gp);

                    return this.patientRepo.save(patientEntity);
                })
                .orElseThrow(() -> new PatientNotFoundException("Patient with id=" + id + " not found!"));
    }

    @Override
    public void deletePatient(long id) {
        if (!this.patientRepo.existsById(id)) {
            throw new PatientNotFoundException("Patient with id=" + id + " not found!");
        }

        this.patientRepo.deleteById(id);
    }
}
