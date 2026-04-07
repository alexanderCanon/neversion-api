package com.neversion.api.product.application.port.in;

import java.util.UUID;

public interface DeleteProductUseCase {
    void delete(UUID id);
}
