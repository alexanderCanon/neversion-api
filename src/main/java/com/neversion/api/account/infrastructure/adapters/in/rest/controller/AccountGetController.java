package com.neversion.api.account.infrastructure.adapters.in.rest.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.api.account.application.port.in.GetAccountUseCase;
import com.neversion.api.account.domain.model.Account;
import com.neversion.api.account.infrastructure.adapters.in.rest.dto.AccountResponse;
import com.neversion.api.account.infrastructure.adapters.in.rest.mapper.AccountMapper;
import com.neversion.api.shared.domain.model.enums.AccountType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Accounts", description = "Digital service account management")
public class AccountGetController {

    private final GetAccountUseCase getAccountUseCase;
    private final AccountMapper accountMapper;

    public AccountGetController(GetAccountUseCase getAccountUseCase, AccountMapper accountMapper) {
        this.getAccountUseCase = getAccountUseCase;
        this.accountMapper = accountMapper;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account by ID", description = "Retrieve a single account by its UUID")
    @ApiResponse(responseCode = "200", description = "Account found")
    @ApiResponse(responseCode = "404", description = "Account not found")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable UUID id) {
        Account account = getAccountUseCase.getById(id);
        AccountResponse response = accountMapper.toResponse(account);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get accounts", description = "Retrieve accounts filtered by seller, type, expiration, or active status")
    @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully")
    public ResponseEntity<?> getAccounts(
            @Parameter(description = "Filter by seller name") @RequestParam(required = false) String seller,
            @Parameter(description = "Filter by account type (FAMILY, INDIVIDUAL)") @RequestParam(required = false) AccountType accountType,
            @Parameter(description = "Filter by expiration date before (YYYY-MM-DD)") @RequestParam(required = false) LocalDate expirationBefore,
            @Parameter(description = "Filter by active status") @RequestParam(required = false) Boolean isActive) {

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
