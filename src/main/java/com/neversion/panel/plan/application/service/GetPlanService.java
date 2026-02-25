package com.neversion.panel.plan.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.plan.application.port.in.GetPlanUseCase;
import com.neversion.panel.plan.domain.model.Plan;
import com.neversion.panel.plan.domain.port.out.PlanRepositoryPort;

@Service
public class GetPlanService implements GetPlanUseCase {
    private final PlanRepositoryPort productDetailRepositoryPort;

    public GetPlanService(PlanRepositoryPort productDetailRepositoryPort) {
        this.productDetailRepositoryPort = productDetailRepositoryPort;
    }

    @Override
    public Plan getById(Long id) {
        return productDetailRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductDetail with id " + id + " not found"));
    }

    @Override
    public List<Plan> getAll() {
        return productDetailRepositoryPort.findAll();
    }

    @Override
    public List<Plan> getByProductName(String productName) {
        return productDetailRepositoryPort.findByProductName(productName);
    }

    @Override
    public List<Plan> getByAccountType(String accountType) {
        return productDetailRepositoryPort.findByAccountType(accountType);
    }
}
