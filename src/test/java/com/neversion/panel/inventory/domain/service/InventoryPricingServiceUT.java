package com.neversion.panel.inventory.domain.service;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.neversion.panel.inventory.domain.model.Inventory;

@DisplayName("InventoryPricingService Unit Tests (BR-04)")
class InventoryPricingServiceUT {

    private InventoryPricingService pricingService;

    @BeforeEach
    void setUp() {
        pricingService = new InventoryPricingService();
    }

    @Nested
    @DisplayName("applyDurationDiscount (BR-04)")
    class ApplyDurationDiscount {

        @Test
        @DisplayName("should NOT apply discount for 30-day duration")
        void shouldNotDiscountShortDuration() {
            Inventory inventory = new Inventory();
            inventory.setPrice(new BigDecimal("30.00"));
            inventory.setDurationDays(30);

            pricingService.applyDurationDiscount(inventory);

            assertThat(inventory.getPrice()).isEqualByComparingTo("30.00");
        }

        @Test
        @DisplayName("should NOT apply discount for 89-day duration")
        void shouldNotDiscount89Days() {
            Inventory inventory = new Inventory();
            inventory.setPrice(new BigDecimal("89.00"));
            inventory.setDurationDays(89);

            pricingService.applyDurationDiscount(inventory);

            assertThat(inventory.getPrice()).isEqualByComparingTo("89.00");
        }

        @Test
        @DisplayName("should apply 3% discount for exactly 90-day duration")
        void shouldDiscount90Days() {
            Inventory inventory = new Inventory();
            inventory.setPrice(new BigDecimal("90.00"));
            inventory.setDurationDays(90);

            pricingService.applyDurationDiscount(inventory);

            // Monthly price = 90/3 = 30.00
            // 3% of 30.00 = 0.90
            // Discounted monthly = 29.10
            // Total = 29.10 * 3 = 87.30
            assertThat(inventory.getPrice()).isEqualByComparingTo("87.30");
        }

        @Test
        @DisplayName("should apply 3% discount for 365-day duration")
        void shouldDiscount365Days() {
            Inventory inventory = new Inventory();
            inventory.setPrice(new BigDecimal("365.00"));
            inventory.setDurationDays(365);

            pricingService.applyDurationDiscount(inventory);

            // Should be less than original price (3% discount on monthly rate)
            assertThat(inventory.getPrice()).isLessThan(new BigDecimal("365.00"));
        }

        @Test
        @DisplayName("should handle null duration gracefully")
        void shouldHandleNullDuration() {
            Inventory inventory = new Inventory();
            inventory.setPrice(new BigDecimal("50.00"));
            inventory.setDurationDays(null);

            pricingService.applyDurationDiscount(inventory);

            assertThat(inventory.getPrice()).isEqualByComparingTo("50.00");
        }

        @Test
        @DisplayName("should handle zero duration gracefully")
        void shouldHandleZeroDuration() {
            Inventory inventory = new Inventory();
            inventory.setPrice(new BigDecimal("50.00"));
            inventory.setDurationDays(0);

            pricingService.applyDurationDiscount(inventory);

            assertThat(inventory.getPrice()).isEqualByComparingTo("50.00");
        }
    }
}
