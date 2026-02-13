package com.neversion.panel.category.application.port.in;

import com.neversion.panel.category.domain.model.Category;

public interface CreateCategoryUseCase {
    Category create(Category category);
}
