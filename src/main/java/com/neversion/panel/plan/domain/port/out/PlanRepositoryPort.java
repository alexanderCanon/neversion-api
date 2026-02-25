package com.neversion.panel.plan.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.neversion.panel.plan.domain.model.Plan;

public interface PlanRepositoryPort {
    Plan save(Plan productDetail);

    Optional<Plan> findById(Long id);

    List<Plan> findAll();

    List<Plan> findByProductName(String productName);

    List<Plan> findByAccountType(String accountType);
}
