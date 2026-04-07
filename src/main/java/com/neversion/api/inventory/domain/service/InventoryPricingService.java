package com.neversion.api.inventory.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.neversion.api.inventory.domain.model.Inventory;

/**
 * Domain service for inventory pricing logic.
 * <p>
 * Implements BR-04 (Duration Discount):
 * If an inventory item has a duration of 90+ days, a 3% discount
 * is applied on the proportional monthly base price.
 * </p>
 */
@Service
public class InventoryPricingService {

    private static final int DISCOUNT_THRESHOLD_DAYS = 90;
    private static final BigDecimal DISCOUNT_PERCENTAGE = new BigDecimal("0.03");
    private static final int DAYS_PER_MONTH = 30;

    /**
     * Applies BR-04 duration discount if duration is 90+ days.
     * Mutates the inventory price in-place.
     *
     * @param inventory the inventory item to evaluate and potentially discount
     */
    public void applyDurationDiscount(Inventory inventory) {
        Integer durationDays = inventory.getDurationDays();
        if (durationDays == null || durationDays <= 0) {
            return;
        }

        if (durationDays >= DISCOUNT_THRESHOLD_DAYS) {
            BigDecimal monthlyPrice = calculateMonthlyPrice(inventory.getPrice(), durationDays);
            BigDecimal discount = monthlyPrice.multiply(DISCOUNT_PERCENTAGE);
            BigDecimal discountedMonthlyPrice = monthlyPrice.subtract(discount);
            BigDecimal totalDiscountedPrice = discountedMonthlyPrice
                    .multiply(BigDecimal.valueOf((double) durationDays / DAYS_PER_MONTH))
                    .setScale(2, RoundingMode.HALF_UP);
            inventory.setPrice(totalDiscountedPrice);
        }
    }

    /**
     * Converts a total price into a proportional monthly price.
     */
    private BigDecimal calculateMonthlyPrice(BigDecimal totalPrice, int days) {
        return totalPrice.divide(
                BigDecimal.valueOf((double) days / DAYS_PER_MONTH),
                2, RoundingMode.HALF_UP);
    }
}
