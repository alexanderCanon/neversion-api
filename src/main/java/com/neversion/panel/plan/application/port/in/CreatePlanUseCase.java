package com.neversion.panel.plan.application.port.in;

import com.neversion.panel.plan.domain.model.Plan;

public interface CreatePlanUseCase {
    Plan create(Integer productId, Plan productDetail);
}
