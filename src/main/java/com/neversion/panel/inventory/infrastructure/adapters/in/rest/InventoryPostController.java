package com.neversion.panel.inventory.infrastructure.adapters.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.inventory.application.port.in.CreateInventoryUseCase;
import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.infrastructure.adapters.in.rest.dto.InventoryRequest;
import com.neversion.panel.inventory.infrastructure.adapters.in.rest.dto.InventoryResponse;
import com.neversion.panel.inventory.infrastructure.adapters.in.rest.mapper.InventoryMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/inventories")
public class InventoryPostController {
    private final CreateInventoryUseCase createInventoryUseCase;
    private final InventoryMapper inventoryMapper;

    public InventoryPostController(CreateInventoryUseCase createInventoryUseCase, InventoryMapper inventoryMapper) {
        this.createInventoryUseCase = createInventoryUseCase;
        this.inventoryMapper = inventoryMapper;
    }

    @PostMapping
    public ResponseEntity<InventoryResponse> createInventory(@Valid @RequestBody InventoryRequest request) {
        Inventory inventory = inventoryMapper.toDomain(request);
        Inventory created = createInventoryUseCase.create(inventory);
        InventoryResponse response = inventoryMapper.toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
