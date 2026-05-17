package com.example.medical_record_system.service.impl;

import com.example.medical_record_system.data.entity.Visit;
import com.example.medical_record_system.data.repo.VisitRepo;
import com.example.medical_record_system.dto.SickNoteDto;
import com.example.medical_record_system.dto.VisitDto;
import com.example.medical_record_system.service.VisitService;
import com.example.medical_record_system.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {
    private final VisitRepo visitRepo;
    private final MapperUtil mapperUtil;

    @Override
    public VisitDto createVisit(Visit visit) {
        return mapperUtil.getModelMapper().map(
                this.visitRepo.save(
                        mapperUtil.getModelMapper().map(
                                visit, Visit.class)), VisitDto.class);
    }

    @Override
    public List<VisitDto> getVisits() {
        return mapperUtil.mapList(
                this.visitRepo.findAll(), VisitDto.class);
    }

    @Override
    public VisitDto getVisit(long id) {
        return mapperUtil.getModelMapper().map(
                this.visitRepo.findById(id)
                        .orElseThrow( () -> new RuntimeException("There is no sick note with such id")),
                VisitDto.class);
    }

    @Override
    public Visit updateVisit(Visit visit, long id) {
        return this.visitRepo.findById(id).map(
                visit1 -> {
                    visit1.setPatient(visit.getPatient());
                    visit1.setDoctor(visit.getDoctor());
                    visit1.setDate(visit.getDate());
                    visit1.setPrice(visit.getPrice());
                    visit1.setDiagnosis(visit.getDiagnosis());
                    visit1.setTreatment(visit.getTreatment());
                    return this.visitRepo.save(visit1);
                }
        ).orElseGet(() -> this.visitRepo.save(visit));
    }

    @Override
    public void deleteVisit(long id) {
        this.visitRepo.deleteById(id);
    }
}
