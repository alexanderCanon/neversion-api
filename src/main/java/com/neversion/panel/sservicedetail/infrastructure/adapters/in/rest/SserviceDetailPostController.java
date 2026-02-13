package com.neversion.panel.sservicedetail.infrastructure.adapters.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.sservicedetail.application.port.in.CreateSserviceDetailUseCase;
import com.neversion.panel.sservicedetail.domain.model.SserviceDetail;
import com.neversion.panel.sservicedetail.infrastructure.adapters.in.rest.dto.SserviceDetailRequest;
import com.neversion.panel.sservicedetail.infrastructure.adapters.in.rest.dto.SserviceDetailResponse;
import com.neversion.panel.sservicedetail.infrastructure.adapters.in.rest.mapper.SserviceDetailMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/service-details")
public class SserviceDetailPostController {
    private final CreateSserviceDetailUseCase createSserviceDetailUseCase;
    private final SserviceDetailMapper sserviceDetailMapper;

    public SserviceDetailPostController(CreateSserviceDetailUseCase createSserviceDetailUseCase,
        SserviceDetailMapper sserviceDetailMapper) {
        this.createSserviceDetailUseCase = createSserviceDetailUseCase;
        this.sserviceDetailMapper = sserviceDetailMapper;
    }

    @PostMapping
    public ResponseEntity<SserviceDetailResponse> createSserviceDetail(
        @Valid @RequestBody SserviceDetailRequest request) {
        SserviceDetail sserviceDetail = sserviceDetailMapper.toDomain(request);
        SserviceDetail created = createSserviceDetailUseCase.create(sserviceDetail);
        SserviceDetailResponse response = sserviceDetailMapper.toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
