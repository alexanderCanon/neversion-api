package com.neversion.panel.inventory.application.port.in;

public interface IncreaseStockUseCase {
    void increase(Long id, Integer quantity);
}
