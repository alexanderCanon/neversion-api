package com.neversion.panel.sservicedetail.infrastructure.adapters.out;

import java.math.BigDecimal;

import com.neversion.panel.category.infrastructure.adapters.out.CategoryEntity;
import com.neversion.panel.sservice.infrastructure.adapters.out.SserviceEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "services_details")
@Getter
public class SserviceDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", insertable = false, updatable = false)
    SserviceEntity service;

    @Column(name = "service_id")
    Integer serviceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    CategoryEntity category;

    @Column(name = "category_id")
    Integer categoryId;

    @Column(name = "price_individual")
    BigDecimal priceIndividual;

    @Column(name = "price_familiar")
    BigDecimal priceFamiliar;

    public SserviceDetailEntity() {}

    public SserviceDetailEntity(Long id, Integer serviceId, Integer categoryId,
        BigDecimal priceIndividual, BigDecimal priceFamiliar) {
        this.id = id;
        this.serviceId = serviceId;
        this.categoryId = categoryId;
        this.priceIndividual = priceIndividual;
        this.priceFamiliar = priceFamiliar;
    }
}
