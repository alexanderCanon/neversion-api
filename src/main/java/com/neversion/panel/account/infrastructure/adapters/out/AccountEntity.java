package com.neversion.panel.account.infrastructure.adapters.out;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.neversion.panel.account.domain.model.enums.AccountType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "accounts")
@Getter
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "email")
    String email;

    @Column(name = "pass")
    String pass;

    @Column(name = "service_id")
    Integer serviceId;

    @Column(name = "seller")
    String seller;

    @Column(name = "price_seller")
    BigDecimal priceSeller;

    @Column(name = "stock")
    Integer stock;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "account_type")
    AccountType accountType;

    @Column(name = "expiration_date")
    LocalDate expirationDate;

    @Column(name = "is_active")
    Boolean isActive;

    @Column(name = "created_at")
    Instant createdAt;

    public AccountEntity() {
    }

    public AccountEntity(Long id, String email, String pass, Integer serviceId, String seller,
            BigDecimal priceSeller, Integer stock, AccountType accountType, LocalDate expirationDate,
            Boolean isActive) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.serviceId = serviceId;
        this.seller = seller;
        this.priceSeller = priceSeller;
        this.stock = stock;
        this.accountType = accountType;
        this.expirationDate = expirationDate;
        this.isActive = isActive;
    }
}
