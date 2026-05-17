package com.example.medical_record_system.web.api;

import com.example.medical_record_system.data.entity.Visit;
import com.example.medical_record_system.dto.CreateVisitDto;
import com.example.medical_record_system.dto.VisitDto;
import com.example.medical_record_system.service.VisitService;
import com.example.medical_record_system.util.MapperUtil;
import com.example.medical_record_system.web.view.controller.model.CreateVisitViewModel;
import com.example.medical_record_system.web.view.controller.model.VisitViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/visits")
public class VisitApiController {

    private final VisitService visitService;
    private final MapperUtil mapperUtil;

    @PostMapping
    public CreateVisitViewModel createVisit(CreateVisitViewModel visitViewModel){
        return this.mapperUtil.getModelMapper().map(
                this.visitService.createVisit(
                        this.mapperUtil.getModelMapper().map(
                                visitViewModel, CreateVisitDto.class
                        )
                ), CreateVisitViewModel.class
        );
    }

    @GetMapping
    public List<VisitDto> getVisits(){
        return this.visitService.getVisits();
    }

    @GetMapping("/{id}")
    public VisitViewModel getVisit(@PathVariable long id){
        return this.mapperUtil.getModelMapper().map(
                this.visitService.getVisit(id), VisitViewModel.class
        );
    }

    @PutMapping("/{id}")
    public Visit updateVisit(@RequestBody Visit visit, @PathVariable long id){
        return this.visitService.updateVisit(visit, id);
    }

    @DeleteMapping("/{id}")
    public void deleteVisit(@PathVariable long id){
        this.visitService.deleteVisit(id);
    }
}
