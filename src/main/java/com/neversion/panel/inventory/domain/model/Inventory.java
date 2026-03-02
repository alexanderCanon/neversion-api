package com.neversion.panel.inventory.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.neversion.panel.shared.domain.model.enums.AccountType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Inventory {

        private Long id;
        private UUID productId;
        private BigDecimal price;
        private Integer durationDays;
        private AccountType accountType;
        private Integer stock;

        public Inventory() {
        }

        public Inventory(Long id, UUID productId, BigDecimal price, Integer durationDays,
                        AccountType accountType, Integer stock) {
                this.id = id;
                this.productId = productId;
                this.price = price;
                this.durationDays = durationDays;
                this.accountType = accountType;
                this.stock = stock;
        }
}
