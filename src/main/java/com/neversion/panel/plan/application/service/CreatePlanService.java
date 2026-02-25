package com.neversion.panel.plan.application.service;

import org.springframework.stereotype.Service;

import com.neversion.panel.product.application.port.in.GetProductUseCase;
import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.plan.application.port.in.CreatePlanUseCase;
import com.neversion.panel.plan.domain.model.Plan;
import com.neversion.panel.plan.domain.port.out.PlanRepositoryPort;

@Service
public class CreatePlanService implements CreatePlanUseCase {
    private final PlanRepositoryPort productDetailRepositoryPort;
    private final GetProductUseCase getProductUseCase;

    public CreatePlanService(
            PlanRepositoryPort productDetailRepositoryPort,
            GetProductUseCase getProductUseCase) {
        this.productDetailRepositoryPort = productDetailRepositoryPort;
        this.getProductUseCase = getProductUseCase;
    }

    @Override
    public Plan create(Integer productId, Plan productDetail) {
        Product product = getProductUseCase.getById(productId);
        productDetail.setProduct(product);
        return productDetailRepositoryPort.save(productDetail);
    }
}
