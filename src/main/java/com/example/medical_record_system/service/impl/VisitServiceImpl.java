package com.example.medical_record_system.service.impl;

import com.example.medical_record_system.data.repo.VisitRepo;
import com.example.medical_record_system.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {
    private final VisitRepo visitRepo;
}
