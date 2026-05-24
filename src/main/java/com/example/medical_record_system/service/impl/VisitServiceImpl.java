package com.example.medical_record_system.service.impl;

import com.example.medical_record_system.data.entity.Doctor;
import com.example.medical_record_system.data.entity.Patient;
import com.example.medical_record_system.data.entity.Visit;
import com.example.medical_record_system.data.repo.DoctorRepo;
import com.example.medical_record_system.data.repo.PatientRepo;
import com.example.medical_record_system.data.repo.VisitRepo;
import com.example.medical_record_system.dto.CreateVisitDto;
import com.example.medical_record_system.dto.VisitDto;
import com.example.medical_record_system.exception.DoctorNotFoundException;
import com.example.medical_record_system.exception.PatientNotFoundException;
import com.example.medical_record_system.exception.VisitNotFoundException;
import com.example.medical_record_system.service.VisitService;
import com.example.medical_record_system.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {
    private final VisitRepo visitRepo;
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;
    private final MapperUtil mapperUtil;

    @Override
    public CreateVisitDto createVisit(CreateVisitDto visit) {
        Visit visitEntity = mapperUtil.getModelMapper().map(visit, Visit.class);

        Patient patient = patientRepo.findById(visit.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient with id=" + visit.getPatientId() + " not found!"));

        Doctor doctor = doctorRepo.findById(visit.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with id=" + visit.getDoctorId() + " not found!"));

        visitEntity.setPatient(patient);
        visitEntity.setDoctor(doctor);

        return mapperUtil.getModelMapper().map(
                this.visitRepo.save(visitEntity),
                CreateVisitDto.class
        );
    }

    @Override
    public List<VisitDto> getVisits() {
        return mapperUtil.mapList(
                this.visitRepo.findAll(), VisitDto.class);
    }

    @Override
    public VisitDto getVisit(long id) {
        return mapperUtil.getModelMapper().map(
                this.visitRepo.findById(id)
                        .orElseThrow(() -> new VisitNotFoundException("Visit with id=" + id + " not found!")),
                VisitDto.class
        );
    }

    @Override
    public Visit updateVisit(VisitDto visit, long id) {
        return this.visitRepo.findById(id)
                .map(visitEntity -> {
                    Patient patient = patientRepo.findById(visit.getPatientId())
                            .orElseThrow(() -> new PatientNotFoundException("Patient with id=" + visit.getPatientId() + " not found!"));

                    Doctor doctor = doctorRepo.findById(visit.getDoctorId())
                            .orElseThrow(() -> new DoctorNotFoundException("Doctor with id=" + visit.getDoctorId() + " not found!"));

                    visitEntity.setDate(visit.getDate());
                    visitEntity.setPatient(patient);
                    visitEntity.setDoctor(doctor);
                    visitEntity.setPrice(visit.getPrice());
                    visitEntity.setDiagnosis(visit.getDiagnosis());
                    visitEntity.setTreatment(visit.getTreatment());

                    return this.visitRepo.save(visitEntity);
                })
                .orElseThrow(() -> new VisitNotFoundException("Visit with id=" + id + " not found!"));
    }

    @Override
    public void deleteVisit(long id) {
        if (!this.visitRepo.existsById(id)) {
            throw new VisitNotFoundException("Visit with id=" + id + " not found!");
        }

        this.visitRepo.deleteById(id);
    }
}
