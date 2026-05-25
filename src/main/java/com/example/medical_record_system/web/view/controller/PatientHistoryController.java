package com.example.medical_record_system.web.view.controller;

import com.example.medical_record_system.data.entity.Patient;
import com.example.medical_record_system.data.entity.Visit;
import com.example.medical_record_system.data.repo.PatientRepo;
import com.example.medical_record_system.data.repo.VisitRepo;
import com.example.medical_record_system.exception.PatientNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PatientHistoryController {

    private final PatientRepo patientRepo;
    private final VisitRepo visitRepo;

    @GetMapping("/patient/my-history")
    public String myHistory(Authentication authentication, Model model) {
        String username = authentication.getName();

        Patient patient = patientRepo.findByUsername(username);

        if (patient == null) {
            throw new PatientNotFoundException("Patient with username=" + username + " not found!");
        }

        List<Visit> visits = visitRepo.findAllByPatientId(patient.getId());

        model.addAttribute("patient", patient);
        model.addAttribute("visits", visits);

        return "patients/my-history";
    }
}