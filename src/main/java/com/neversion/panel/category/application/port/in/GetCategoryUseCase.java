package com.neversion.panel.category.application.port.in;

import java.util.List;

import com.neversion.panel.category.domain.model.Category;

public interface GetCategoryUseCase {
    Category getById(Integer id);
    Category getByName(String name);
    List<Category> getAll();
}
