package com.neversion.panel.inventory.infrastructure.adapters.out;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.neversion.panel.shared.domain.model.enums.AccountType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@Builder
@SQLDelete(sql = "UPDATE inventory SET is_active = false WHERE id = ?")
@SQLRestriction("is_active = true")
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "duration_days")
    private Integer durationDays;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private Instant createdAt;

    public InventoryEntity() {
    }

    public InventoryEntity(Long id, UUID productId, BigDecimal price, Integer durationDays,
            AccountType accountType, Integer stock, Boolean isActive, Instant createdAt) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.durationDays = durationDays;
        this.accountType = accountType;
        this.stock = stock;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }
}
