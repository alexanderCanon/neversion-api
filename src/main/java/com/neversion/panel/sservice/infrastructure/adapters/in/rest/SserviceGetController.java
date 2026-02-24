package com.neversion.panel.sservice.infrastructure.adapters.in.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.sservice.application.port.in.GetSserviceUseCase;
import com.neversion.panel.sservice.domain.model.Sservice;
import com.neversion.panel.sservice.infrastructure.adapters.in.rest.dto.SserviceResponse;
import com.neversion.panel.sservice.infrastructure.adapters.in.rest.mapper.SserviceMapper;

@RestController
@RequestMapping("/api/v1/sservices")
public class SserviceGetController {

    private final GetSserviceUseCase getSserviceUseCase;
    private final SserviceMapper sserviceMapper;

    public SserviceGetController(GetSserviceUseCase getSserviceUseCase, SserviceMapper sserviceMapper) {
        this.getSserviceUseCase = getSserviceUseCase;
        this.sserviceMapper = sserviceMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SserviceResponse> getPlatformById(@PathVariable Integer id) {
        Sservice sservice = getSserviceUseCase.getById(id);
        SserviceResponse response = sserviceMapper.toResponse(sservice);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getPlatforms(@RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            Sservice sservice = getSserviceUseCase.getByName(name);
            SserviceResponse response = sserviceMapper.toResponse(sservice);
            return ResponseEntity.ok(response);
        }
        List<SserviceResponse> response = getSserviceUseCase.getAll().stream()
                .map(sserviceMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}
