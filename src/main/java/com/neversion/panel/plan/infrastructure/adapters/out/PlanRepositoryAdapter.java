package com.neversion.panel.plan.infrastructure.adapters.out;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepositoryAdapter extends JpaRepository<PlanEntity, Long> {
    List<PlanEntity> findByProduct_Name(String productName);

    List<PlanEntity> findByAccountType(String accountType);
}
