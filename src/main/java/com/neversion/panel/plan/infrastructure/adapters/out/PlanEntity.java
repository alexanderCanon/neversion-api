package com.neversion.panel.plan.infrastructure.adapters.out;

import java.math.BigDecimal;

import com.neversion.panel.product.infrastructure.adapters.out.ProductEntity;
import com.neversion.panel.plan.domain.model.enums.AccountType;

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
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "plans")
@Getter
@Setter
@Builder
public class PlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false, nullable = false) // FK
    private ProductEntity product;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "duration")
    private String duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    public PlanEntity() {
    }

    public PlanEntity(
            Long id,
            ProductEntity product,
            Integer productId,
            BigDecimal price,
            String duration,
            AccountType accountType) {
        this.id = id;
        this.product = product;
        this.productId = productId;
        this.price = price;
        this.duration = duration;
        this.accountType = accountType;
    }
}
