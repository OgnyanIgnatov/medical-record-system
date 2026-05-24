package com.example.medical_record_system.service.impl;

import com.example.medical_record_system.data.entity.SickNote;
import com.example.medical_record_system.data.entity.Visit;
import com.example.medical_record_system.data.repo.SickNoteRepo;
import com.example.medical_record_system.data.repo.VisitRepo;
import com.example.medical_record_system.dto.CreateSickNoteDto;
import com.example.medical_record_system.dto.SickNoteDto;
import com.example.medical_record_system.exception.SickNoteNotFoundException;
import com.example.medical_record_system.exception.VisitNotFoundException;
import com.example.medical_record_system.service.SickNoteService;
import com.example.medical_record_system.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SickNoteServiceImpl implements SickNoteService {
    private final SickNoteRepo sickNoteRepo;
    private final VisitRepo visitRepo;
    private final MapperUtil mapperUtil;

    @Override
    public CreateSickNoteDto createSickNote(CreateSickNoteDto sickNote) {
        SickNote sickNoteEntity = mapperUtil.getModelMapper().map(sickNote, SickNote.class);

        Visit visit = visitRepo.findById(sickNote.getVisitId())
                .orElseThrow(() -> new VisitNotFoundException("Visit with id=" + sickNote.getVisitId() + " not found!"));

        sickNoteEntity.setVisit(visit);

        return mapperUtil.getModelMapper().map(
                this.sickNoteRepo.save(sickNoteEntity),
                CreateSickNoteDto.class
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
                        .orElseThrow(() -> new SickNoteNotFoundException("Sick note with id=" + id + " not found!")),
                SickNoteDto.class
        );
    }

    @Override
    public SickNote updateSickNote(SickNoteDto sickNote, long id) {
        return this.sickNoteRepo.findById(id)
                .map(sickNoteEntity -> {
                    Visit visit = visitRepo.findById(sickNote.getVisitId())
                            .orElseThrow(() -> new VisitNotFoundException("Visit with id=" + sickNote.getVisitId() + " not found!"));

                    sickNoteEntity.setIssuedDate(sickNote.getIssuedDate());
                    sickNoteEntity.setDaysCount(sickNote.getDaysCount());
                    sickNoteEntity.setVisit(visit);

                    return this.sickNoteRepo.save(sickNoteEntity);
                })
                .orElseThrow(() -> new SickNoteNotFoundException("Sick note with id=" + id + " not found!"));
    }

    @Override
    public void deleteSickNote(long id) {
        if (!this.sickNoteRepo.existsById(id)) {
            throw new SickNoteNotFoundException("Sick note with id=" + id + " not found!");
        }

        this.sickNoteRepo.deleteById(id);
    }
}
