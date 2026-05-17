package com.example.medical_record_system.service.impl;

import com.example.medical_record_system.data.repo.SickNoteRepo;
import com.example.medical_record_system.service.SickNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SickNoteServiceImpl implements SickNoteService {
    private final SickNoteRepo sickNoteRepo;
}
