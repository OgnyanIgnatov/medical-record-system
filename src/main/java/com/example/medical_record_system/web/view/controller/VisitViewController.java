package com.example.medical_record_system.web.view.controller;

import com.example.medical_record_system.dto.CreateVisitDto;
import com.example.medical_record_system.dto.VisitDto;
import com.example.medical_record_system.service.DoctorService;
import com.example.medical_record_system.service.PatientService;
import com.example.medical_record_system.service.VisitService;
import com.example.medical_record_system.util.MapperUtil;
import com.example.medical_record_system.web.view.controller.model.CreateVisitViewModel;
import com.example.medical_record_system.web.view.controller.model.VisitViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/visits")
public class VisitViewController {

    private final VisitService visitService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final MapperUtil mapperUtil;

    @PostMapping("/create")
    public String createVisit(@Valid @ModelAttribute("visit") CreateVisitViewModel visitViewModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("patients", patientService.getPatients());
            model.addAttribute("doctors", doctorService.getDoctors());
            return "visits/create-visit";
        }

        this.visitService.createVisit(
                mapperUtil.getModelMapper().map(visitViewModel, CreateVisitDto.class)
        );

        return "redirect:/visits";
    }

    @GetMapping("/create-visit")
    public String showCreateVisitForm(Model model) {
        model.addAttribute("visit", new CreateVisitViewModel());
        model.addAttribute("patients", patientService.getPatients());
        model.addAttribute("doctors", doctorService.getDoctors());
        return "visits/create-visit";
    }

    @GetMapping
    public String getVisits(Model model) {
        List<VisitViewModel> visits = mapperUtil.mapList(
                this.visitService.getVisits(), VisitViewModel.class
        );

        model.addAttribute("visits", visits);
        return "visits/visits";
    }

    @GetMapping("/edit-visit/{id}")
    public String showEditVisitForm(Model model, @PathVariable long id) {
        model.addAttribute("visit", this.visitService.getVisit(id));
        model.addAttribute("patients", patientService.getPatients());
        model.addAttribute("doctors", doctorService.getDoctors());
        return "visits/edit-visit";
    }

    @PostMapping("/update/{id}")
    public String updateVisit(@PathVariable long id, @Valid @ModelAttribute("visit") VisitDto visit, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("patients", patientService.getPatients());
            model.addAttribute("doctors", doctorService.getDoctors());
            return "visits/edit-visit";
        }

        this.visitService.updateVisit(visit, id);
        return "redirect:/visits";
    }

    @GetMapping("/delete/{id}")
    public String deleteVisit(@PathVariable long id) {
        this.visitService.deleteVisit(id);
        return "redirect:/visits";
    }
}