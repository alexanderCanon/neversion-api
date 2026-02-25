package com.neversion.panel.plan.domain.model;

import java.math.BigDecimal;

import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.plan.domain.model.enums.AccountType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class Plan {

        private Long id;
        @Setter
        private Product product;
        private ProductPrice price; // record
        private String duration;
        private AccountType accountType;

        public void updatePrice(BigDecimal newAmount) {
                this.price = new ProductPrice(newAmount);
        }

        public void applyDiscount(BigDecimal percentage) {
                BigDecimal newAmount = this.price.amount().multiply(BigDecimal.ONE.subtract(percentage));
                this.price = new ProductPrice(newAmount);
        }

        public Plan() {
        }

        public Plan(Long id, Product product, ProductPrice price, String duration,
                        AccountType accountType) {
                this.id = id;
                this.product = product;
                this.price = price;
                this.duration = duration;
                this.accountType = accountType;
        }
}