package com.neversion.panel.account.infrastructure.adapters.out;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.neversion.panel.infrastructure.AuditableEntity;
import com.neversion.panel.shared.domain.model.enums.AccountStatus;
import com.neversion.panel.shared.domain.model.enums.AccountType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class AccountEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "pass")
    private String pass;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "seller")
    private String seller;

    @Column(name = "price_seller")
    private BigDecimal priceSeller;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    public AccountEntity() {
    }

    public AccountEntity(UUID id, String email, String pass, UUID productId, String seller,
            BigDecimal priceSeller, AccountType accountType, AccountStatus status,
            LocalDate expirationDate) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.productId = productId;
        this.seller = seller;
        this.priceSeller = priceSeller;
        this.accountType = accountType;
        this.status = status;
        this.expirationDate = expirationDate;
    }
}
