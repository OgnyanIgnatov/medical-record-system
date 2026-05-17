package com.example.medical_record_system.web.view.controller;

import com.example.medical_record_system.service.DoctorService;
import com.example.medical_record_system.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class DoctorViewController {

    private final DoctorService doctorService;
    private final MapperUtil mapperUtil;


//    @PostMapping("/create")
//    public String createDoctor(@Valid )
}
