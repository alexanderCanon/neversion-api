package com.neversion.api.accountslot.infrastructure.adapters.in.rest.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.api.accountslot.application.port.in.AccountSlotUseCase;
import com.neversion.api.accountslot.domain.model.AccountSlot;
import com.neversion.api.accountslot.infrastructure.adapters.in.rest.dto.AccountSlotRequest;
import com.neversion.api.accountslot.infrastructure.adapters.in.rest.dto.AccountSlotResponse;
import com.neversion.api.accountslot.infrastructure.adapters.in.rest.mapper.AccountSlotMapper;
import com.neversion.api.exception.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/account-slots")
@Tag(name = "Account Slots", description = "Manage account slots (profiles within master accounts)")
public class AccountSlotController {

    private final AccountSlotUseCase accountSlotUseCase;
    private final AccountSlotMapper accountSlotMapper;

    public AccountSlotController(AccountSlotUseCase accountSlotUseCase, AccountSlotMapper accountSlotMapper) {
        this.accountSlotUseCase = accountSlotUseCase;
        this.accountSlotMapper = accountSlotMapper;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get slot by ID")
    @ApiResponse(responseCode = "200", description = "Slot found")
    @ApiResponse(responseCode = "404", description = "Slot not found")
    public ResponseEntity<AccountSlotResponse> getById(
            @Parameter(description = "Slot UUID") @PathVariable UUID id) {

        AccountSlot slot = accountSlotUseCase.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found: " + id));
        return ResponseEntity.ok(accountSlotMapper.toResponse(slot));
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "List slots for an account")
    @ApiResponse(responseCode = "200", description = "Slots returned")
    public ResponseEntity<List<AccountSlotResponse>> getByAccount(
            @Parameter(description = "Account UUID") @PathVariable UUID accountId) {

        List<AccountSlotResponse> slots = accountSlotUseCase.findByAccountId(accountId).stream()
                .map(accountSlotMapper::toResponse)
                .toList();
        return ResponseEntity.ok(slots);
    }

    @GetMapping("/account/{accountId}/available")
    @Operation(summary = "List available slots for an account")
    @ApiResponse(responseCode = "200", description = "Available slots returned")
    public ResponseEntity<List<AccountSlotResponse>> getAvailableByAccount(
            @Parameter(description = "Account UUID") @PathVariable UUID accountId) {

        List<AccountSlotResponse> slots = accountSlotUseCase.findAvailableByAccountId(accountId).stream()
                .map(accountSlotMapper::toResponse)
                .toList();
        return ResponseEntity.ok(slots);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update slot details", description = "Update profile name and pin of a slot.")
    @ApiResponse(responseCode = "200", description = "Slot updated")
    @ApiResponse(responseCode = "404", description = "Slot not found")
    public ResponseEntity<AccountSlotResponse> update(
            @Parameter(description = "Slot UUID") @PathVariable UUID id,
            @Valid @RequestBody AccountSlotRequest request) {

        AccountSlot slot = accountSlotUseCase.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found: " + id));

        slot.setProfileName(request.profileName());
        slot.setPin(request.pin());

        AccountSlot saved = accountSlotUseCase.save(slot);
        return ResponseEntity.ok(accountSlotMapper.toResponse(saved));
    }
}
