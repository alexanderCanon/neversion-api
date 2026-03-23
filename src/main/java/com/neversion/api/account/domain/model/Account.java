package com.neversion.api.account.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.neversion.api.shared.domain.model.enums.AccountStatus;
import com.neversion.api.shared.domain.model.enums.AccountType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Account {
        private UUID id;
        private String email;
        private String pass;
        private Long inventoryId;
        private String seller;
        private BigDecimal priceSeller;
        private AccountType accountType;
        private AccountStatus status;
        private LocalDate expirationDate;

        public Account(UUID id, String email, String pass, Long inventoryId, String seller,
                        BigDecimal priceSeller, AccountType accountType, AccountStatus status,
                        LocalDate expirationDate) {
                this.id = id;
                this.email = email;
                this.pass = pass;
                this.inventoryId = inventoryId;
                this.seller = seller;
                this.priceSeller = priceSeller;
                this.accountType = accountType;
                this.status = status;
                this.expirationDate = expirationDate;
        }
}
