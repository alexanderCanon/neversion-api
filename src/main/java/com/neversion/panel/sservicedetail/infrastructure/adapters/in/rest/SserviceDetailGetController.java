package com.neversion.panel.sservicedetail.infrastructure.adapters.in.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.sservicedetail.application.port.in.GetSserviceDetailUseCase;
import com.neversion.panel.sservicedetail.domain.model.SserviceDetail;
import com.neversion.panel.sservicedetail.infrastructure.adapters.in.rest.dto.SserviceDetailResponse;
import com.neversion.panel.sservicedetail.infrastructure.adapters.in.rest.mapper.SserviceDetailMapper;

@RestController
@RequestMapping("/api/v1/service-details")
public class SserviceDetailGetController {

    private final GetSserviceDetailUseCase getSserviceDetailUseCase;
    private final SserviceDetailMapper sserviceDetailMapper;

    public SserviceDetailGetController(GetSserviceDetailUseCase getSserviceDetailUseCase,
        SserviceDetailMapper sserviceDetailMapper) {
        this.getSserviceDetailUseCase = getSserviceDetailUseCase;
        this.sserviceDetailMapper = sserviceDetailMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SserviceDetailResponse> getById(@PathVariable Long id) {
        SserviceDetail sserviceDetail = getSserviceDetailUseCase.getById(id);
        SserviceDetailResponse response = sserviceDetailMapper.toResponse(sserviceDetail);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getServiceDetails(
        @RequestParam(required = false) String serviceName,
        @RequestParam(required = false) String categoryName) {

        if (serviceName != null && !serviceName.isBlank()) {
            List<SserviceDetailResponse> response = getSserviceDetailUseCase.getByServiceName(serviceName)
                .stream()
                .map(sserviceDetailMapper::toResponse)
                .toList();
            return ResponseEntity.ok(response);
        }

        if (categoryName != null && !categoryName.isBlank()) {
            List<SserviceDetailResponse> response = getSserviceDetailUseCase.getByCategoryName(categoryName)
                .stream()
                .map(sserviceDetailMapper::toResponse)
                .toList();
            return ResponseEntity.ok(response);
        }

        List<SserviceDetailResponse> response = getSserviceDetailUseCase.getAll()
            .stream()
            .map(sserviceDetailMapper::toResponse)
            .toList();
        return ResponseEntity.ok(response);
    }
}
