package com.example.medical_record_system.web.api;

import com.example.medical_record_system.data.entity.SickNote;
import com.example.medical_record_system.dto.SickNoteDto;
import com.example.medical_record_system.service.SickNoteService;
import com.example.medical_record_system.util.MapperUtil;
import com.example.medical_record_system.web.view.controller.model.CreateSickNoteViewModel;
import com.example.medical_record_system.web.view.controller.model.SickNoteViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sickNotes")
public class SickNoteApiController {

    private final SickNoteService sickNoteService;
    private final MapperUtil mapperUtil;

    @PostMapping
    public CreateSickNoteViewModel createSickNote(@RequestBody CreateSickNoteViewModel sickNoteDto){
        return this.mapperUtil.getModelMapper().map(
                this.sickNoteService.createSickNote(
                        this.mapperUtil.getModelMapper().map(
                                sickNoteDto, SickNoteDto.class)), CreateSickNoteViewModel.class
        );
    }

    @GetMapping
    public List<SickNoteDto> getSickNotes(){
        return this.sickNoteService.getSickNotes();
    }

    @GetMapping("/{id}")
    public SickNoteViewModel getSickNote(@PathVariable long id){
        return this.mapperUtil.getModelMapper().map(
                this.sickNoteService.getSickNote(id), SickNoteViewModel.class
        );
    }

    @PutMapping("/{id}")
    public SickNote updateSickNote(@RequestBody SickNote sickNote, @PathVariable long id){
        return this.sickNoteService.updateSickNote(sickNote, id);
    }

    @DeleteMapping("/{id}")
    public void deleteSickNote(@PathVariable long id){
        this.sickNoteService.deleteSickNote(id);
    }
}
