package com.neversion.panel.plan.infrastructure.adapters.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.neversion.panel.plan.application.port.in.CreatePlanUseCase;
import com.neversion.panel.plan.domain.model.Plan;
import com.neversion.panel.plan.infrastructure.adapters.in.rest.dto.PlanRequest;
import com.neversion.panel.plan.infrastructure.adapters.in.rest.dto.PlanResponse;
import com.neversion.panel.plan.infrastructure.adapters.in.rest.mapper.PlanMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/plans")
public class PlanPostController {

    private final CreatePlanUseCase createPlanUseCase;

    public PlanPostController(CreatePlanUseCase createPlanUseCase) {
        this.createPlanUseCase = createPlanUseCase;
    }

    @PostMapping
    public ResponseEntity<PlanResponse> createPlan(@Valid @RequestBody PlanRequest request) {
        if (request.productId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "productId is required to create a service item independently.");
        }

        Plan plan = PlanMapper.toDomain(request);
        Plan createdItem = createPlanUseCase.create(request.productId(), plan);
        PlanResponse response = PlanMapper.toResponse(createdItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
