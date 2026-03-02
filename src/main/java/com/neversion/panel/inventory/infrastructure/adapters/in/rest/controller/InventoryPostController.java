package com.neversion.panel.inventory.infrastructure.adapters.in.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.neversion.panel.inventory.application.port.in.AddInventoryUseCase;
import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.infrastructure.adapters.in.rest.dto.InventoryRequest;
import com.neversion.panel.inventory.infrastructure.adapters.in.rest.dto.InventoryResponse;
import com.neversion.panel.inventory.infrastructure.adapters.in.rest.mapper.InventoryMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryPostController {

    private final AddInventoryUseCase addInventoryUseCase;
    private final InventoryMapper inventoryMapper;

    public InventoryPostController(AddInventoryUseCase addInventoryUseCase, InventoryMapper inventoryMapper) {
        this.addInventoryUseCase = addInventoryUseCase;
        this.inventoryMapper = inventoryMapper;
    }

    @PostMapping
    public ResponseEntity<?> createInventory(@Valid @RequestBody InventoryRequest request) {
        if (request.productId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Se requiere el ID del producto para agregar un detalle de inventario.");
        }

        Inventory inventory = inventoryMapper.toDomain(request);
        Inventory createdInventory = addInventoryUseCase.add(request.productId(), inventory);
        InventoryResponse response = inventoryMapper.toResponse(createdInventory);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
