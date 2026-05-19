package com.example.medical_record_system.service;

import com.example.medical_record_system.data.entity.Visit;
import com.example.medical_record_system.dto.CreateVisitDto;
import com.example.medical_record_system.dto.VisitDto;

import java.util.List;

public interface VisitService {

    CreateVisitDto createVisit(CreateVisitDto visit);

    List<VisitDto> getVisits();

    VisitDto getVisit(long id);

    Visit updateVisit(VisitDto visit, long id);

    void deleteVisit(long id);
}
