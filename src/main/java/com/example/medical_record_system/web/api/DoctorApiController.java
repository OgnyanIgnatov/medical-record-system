package com.example.medical_record_system.web.api;

import com.example.medical_record_system.data.entity.Doctor;
import com.example.medical_record_system.dto.CreateDoctorDto;
import com.example.medical_record_system.dto.DoctorDto;
import com.example.medical_record_system.service.DoctorService;
import com.example.medical_record_system.util.MapperUtil;
import com.example.medical_record_system.web.view.controller.model.CreateDoctorViewModel;
import com.example.medical_record_system.web.view.controller.model.DoctorViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorApiController {

    private final DoctorService doctorService;
    private final MapperUtil mapperUtil;

    @PostMapping
    public CreateDoctorViewModel createDoctor(@RequestBody CreateDoctorViewModel doctorViewModel){
        return mapperUtil.getModelMapper().map(this.doctorService
                .createDoctor(mapperUtil.getModelMapper().map(doctorViewModel, CreateDoctorDto.class)), CreateDoctorViewModel.class
        );
    }

    @GetMapping
    public List<DoctorDto> getDoctors(){
        return this.doctorService.getDoctors();
    }

    @GetMapping("/{id}")
    public DoctorViewModel getDoctor(@PathVariable long id){
        return this.mapperUtil.getModelMapper().map(
                this.doctorService.getDoctor(id), DoctorViewModel.class
        );
    }

    @PutMapping("/{id}")
    public Doctor updateDoctor(@RequestBody DoctorDto doctor, @PathVariable long id){
        return this.doctorService.updateDoctor(doctor, id);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable long id){
        this.doctorService.deleteDoctor(id);
    }
}
