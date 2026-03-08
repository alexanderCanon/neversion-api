package com.neversion.panel.account.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.account.domain.model.Account;
import com.neversion.panel.account.infrastructure.adapters.out.AccountEntity;

@Component
public class AccountPersistenceMapper {

    public Account toDomain(AccountEntity entity) {
        return entity != null ? Account.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .pass(entity.getPass())
                .inventoryId(entity.getInventoryId())
                .seller(entity.getSeller())
                .priceSeller(entity.getPriceSeller())
                .accountType(entity.getAccountType())
                .status(entity.getStatus())
                .expirationDate(entity.getExpirationDate())
                .build() : null;
    }

    public AccountEntity toEntity(Account account) {
        return account != null ? AccountEntity.builder()
                .id(account.getId())
                .email(account.getEmail())
                .pass(account.getPass())
                .inventoryId(account.getInventoryId())
                .seller(account.getSeller())
                .priceSeller(account.getPriceSeller())
                .accountType(account.getAccountType())
                .status(account.getStatus())
                .expirationDate(account.getExpirationDate())
                .build() : null;
    }
}
