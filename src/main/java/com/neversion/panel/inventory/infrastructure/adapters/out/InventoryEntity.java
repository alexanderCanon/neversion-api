package com.neversion.panel.inventory.infrastructure.adapters.out;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.neversion.panel.credential.infrastructure.adapters.out.CredentialEntity;
import com.neversion.panel.inventory.domain.model.enums.AccountType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "inventory")
@Getter
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credentials_id", insertable = false, updatable = false)
    CredentialEntity credential;

    @Column(name = "credentials_id")
    Long credentialsId;

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

    public InventoryEntity() {}

    public InventoryEntity(Long id, Long credentialsId, String seller, BigDecimal priceSeller,
        Integer stock, AccountType accountType, LocalDate expirationDate, Boolean isActive) {
        this.id = id;
        this.credentialsId = credentialsId;
        this.seller = seller;
        this.priceSeller = priceSeller;
        this.stock = stock;
        this.accountType = accountType;
        this.expirationDate = expirationDate;
        this.isActive = isActive;
    }
}
