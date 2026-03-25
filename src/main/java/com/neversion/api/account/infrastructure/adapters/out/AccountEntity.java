package com.neversion.api.account.infrastructure.adapters.out;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.neversion.api.infrastructure.AuditableEntity;
import com.neversion.api.shared.domain.model.enums.AccountStatus;
import com.neversion.api.shared.domain.model.enums.AccountStatus;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@SQLDelete(sql = "UPDATE accounts SET is_active = false WHERE id = ?")
@SQLRestriction("is_active = true")
@Getter
@Setter
@Builder
public class AccountEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "pass")
    private String pass;

    @Column(name = "inventory_id")
    private Long inventoryId;

    @Column(name = "seller")
    private String seller;

    @Column(name = "price_seller")
    private BigDecimal priceSeller;



    @Column(name = "status", columnDefinition = "account_status")
    private AccountStatus status;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    public AccountEntity() {
    }

    public AccountEntity(UUID id, String email, String pass, Long inventoryId, String seller,
            BigDecimal priceSeller, AccountStatus status, LocalDate expirationDate) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.inventoryId = inventoryId;
        this.seller = seller;
        this.priceSeller = priceSeller;
        this.status = status;
        this.expirationDate = expirationDate;
    }
}
