package com.neversion.panel.account.infrastructure.adapters.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.account.application.port.in.CreateAccountUseCase;
import com.neversion.panel.account.domain.model.Account;
import com.neversion.panel.account.infrastructure.adapters.in.rest.dto.AccountRequest;
import com.neversion.panel.account.infrastructure.adapters.in.rest.dto.AccountResponse;
import com.neversion.panel.account.infrastructure.adapters.in.rest.mapper.AccountMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Accounts")
public class AccountPostController {
    private final CreateAccountUseCase createAccountUseCase;
    private final AccountMapper accountMapper;

    public AccountPostController(CreateAccountUseCase createAccountUseCase, AccountMapper accountMapper) {
        this.createAccountUseCase = createAccountUseCase;
        this.accountMapper = accountMapper;
    }

    @PostMapping
    @Operation(summary = "Create an account", description = "Create a new digital service account")
    @ApiResponse(responseCode = "201", description = "Account created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request or business rule violation")
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest request) {
        Account account = accountMapper.toDomain(request);
        Account created = createAccountUseCase.create(account);
        AccountResponse response = accountMapper.toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
