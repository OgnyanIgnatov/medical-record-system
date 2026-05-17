package com.example.medical_record_system.service;

import com.example.medical_record_system.data.entity.Visit;
import com.example.medical_record_system.dto.VisitDto;

import java.util.List;

public interface VisitService {

    VisitDto createVisit(VisitDto visit);

    List<VisitDto> getVisits();

    VisitDto getVisit(long id);

    Visit updateVisit(Visit visit, long id);

    void deleteVisit(long id);
}
