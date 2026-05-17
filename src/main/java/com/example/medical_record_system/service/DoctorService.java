package com.example.medical_record_system.service;

import com.example.medical_record_system.dto.DoctorDto;
import com.example.medical_record_system.data.entity.Doctor;

import java.util.List;

public interface DoctorService {

    DoctorDto createDoctor(DoctorDto doctor);

    List<DoctorDto> getDoctors();

    DoctorDto getDoctor(long id);

    Doctor updateDoctor(Doctor doctor, long id);

    void deleteDoctor(long id);
}
