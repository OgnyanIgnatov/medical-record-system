package com.example.medical_record_system.web.view.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        if (hasAuthority(authentication, "admin")) {
            return "redirect:/admin/dashboard";
        }

        if (hasAuthority(authentication, "doctor")) {
            return "redirect:/doctor/dashboard";
        }

        if (hasAuthority(authentication, "patient")) {
            return "redirect:/patient/dashboard";
        }

        return "redirect:/unauthorized";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "dashboards/admin-dashboard";
    }

    @GetMapping("/doctor/dashboard")
    public String doctorDashboard() {
        return "dashboards/doctor-dashboard";
    }

    @GetMapping("/patient/dashboard")
    public String patientDashboard() {
        return "dashboards/patient-dashboard";
    }

    private boolean hasAuthority(Authentication authentication, String authority) {
        return authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(authority));
    }
}