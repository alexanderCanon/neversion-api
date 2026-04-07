package com.neversion.api.inventory.application.port.in;

public interface IncreaseStockUseCase {
    void increase(Long id, Integer quantity);
}
