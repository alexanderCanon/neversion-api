package com.neversion.panel.account.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.account.domain.model.Account;
import com.neversion.panel.account.infrastructure.adapters.in.rest.dto.AccountRequest;
import com.neversion.panel.account.infrastructure.adapters.in.rest.dto.AccountResponse;
import com.neversion.panel.shared.domain.model.enums.AccountStatus;

@Component
public class AccountMapper {

    public Account toDomain(AccountRequest request) {
        return new Account(
                null,
                request.getEmail(),
                request.getPass(),
                request.getProductId(),
                request.getSeller(),
                request.getPriceSeller(),
                request.getAccountType(),
                AccountStatus.AVAILABLE,
                request.getExpirationDate(),
                true,
                null);
    }

    public AccountResponse toResponse(Account account) {
        return new AccountResponse(
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
