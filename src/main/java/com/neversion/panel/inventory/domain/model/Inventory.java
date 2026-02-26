package com.neversion.panel.inventory.domain.model;

import java.math.BigDecimal;

import com.neversion.panel.inventory.domain.model.enums.AccountType;
import com.neversion.panel.product.domain.model.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Inventory {

        private Long id;
        private Product product;
        private BigDecimal price;
        private String duration;
        private AccountType accountType;
        private Integer stock;

        public void updatePrice(BigDecimal newAmount) {
                this.price = newAmount;
        }

        public void applyDiscount(BigDecimal percentage) {
                BigDecimal newAmount = this.price.multiply(BigDecimal.ONE.subtract(percentage));
                this.price = newAmount;
        }

        public Inventory() {
        }

        public Inventory(Long id, Product product, BigDecimal price, String duration,
                        AccountType accountType, Integer stock) {
                this.id = id;
                this.product = product;
                this.price = price;
                this.duration = duration;
                this.accountType = accountType;
                this.stock = stock;
        }
}
