package com.neversion.api.inventory.application.port.in;

public interface DecreaseStockUseCase {
    void decrease(Long id, Integer quantity);
}
