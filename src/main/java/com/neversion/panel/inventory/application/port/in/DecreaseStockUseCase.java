package com.neversion.panel.inventory.application.port.in;

public interface DecreaseStockUseCase {
    void decrease(Long id, Integer quantity);
}
