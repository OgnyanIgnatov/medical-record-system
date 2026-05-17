package com.example.medical_record_system.service;

import com.example.medical_record_system.data.entity.SickNote;
import com.example.medical_record_system.dto.SickNoteDto;

import java.util.List;

public interface SickNoteService {

    SickNoteDto createSickNote(SickNote sickNote);

    List<SickNoteDto> getSickNotes();

    SickNoteDto getSickNote(long id);

    SickNote updateSickNote(SickNote sickNote, long id);

    void deleteSickNote(long id);
}
