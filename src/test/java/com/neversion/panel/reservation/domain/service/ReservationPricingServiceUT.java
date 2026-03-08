package com.neversion.panel.reservation.domain.service;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.neversion.panel.reservation.domain.model.ReservationDetail;

@DisplayName("ReservationPricingService Unit Tests (BR-03)")
class ReservationPricingServiceUT {

    private ReservationPricingService pricingService;

    @BeforeEach
    void setUp() {
        pricingService = new ReservationPricingService();
    }

    @Nested
    @DisplayName("calculateGrossTotal")
    class CalculateGrossTotal {

        @Test
        @DisplayName("should sum qty × unitPrice for all items")
        void shouldSumAllItems() {
            List<ReservationDetail> details = List.of(
                    new ReservationDetail(null, null, 1L, 2, new BigDecimal("10.00"), null),
                    new ReservationDetail(null, null, 2L, 3, new BigDecimal("5.00"), null));

            BigDecimal gross = pricingService.calculateGrossTotal(details);

            assertThat(gross).isEqualByComparingTo("35.00");
        }

        @Test
        @DisplayName("should return zero for empty list")
        void shouldReturnZeroForEmptyList() {
            BigDecimal gross = pricingService.calculateGrossTotal(List.of());

            assertThat(gross).isEqualByComparingTo("0");
        }
    }

    @Nested
    @DisplayName("calculateComboDiscount (BR-03)")
    class CalculateComboDiscount {

        @Test
        @DisplayName("should return 0 discount for 1 item")
        void shouldReturnZeroForSingleItem() {
            BigDecimal discount = pricingService.calculateComboDiscount(
                    new BigDecimal("100.00"), 1);

            assertThat(discount).isEqualByComparingTo("0");
        }

        @Test
        @DisplayName("should return 2% discount for 2 items")
        void shouldReturn2PercentFor2Items() {
            BigDecimal discount = pricingService.calculateComboDiscount(
                    new BigDecimal("100.00"), 2);

            assertThat(discount).isEqualByComparingTo("2.00");
        }

        @Test
        @DisplayName("should return 2% discount for 5 items")
        void shouldReturn2PercentFor5Items() {
            BigDecimal discount = pricingService.calculateComboDiscount(
                    new BigDecimal("250.00"), 5);

            assertThat(discount).isEqualByComparingTo("5.00");
        }
    }

    @Nested
    @DisplayName("calculateFinalTotal")
    class CalculateFinalTotal {

        @Test
        @DisplayName("should subtract discount from gross total")
        void shouldSubtractDiscount() {
            BigDecimal result = pricingService.calculateFinalTotal(
                    new BigDecimal("100.00"), new BigDecimal("2.00"));

            assertThat(result).isEqualByComparingTo("98.00");
        }

        @Test
        @DisplayName("should return same as gross when discount is zero")
        void shouldReturnGrossWhenNoDiscount() {
            BigDecimal result = pricingService.calculateFinalTotal(
                    new BigDecimal("50.00"), BigDecimal.ZERO);

            assertThat(result).isEqualByComparingTo("50.00");
        }
    }
}
