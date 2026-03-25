package com.neversion.api.account.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.api.account.domain.model.Account;
import com.neversion.api.account.infrastructure.adapters.in.rest.dto.AccountRequest;
import com.neversion.api.account.infrastructure.adapters.in.rest.dto.AccountResponse;
import com.neversion.api.shared.domain.model.enums.AccountStatus;
import com.neversion.api.shared.domain.model.enums.AccountType;

@Component
public class AccountMapper {

    public Account toDomain(AccountRequest request) {
        return request != null ? Account.builder()
                .email(request.email())
                .pass(request.pass())
                .inventoryId(request.inventoryId())
                .seller(request.seller())
                .priceSeller(request.priceSeller())
                .status(AccountStatus.valueOf(request.status()))
                .expirationDate(request.expirationDate())
                .build() : null;
    }

    public AccountResponse toResponse(Account account) {
        return account != null ? AccountResponse.builder()
                .id(account.getId())
                .email(account.getEmail())
                .pass(account.getPass())
                .inventoryId(account.getInventoryId())
                .seller(account.getSeller())
                .priceSeller(account.getPriceSeller())
                .status(account.getStatus().name())
                .expirationDate(account.getExpirationDate())
                .build() : null;
    }
}
