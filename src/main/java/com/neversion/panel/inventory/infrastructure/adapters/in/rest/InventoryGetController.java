package com.neversion.panel.inventory.infrastructure.adapters.in.rest;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.inventory.application.port.in.GetInventoryUseCase;
import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.domain.model.enums.AccountType;
import com.neversion.panel.inventory.infrastructure.adapters.in.rest.dto.InventoryResponse;
import com.neversion.panel.inventory.infrastructure.adapters.in.rest.mapper.InventoryMapper;

@RestController
@RequestMapping("/api/v1/inventories")
public class InventoryGetController {

    private final GetInventoryUseCase getInventoryUseCase;
    private final InventoryMapper inventoryMapper;

    public InventoryGetController(GetInventoryUseCase getInventoryUseCase, InventoryMapper inventoryMapper) {
        this.getInventoryUseCase = getInventoryUseCase;
        this.inventoryMapper = inventoryMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> getInventoryById(@PathVariable Long id) {
        Inventory inventory = getInventoryUseCase.getById(id);
        InventoryResponse response = inventoryMapper.toResponse(inventory);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getInventories(
        @RequestParam(required = false) String seller,
        @RequestParam(required = false) AccountType accountType,
        @RequestParam(required = false) LocalDate expirationBefore,
        @RequestParam(required = false) Boolean isActive) {

        if (seller != null && !seller.isBlank()) {
            List<InventoryResponse> response = getInventoryUseCase.getBySeller(seller).stream()
                .map(inventoryMapper::toResponse)
                .toList();
            return ResponseEntity.ok(response);
        }

        if (accountType != null) {
            List<InventoryResponse> response = getInventoryUseCase.getByAccountType(accountType).stream()
                .map(inventoryMapper::toResponse)
                .toList();
            return ResponseEntity.ok(response);
        }

        if (expirationBefore != null) {
            List<InventoryResponse> response = getInventoryUseCase.getByExpirationDateBefore(expirationBefore).stream()
                .map(inventoryMapper::toResponse)
                .toList();
            return ResponseEntity.ok(response);
        }

        if (isActive != null) {
            List<InventoryResponse> response = getInventoryUseCase.getByIsActive(isActive).stream()
                .map(inventoryMapper::toResponse)
                .toList();
            return ResponseEntity.ok(response);
        }

        List<InventoryResponse> response = getInventoryUseCase.getAll().stream()
            .map(inventoryMapper::toResponse)
            .toList();
        return ResponseEntity.ok(response);
    }
}
