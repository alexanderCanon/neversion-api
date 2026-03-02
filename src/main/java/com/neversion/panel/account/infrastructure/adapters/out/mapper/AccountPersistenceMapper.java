package com.neversion.panel.account.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.account.domain.model.Account;
import com.neversion.panel.account.infrastructure.adapters.out.AccountEntity;

@Component
public class AccountPersistenceMapper {

    public Account toDomain(AccountEntity entity) {
        return new Account(
                entity.getId(),
                entity.getEmail(),
                entity.getPass(),
                entity.getProductId(),
                entity.getSeller(),
                entity.getPriceSeller(),
                entity.getAccountType(),
                entity.getStatus(),
                entity.getExpirationDate(),
                entity.getIsActive(),
                entity.getCreatedAt());
    }

    public AccountEntity toEntity(Account account) {
        return new AccountEntity(
                account.id(),
                account.email(),
                account.pass(),
                account.productId(),
                account.seller(),
                account.priceSeller(),
                account.accountType(),
                account.status(),
                account.expirationDate(),
                account.isActive());
    }
}
