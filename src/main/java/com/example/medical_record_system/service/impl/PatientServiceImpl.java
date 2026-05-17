package com.example.medical_record_system.service.impl;

import com.example.medical_record_system.data.entity.Patient;
import com.example.medical_record_system.data.repo.PatientRepo;
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
    private final MapperUtil mapperUtil;

    @Override
    public PatientDto createPatient(PatientDto patient) {
        return mapperUtil.getModelMapper()
                .map(this.patientRepo
                        .save(mapperUtil.getModelMapper()
                                .map(patient, Patient.class)), PatientDto.class);
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
    public Patient updatePatient(Patient patient, long id) {
        return this.patientRepo.findById(id).map(
                patient1 ->{
                    patient1.setName(patient.getName());
                    patient1.setUcn(patient.getUcn());
                    patient1.setIsInsured(patient.getIsInsured());
                    patient1.setGp(patient.getGp());
                    patient1.setVisits(patient.getVisits());
                    return this.patientRepo.save(patient1);
                }
        ).orElseGet(
                () -> this.patientRepo.save(patient)
        );
    }

    @Override
    public void deletePatient(long id) {
        this.patientRepo.deleteById(id);
    }
}
