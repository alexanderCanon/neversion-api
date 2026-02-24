package com.neversion.panel.sserviceitem.infrastructure.adapters.out;

import java.math.BigDecimal;

import com.neversion.panel.sservice.infrastructure.adapters.out.SserviceEntity;
import com.neversion.panel.sserviceitem.domain.model.enums.AccountType;

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
@Table(name = "service_items")
@Getter
@Setter
@Builder
public class SserviceItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", insertable = false, updatable = false, nullable = false) // FK
    private SserviceEntity service;

    @Column(name = "service_id")
    private Integer serviceId;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "duration")
    private String duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    public SserviceItemEntity() {
    }

    public SserviceItemEntity(
            Long id,
            SserviceEntity service,
            Integer serviceId,
            BigDecimal price,
            String duration,
            AccountType accountType) {
        this.id = id;
        this.service = service;
        this.serviceId = serviceId;
        this.price = price;
        this.duration = duration;
        this.accountType = accountType;
    }
}
