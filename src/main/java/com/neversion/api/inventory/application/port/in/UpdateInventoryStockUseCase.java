package com.neversion.api.inventory.application.port.in;

public interface UpdateInventoryStockUseCase {
    void updateStock(Long id, Integer newStock);
}
