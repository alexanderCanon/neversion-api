package com.neversion.panel.inventory.application.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.neversion.panel.inventory.application.port.in.AddInventoryUseCase;
import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.domain.port.out.InventoryRepositoryPort;
import com.neversion.panel.product.application.port.in.GetProductUseCase;
import com.neversion.panel.product.domain.model.Product;

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
    public Inventory add(Long productId, Inventory inventory) {
        Product product = getProductUseCase.getById(productId);
        inventory.setProduct(product);
        
        applyDurationDiscount(inventory);
        
        return inventoryRepositoryPort.save(inventory);
    }

    private void applyDurationDiscount(Inventory inventory) {
        String duration = inventory.getDuration();
        if (duration == null) {
            return;
        }

        int days = extractDaysFromDuration(duration);
        if (days >= DISCOUNT_THRESHOLD_DAYS) {
            BigDecimal monthlyPrice = calculateMonthlyPrice(inventory.getPrice(), days);
            BigDecimal discount = monthlyPrice.multiply(DISCOUNT_PERCENTAGE);
            BigDecimal discountedMonthlyPrice = monthlyPrice.subtract(discount);
            BigDecimal totalDiscountedPrice = discountedMonthlyPrice.multiply(BigDecimal.valueOf(days / 30.0))
                    .setScale(2, RoundingMode.HALF_UP);
            inventory.setPrice(totalDiscountedPrice);
        }
    }

    private int extractDaysFromDuration(String duration) {
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(duration);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    private BigDecimal calculateMonthlyPrice(BigDecimal totalPrice, int days) {
        return totalPrice.divide(BigDecimal.valueOf(days / 30.0), 2, RoundingMode.HALF_UP);
    }
}
