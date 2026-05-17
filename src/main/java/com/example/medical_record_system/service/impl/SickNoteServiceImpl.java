package com.example.medical_record_system.service.impl;

import com.example.medical_record_system.data.entity.SickNote;
import com.example.medical_record_system.data.repo.SickNoteRepo;
import com.example.medical_record_system.dto.SickNoteDto;
import com.example.medical_record_system.service.SickNoteService;
import com.example.medical_record_system.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SickNoteServiceImpl implements SickNoteService {
    private final SickNoteRepo sickNoteRepo;
    private final MapperUtil mapperUtil;

    @Override
    public SickNoteDto createSickNote(SickNoteDto sickNote) {
        return mapperUtil.getModelMapper().map(
                this.sickNoteRepo.save(
                        mapperUtil.getModelMapper().map(
                                sickNote, SickNote.class
                        )), SickNoteDto.class
        );
    }

    @Override
    public List<SickNoteDto> getSickNotes() {
        return mapperUtil.mapList(
                this.sickNoteRepo.findAll(), SickNoteDto.class
        );
    }

    @Override
    public SickNoteDto getSickNote(long id) {
        return mapperUtil.getModelMapper().map(
                this.sickNoteRepo.findById(id)
                        .orElseThrow( () -> new RuntimeException("There is no sick note with such id")),
                                SickNoteDto.class);
    }

    @Override
    public SickNote updateSickNote(SickNote sickNote, long id) {
        return this.sickNoteRepo.findById(id).map(
                sickNote1 -> {
                    sickNote1.setIssuedDate(sickNote.getIssuedDate());
                    sickNote1.setVisit(sickNote.getVisit());
                    sickNote1.setDaysCount(sickNote.getDaysCount());
                    return this.sickNoteRepo.save(sickNote1);
                }
        ).orElseGet(() -> this.sickNoteRepo.save(sickNote));
    }

    @Override
    public void deleteSickNote(long id) {
        this.sickNoteRepo.deleteById(id);
    }
}
