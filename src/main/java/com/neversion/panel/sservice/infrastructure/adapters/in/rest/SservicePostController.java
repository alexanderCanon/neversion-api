package com.neversion.panel.sservice.infrastructure.adapters.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.sservice.application.port.in.CreateSserviceUseCase;
import com.neversion.panel.sservice.domain.model.Sservice;
import com.neversion.panel.sservice.infrastructure.adapters.in.rest.dto.SserviceRequest;
import com.neversion.panel.sservice.infrastructure.adapters.in.rest.mapper.SserviceMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/sservices")
public class SservicePostController {

    private final CreateSserviceUseCase createSserviceUseCase;
    private final SserviceMapper sserviceMapper;

    public SservicePostController(CreateSserviceUseCase createSserviceUseCase, SserviceMapper sserviceMapper) {
        this.createSserviceUseCase = createSserviceUseCase;
        this.sserviceMapper = sserviceMapper;
    }

    @PostMapping
    public ResponseEntity<Sservice> createSservice(@Valid @RequestBody SserviceRequest request) {
        Sservice sservice = sserviceMapper.toDomain(request);
        Sservice createdSservice = createSserviceUseCase.create(sservice);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSservice);
    }
}
