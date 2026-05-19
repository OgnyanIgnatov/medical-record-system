package com.example.medical_record_system.web.view.controller;

import com.example.medical_record_system.data.entity.Doctor;
import com.example.medical_record_system.dto.CreateDoctorDto;
import com.example.medical_record_system.dto.DoctorDto;
import com.example.medical_record_system.service.DoctorService;
import com.example.medical_record_system.util.MapperUtil;
import com.example.medical_record_system.web.view.controller.model.CreateDoctorViewModel;
import com.example.medical_record_system.web.view.controller.model.DoctorViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorViewController {

    private final DoctorService doctorService;
    private final MapperUtil mapperUtil;

    @PostMapping("/create")
    public String createDoctor(@Valid @ModelAttribute("doctor")CreateDoctorViewModel doctor, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(System.out::println);
            return "doctors/create-doctor";
        }
        this.doctorService.createDoctor(mapperUtil.getModelMapper().map(doctor, CreateDoctorDto.class));
        return "redirect:/doctors";
    }

    @GetMapping("/create-doctor")
    public String showCreateDoctorForm(Model model){
        model.addAttribute("doctor", new CreateDoctorViewModel());
        return "doctors/create-doctor";
    }

    @GetMapping
    public String getDoctors(Model model){
        List<DoctorViewModel> doctors = mapperUtil.mapList(
                this.doctorService.getDoctors(), DoctorViewModel.class
        );
        model.addAttribute("doctors", doctors);
        return "doctors/doctors";
    }

    @GetMapping("/edit-doctor/{id}")
    public String showEditDoctorForm(Model model, @PathVariable long id){
        model.addAttribute("doctor", this.doctorService.getDoctor(id));
        return "doctors/edit-doctor";
    }

    @PostMapping("/update/{id}")
    public String updateDoctor(DoctorDto doctor, @PathVariable long id){
        this.doctorService.updateDoctor(doctor, id);
        return "redirect:/doctors";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable long id){
        this.doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }
}
