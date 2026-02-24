package com.neversion.panel.account.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.account.domain.model.Account;
import com.neversion.panel.account.infrastructure.adapters.in.rest.dto.AccountRequest;
import com.neversion.panel.account.infrastructure.adapters.in.rest.dto.AccountResponse;

@Component
public class AccountMapper {

    public Account toDomain(AccountRequest request) {
        return new Account(
                null,
                request.getEmail(),
                request.getPass(),
                request.getServiceId(),
                request.getSeller(),
                request.getPriceSeller(),
                request.getStock() != null ? request.getStock() : 1,
                request.getAccountType(),
                request.getExpirationDate(),
                true,
                null);
    }

    public AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.id(),
                account.email(),
                account.pass(),
                account.serviceId(),
                account.seller(),
                account.priceSeller(),
                account.stock(),
                account.accountType(),
                account.expirationDate(),
                account.isActive());
    }
}
