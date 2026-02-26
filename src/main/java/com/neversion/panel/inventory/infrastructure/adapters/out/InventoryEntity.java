package com.neversion.panel.inventory.infrastructure.adapters.out;

import java.math.BigDecimal;
import jakarta.persistence.Version;

import com.neversion.panel.inventory.domain.model.enums.AccountType;
import com.neversion.panel.product.infrastructure.adapters.out.ProductEntity;

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
@Table(name = "inventory")
@Getter
@Setter
@Builder
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Integer productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private ProductEntity product;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "duration")
    private String duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @Column(name = "stock")
    private Integer stock;

    @Version
    private Long version;

    public InventoryEntity() {
    }

    public InventoryEntity(
            Long id,
            Integer productId,
            ProductEntity product,
            BigDecimal price,
            String duration,
            AccountType accountType,
            Integer stock,
            Long version) {
        this.id = id;
        this.productId = productId;
        this.product = product;
        this.price = price;
        this.duration = duration;
        this.stock = stock;
        this.accountType = accountType;
        this.version = version;
    }
}
