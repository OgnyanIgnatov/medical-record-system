package com.example.medical_record_system.web.api;

import com.example.medical_record_system.data.entity.Patient;
import com.example.medical_record_system.dto.CreatePatientDto;
import com.example.medical_record_system.dto.PatientDto;
import com.example.medical_record_system.service.PatientService;
import com.example.medical_record_system.util.MapperUtil;
import com.example.medical_record_system.web.view.controller.model.CreatePatientViewModel;
import com.example.medical_record_system.web.view.controller.model.PatientViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/patients")
public class PatientApiController {

    private final PatientService patientService;
    private final MapperUtil mapperUtil;

    @PostMapping
    public CreatePatientViewModel createPatient(@RequestBody CreatePatientViewModel patientViewModel){
        return this.mapperUtil.getModelMapper().map(this.patientService.createPatient(
                this.mapperUtil.getModelMapper().map(
                        patientViewModel, CreatePatientDto.class)), CreatePatientViewModel.class);
    }

    @GetMapping
    public List<PatientDto> getPatients(){
        return this.patientService.getPatients();
    }

    @GetMapping("/{id}")
    public PatientViewModel getPatient(@PathVariable long id){
        return this.mapperUtil.getModelMapper().map(
                this.patientService.getPatient(id), PatientViewModel.class
        );
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@RequestBody PatientDto patient, @PathVariable long id){
        return this.patientService.updatePatient(patient, id);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable long id){
        this.patientService.deletePatient(id);
    }
}
