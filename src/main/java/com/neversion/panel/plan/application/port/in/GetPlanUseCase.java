package com.neversion.panel.plan.application.port.in;

import java.util.List;

import com.neversion.panel.plan.domain.model.Plan;

public interface GetPlanUseCase {
    Plan getById(Long id);

    List<Plan> getAll();

    List<Plan> getByProductName(String productName);

    List<Plan> getByAccountType(String accountType);
}
