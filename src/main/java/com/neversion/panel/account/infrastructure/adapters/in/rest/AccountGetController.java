package com.neversion.panel.account.infrastructure.adapters.in.rest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.account.application.port.in.GetAccountUseCase;
import com.neversion.panel.account.domain.model.Account;
import com.neversion.panel.account.infrastructure.adapters.in.rest.dto.AccountResponse;
import com.neversion.panel.account.infrastructure.adapters.in.rest.mapper.AccountMapper;
import com.neversion.panel.shared.domain.model.enums.AccountType;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountGetController {

    private final GetAccountUseCase getAccountUseCase;
    private final AccountMapper accountMapper;

    public AccountGetController(GetAccountUseCase getAccountUseCase, AccountMapper accountMapper) {
        this.getAccountUseCase = getAccountUseCase;
        this.accountMapper = accountMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable UUID id) {
        Account account = getAccountUseCase.getById(id);
        AccountResponse response = accountMapper.toResponse(account);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAccounts(
            @RequestParam(required = false) String seller,
            @RequestParam(required = false) AccountType accountType,
            @RequestParam(required = false) LocalDate expirationBefore,
            @RequestParam(required = false) Boolean isActive) {

        if (seller != null && !seller.isBlank()) {
            List<AccountResponse> response = getAccountUseCase.getBySeller(seller).stream()
                    .map(accountMapper::toResponse)
                    .toList();
            return ResponseEntity.ok(response);
        }

        if (accountType != null) {
            List<AccountResponse> response = getAccountUseCase.getByAccountType(accountType).stream()
                    .map(accountMapper::toResponse)
                    .toList();
            return ResponseEntity.ok(response);
        }

        if (expirationBefore != null) {
            List<AccountResponse> response = getAccountUseCase.getByExpirationDateBefore(expirationBefore).stream()
                    .map(accountMapper::toResponse)
                    .toList();
            return ResponseEntity.ok(response);
        }

        if (isActive != null) {
            List<AccountResponse> response = getAccountUseCase.getByIsActive(isActive).stream()
                    .map(accountMapper::toResponse)
                    .toList();
            return ResponseEntity.ok(response);
        }

        List<AccountResponse> response = getAccountUseCase.getAll().stream()
                .map(accountMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}
