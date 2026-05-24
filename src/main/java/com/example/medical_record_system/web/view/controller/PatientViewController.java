package com.example.medical_record_system.web.view.controller;

import com.example.medical_record_system.data.entity.Patient;
import com.example.medical_record_system.dto.CreatePatientDto;
import com.example.medical_record_system.dto.PatientDto;
import com.example.medical_record_system.service.DoctorService;
import com.example.medical_record_system.service.PatientService;
import com.example.medical_record_system.util.MapperUtil;
import com.example.medical_record_system.web.view.controller.model.CreatePatientViewModel;
import com.example.medical_record_system.web.view.controller.model.PatientViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientViewController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final MapperUtil mapperUtil;

    @PostMapping("/create")
    public String createPatient(@Valid @ModelAttribute("patient") CreatePatientViewModel patientViewModel, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctors", doctorService.getDoctors());
            return "patients/create-patient";
        }
        this.patientService
                .createPatient(mapperUtil.getModelMapper().map(patientViewModel, CreatePatientDto.class));
        return "redirect:/patients";
    }

    @GetMapping("/create-patient")
    public String showCreatePatientForm(Model model) {
        model.addAttribute("patient", new CreatePatientViewModel());
        model.addAttribute("doctors", doctorService.getDoctors());
        return "patients/create-patient";
    }

    @GetMapping
    public String getPatients(Model model) {
        List<PatientViewModel> patients = mapperUtil
                .mapList(this.patientService.getPatients(), PatientViewModel.class);
        model.addAttribute("patients", patients);
        return "patients/patients";
    }

    @GetMapping("/edit-patient/{id}")
    public String showEditPatientForm(Model model, @PathVariable long id) {
        model.addAttribute("patient", this.patientService.getPatient(id));
        model.addAttribute("doctors", doctorService.getDoctors());
        return "patients/edit-patient";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable long id,@Valid @ModelAttribute PatientDto patient, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("doctors", doctorService.getDoctors());
            return "patient/edit-patient";
        }

        this.patientService.updatePatient(patient, id);
        return "redirect:/patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable long id) {
        this.patientService.deletePatient(id);
        return "redirect:/patients";
    }
}
