package com.example.medical_record_system.service.impl;

import com.example.medical_record_system.dto.CreateDoctorDto;
import com.example.medical_record_system.dto.DoctorDto;
import com.example.medical_record_system.data.entity.Doctor;
import com.example.medical_record_system.data.repo.DoctorRepo;
import com.example.medical_record_system.exception.DoctorNotFoundException;
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
                        .orElseThrow(() -> new DoctorNotFoundException("Doctor with id=" + id + " not found!")),
                DoctorDto.class);
    }

    @Override
    public Doctor updateDoctor(DoctorDto doctor, long id) {
        return this.doctorRepo.findById(id)
                .map(doctorEntity -> {
                    doctorEntity.setUid(doctor.getUid());
                    doctorEntity.setName(doctor.getName());
                    doctorEntity.setUsername(doctor.getUsername());
                    doctorEntity.setDegree(doctor.getDegree());
                    return this.doctorRepo.save(doctorEntity);
                })
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with id=" + id + " not found!"));
    }

    @Override
    public void deleteDoctor(long id) {
        if (!this.doctorRepo.existsById(id)) {
            throw new DoctorNotFoundException("Doctor with id=" + id + " not found!");
        }

        this.doctorRepo.deleteById(id);
    }
}
