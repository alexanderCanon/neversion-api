package com.neversion.panel.inventory.application.port.in;

import java.math.BigDecimal;

public interface UpdateInventoryPriceUseCase {
    void updatePrice(Long id, BigDecimal newPrice);
}
