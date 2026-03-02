package com.neversion.panel.inventory.application.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.neversion.panel.inventory.application.port.in.AddInventoryUseCase;
import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.domain.port.out.InventoryRepositoryPort;
import com.neversion.panel.product.application.port.in.GetProductUseCase;

@Service
public class AddInventoryService implements AddInventoryUseCase {

    private static final int DISCOUNT_THRESHOLD_DAYS = 90;
    private static final BigDecimal DISCOUNT_PERCENTAGE = new BigDecimal("0.03");

    private final InventoryRepositoryPort inventoryRepositoryPort;
    private final GetProductUseCase getProductUseCase;

    public AddInventoryService(
            InventoryRepositoryPort inventoryRepositoryPort,
            GetProductUseCase getProductUseCase) {
        this.inventoryRepositoryPort = inventoryRepositoryPort;
        this.getProductUseCase = getProductUseCase;
    }

    @Override
    public Inventory add(UUID productId, Inventory inventory) {
        // Validate that the product exists
        getProductUseCase.getById(productId);
        inventory.setProductId(productId);

        applyDurationDiscount(inventory);

        return inventoryRepositoryPort.save(inventory);
    }

    private void applyDurationDiscount(Inventory inventory) {
        Integer durationDays = inventory.getDurationDays();
        if (durationDays == null || durationDays <= 0) {
            return;
        }

        if (durationDays >= DISCOUNT_THRESHOLD_DAYS) {
            BigDecimal monthlyPrice = calculateMonthlyPrice(inventory.getPrice(), durationDays);
            BigDecimal discount = monthlyPrice.multiply(DISCOUNT_PERCENTAGE);
            BigDecimal discountedMonthlyPrice = monthlyPrice.subtract(discount);
            BigDecimal totalDiscountedPrice = discountedMonthlyPrice.multiply(BigDecimal.valueOf(durationDays / 30.0))
                    .setScale(2, RoundingMode.HALF_UP);
            inventory.setPrice(totalDiscountedPrice);
        }
    }

    private BigDecimal calculateMonthlyPrice(BigDecimal totalPrice, int days) {
        return totalPrice.divide(BigDecimal.valueOf(days / 30.0), 2, RoundingMode.HALF_UP);
    }
}
