package com.example.medical_record_system.service.impl;

import com.example.medical_record_system.dto.CreateDoctorDto;
import com.example.medical_record_system.dto.DoctorDto;
import com.example.medical_record_system.data.entity.Doctor;
import com.example.medical_record_system.data.repo.DoctorRepo;
import com.example.medical_record_system.service.DoctorService;
import com.example.medical_record_system.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepo doctorRepo;
    private final MapperUtil mapperUtil;

    @Override
    public CreateDoctorDto createDoctor(CreateDoctorDto doctor) {
        return mapperUtil.getModelMapper()
                .map(this.doctorRepo
                        .save(mapperUtil.getModelMapper()
                                .map(doctor, Doctor.class)), CreateDoctorDto.class);
    }

    @Override
    public List<DoctorDto> getDoctors() {
        return this.mapperUtil.mapList(
                this.doctorRepo.findAll(), DoctorDto.class);
    }

    @Override
    public DoctorDto getDoctor(long id) {
        return this.mapperUtil.getModelMapper().map(
                this.doctorRepo
                        .findById(id)
                        .orElseThrow( () -> new RuntimeException("There is no doctor with such id!")),
                DoctorDto.class);
    }

    @Override
    public Doctor updateDoctor(DoctorDto doctor, long id) {
        Doctor doctorEntity =  this.doctorRepo.findById(doctor.getId()).orElseThrow(() -> new RuntimeException("No such doctor"));
        return this.doctorRepo.findById(id).map(doctor1 -> {
                    doctor1.setUid(doctorEntity.getUid());
                    doctor1.setName(doctorEntity.getName());
                    doctor1.setDegree(doctorEntity.getDegree());
                    doctor1.setVisits(doctorEntity.getVisits());
                    doctor1.setPatients(doctorEntity.getPatients());
                    return this.doctorRepo.save(doctor1);
                })
                .orElseGet(() -> this.doctorRepo.save(doctorEntity));
    }

    @Override
    public void deleteDoctor(long id) {
        this.doctorRepo.deleteById(id);
    }
}
