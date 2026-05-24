package com.example.medical_record_system.web.view.controller;

import com.example.medical_record_system.data.entity.Patient;
import com.example.medical_record_system.data.entity.SickNote;
import com.example.medical_record_system.data.entity.Visit;
import com.example.medical_record_system.data.repo.PatientRepo;
import com.example.medical_record_system.data.repo.SickNoteRepo;
import com.example.medical_record_system.data.repo.VisitRepo;
import com.example.medical_record_system.service.DoctorService;
import com.example.medical_record_system.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportViewController {

    private final VisitRepo visitRepo;
    private final PatientRepo patientRepo;
    private final SickNoteRepo sickNoteRepo;

    private final PatientService patientService;
    private final DoctorService doctorService;

    @GetMapping
    public String reportsHome(Model model) {
        addBaseAttributes(model);
        addStatistics(model);
        return "reports/reports";
    }

    @GetMapping("/patient-history")
    public String patientHistory(@RequestParam Long patientId, Model model) {
        addBaseAttributes(model);
        addStatistics(model);

        List<Visit> visits = visitRepo.findAllByPatientId(patientId);
        model.addAttribute("patientHistoryVisits", visits);

        return "reports/reports";
    }

    @GetMapping("/patients-by-gp")
    public String patientsByGp(@RequestParam Long doctorId, Model model) {
        addBaseAttributes(model);
        addStatistics(model);

        List<Patient> patients = patientRepo.findAllByGpId(doctorId);
        model.addAttribute("patientsByGp", patients);

        return "reports/reports";
    }

    @GetMapping("/visits-by-doctor")
    public String visitsByDoctor(@RequestParam Long doctorId, Model model) {
        addBaseAttributes(model);
        addStatistics(model);

        List<Visit> visits = visitRepo.findAllByDoctorId(doctorId);
        model.addAttribute("visitsByDoctor", visits);

        return "reports/reports";
    }

    @GetMapping("/visits-by-period")
    public String visitsByPeriod(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            Model model
    ) {
        addBaseAttributes(model);
        addStatistics(model);

        List<Visit> visits = visitRepo.findAllByDateBetween(startDate, endDate);
        model.addAttribute("visitsByPeriod", visits);

        return "reports/reports";
    }

    @GetMapping("/visits-by-doctor-and-period")
    public String visitsByDoctorAndPeriod(
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            Model model
    ) {
        addBaseAttributes(model);
        addStatistics(model);

        List<Visit> visits = visitRepo.findAllByDoctorIdAndDateBetween(doctorId, startDate, endDate);
        model.addAttribute("visitsByDoctorAndPeriod", visits);

        return "reports/reports";
    }

    @GetMapping("/diagnosis")
    public String visitsByDiagnosis(@RequestParam String diagnosis, Model model) {
        addBaseAttributes(model);
        addStatistics(model);

        List<Visit> visits = visitRepo.findAllByDiagnosisContainsIgnoreCase(diagnosis);
        model.addAttribute("visitsByDiagnosis", visits);

        return "reports/reports";
    }

    @GetMapping("/paid-by-doctor")
    public String paidByDoctor(@RequestParam Long doctorId, Model model) {
        addBaseAttributes(model);
        addStatistics(model);

        List<Visit> visits = visitRepo.findAllByDoctorIdAndPatientIsInsuredFalse(doctorId);

        double total = visits.stream()
                .mapToDouble(Visit::getPrice)
                .sum();

        model.addAttribute("paidVisitsByDoctor", visits);
        model.addAttribute("paidByDoctorTotal", total);

        return "reports/reports";
    }

    private void addBaseAttributes(Model model) {
        model.addAttribute("patients", patientService.getPatients());
        model.addAttribute("doctors", doctorService.getDoctors());
    }

    private void addStatistics(Model model) {
        List<Visit> allVisits = visitRepo.findAll();
        List<Patient> allPatients = patientRepo.findAll();
        List<SickNote> allSickNotes = sickNoteRepo.findAll();

        // Най-често срещана диагноза
        Map<String, Long> diagnosisCount = allVisits.stream()
                .collect(Collectors.groupingBy(Visit::getDiagnosis, Collectors.counting()));

        Map.Entry<String, Long> mostCommonDiagnosis = diagnosisCount.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        // Брой посещения при всеки лекар
        Map<String, Long> visitsCountByDoctor = allVisits.stream()
                .collect(Collectors.groupingBy(
                        visit -> visit.getDoctor().getName(),
                        Collectors.counting()
                ));

        // Брой пациенти при всеки личен лекар
        Map<String, Long> patientsCountByGp = allPatients.stream()
                .collect(Collectors.groupingBy(
                        patient -> patient.getGp().getName(),
                        Collectors.counting()
                ));

        // Обща стойност на платените от неосигурени пациенти прегледи
        double totalPaidByPatients = allVisits.stream()
                .filter(visit -> Boolean.FALSE.equals(visit.getPatient().getIsInsured()))
                .mapToDouble(Visit::getPrice)
                .sum();

        // Стойност на платените от пациентите прегледи според лекаря
        Map<String, Double> paidByDoctor = allVisits.stream()
                .filter(visit -> Boolean.FALSE.equals(visit.getPatient().getIsInsured()))
                .collect(Collectors.groupingBy(
                        visit -> visit.getDoctor().getName(),
                        Collectors.summingDouble(Visit::getPrice)
                ));

        // Месец с най-много издадени болнични
        Map<Integer, Long> sickNotesByMonth = allSickNotes.stream()
                .collect(Collectors.groupingBy(
                        sickNote -> sickNote.getIssuedDate().getMonth() + 1,
                        Collectors.counting()
                ));

        Map.Entry<Integer, Long> monthWithMostSickNotes = sickNotesByMonth.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        // Лекар с най-много издадени болнични
        Map<String, Long> sickNotesByDoctor = allSickNotes.stream()
                .collect(Collectors.groupingBy(
                        sickNote -> sickNote.getVisit().getDoctor().getName(),
                        Collectors.counting()
                ));

        Map.Entry<String, Long> doctorWithMostSickNotes = sickNotesByDoctor.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        model.addAttribute("mostCommonDiagnosis", mostCommonDiagnosis);
        model.addAttribute("visitsCountByDoctor", visitsCountByDoctor);
        model.addAttribute("patientsCountByGp", patientsCountByGp);
        model.addAttribute("totalPaidByPatients", totalPaidByPatients);
        model.addAttribute("paidByDoctor", paidByDoctor);
        model.addAttribute("monthWithMostSickNotes", monthWithMostSickNotes);
        model.addAttribute("doctorWithMostSickNotes", doctorWithMostSickNotes);
    }
}