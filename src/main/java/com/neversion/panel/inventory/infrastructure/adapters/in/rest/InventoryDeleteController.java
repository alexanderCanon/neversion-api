package com.neversion.panel.inventory.infrastructure.adapters.in.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.inventory.application.port.in.DeactivateInventoryUseCase;

@RestController
@RequestMapping("/api/v1/inventories")
public class InventoryDeleteController {

    private final DeactivateInventoryUseCase deactivateInventoryUseCase;

    public InventoryDeleteController(DeactivateInventoryUseCase deactivateInventoryUseCase) {
        this.deactivateInventoryUseCase = deactivateInventoryUseCase;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateInventory(@PathVariable Long id) {
        deactivateInventoryUseCase.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
