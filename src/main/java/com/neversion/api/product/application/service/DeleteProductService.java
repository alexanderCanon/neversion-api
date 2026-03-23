package com.neversion.api.product.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.api.exception.BusinessRuleException;
import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.product.application.port.in.DeleteProductUseCase;
import com.neversion.api.product.domain.port.out.ProductRepositoryPort;
import com.neversion.api.inventory.domain.port.out.InventoryRepositoryPort;

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
