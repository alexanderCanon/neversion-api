package com.neversion.panel.product.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.panel.exception.BusinessRuleException;
import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.product.application.port.in.DeleteProductUseCase;
import com.neversion.panel.product.domain.port.out.ProductRepositoryPort;
import com.neversion.panel.inventory.domain.port.out.InventoryRepositoryPort;

@Service
public class DeleteProductService implements DeleteProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;
    private final InventoryRepositoryPort inventoryRepositoryPort;

    public DeleteProductService(ProductRepositoryPort productRepositoryPort,
            InventoryRepositoryPort inventoryRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
        this.inventoryRepositoryPort = inventoryRepositoryPort;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!productRepositoryPort.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Product with id " + id + " not found");
        }

        if (inventoryRepositoryPort.existsByProductId(id)) {
            throw new BusinessRuleException("Cannot delete product with active inventories");
        }

        productRepositoryPort.deleteById(id);
    }
}
