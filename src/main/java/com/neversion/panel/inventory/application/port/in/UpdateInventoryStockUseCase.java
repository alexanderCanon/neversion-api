package com.neversion.panel.inventory.application.port.in;

public interface UpdateInventoryStockUseCase {
    void updateStock(Long id, Integer newStock);
}
