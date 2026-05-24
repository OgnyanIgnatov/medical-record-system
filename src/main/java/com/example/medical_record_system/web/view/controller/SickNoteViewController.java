package com.example.medical_record_system.web.view.controller;

import com.example.medical_record_system.dto.CreateSickNoteDto;
import com.example.medical_record_system.dto.SickNoteDto;
import com.example.medical_record_system.service.SickNoteService;
import com.example.medical_record_system.service.VisitService;
import com.example.medical_record_system.util.MapperUtil;
import com.example.medical_record_system.web.view.controller.model.CreateSickNoteViewModel;
import com.example.medical_record_system.web.view.controller.model.SickNoteViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sick-notes")
public class SickNoteViewController {

    private final SickNoteService sickNoteService;
    private final VisitService visitService;
    private final MapperUtil mapperUtil;

    @PostMapping("/create")
    public String createSickNote(@Valid @ModelAttribute("sickNote") CreateSickNoteViewModel sickNoteViewModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("visits", visitService.getVisits());
            return "sick-notes/create-sick-note";
        }

        this.sickNoteService.createSickNote(
                mapperUtil.getModelMapper().map(sickNoteViewModel, CreateSickNoteDto.class)
        );

        return "redirect:/sick-notes";
    }

    @GetMapping("/create-sick-note")
    public String showCreateSickNoteForm(Model model) {
        model.addAttribute("sickNote", new CreateSickNoteViewModel());
        model.addAttribute("visits", visitService.getVisits());
        return "sick-notes/create-sick-note";
    }

    @GetMapping
    public String getSickNotes(Model model) {
        List<SickNoteViewModel> sickNotes = mapperUtil.mapList(
                this.sickNoteService.getSickNotes(), SickNoteViewModel.class
        );

        model.addAttribute("sickNotes", sickNotes);
        return "sick-notes/sick-notes";
    }

    @GetMapping("/edit-sick-note/{id}")
    public String showEditSickNoteForm(Model model, @PathVariable long id) {
        model.addAttribute("sickNote", this.sickNoteService.getSickNote(id));
        model.addAttribute("visits", visitService.getVisits());
        return "sick-notes/edit-sick-note";
    }

    @PostMapping("/update/{id}")
    public String updateSickNote(@PathVariable long id, @Valid @ModelAttribute("sickNote") SickNoteDto sickNote, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("visits", visitService.getVisits());
            return "sick-notes/edit-sick-note";
        }

        this.sickNoteService.updateSickNote(sickNote, id);
        return "redirect:/sick-notes";
    }

    @GetMapping("/delete/{id}")
    public String deleteSickNote(@PathVariable long id) {
        this.sickNoteService.deleteSickNote(id);
        return "redirect:/sick-notes";
    }
}