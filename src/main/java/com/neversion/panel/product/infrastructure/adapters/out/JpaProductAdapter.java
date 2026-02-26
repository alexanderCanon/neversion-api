package com.neversion.panel.product.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.product.domain.model.enums.CategoryType;
import com.neversion.panel.product.domain.port.out.ProductRepositoryPort;
import com.neversion.panel.product.infrastructure.adapters.out.mapper.ProductPersistenceMapper;

@Repository
public class JpaProductAdapter implements ProductRepositoryPort {

    private final SpringDataProductRepository productRepo;
    private final ProductPersistenceMapper productPersistenceMapper;

    public JpaProductAdapter(SpringDataProductRepository productRepo,
            ProductPersistenceMapper productPersistenceMapper) {
        this.productRepo = productRepo;
        this.productPersistenceMapper = productPersistenceMapper;
    }

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = productPersistenceMapper.toEntity(product);
        ProductEntity savedProduct = productRepo.save(productEntity);
        return productPersistenceMapper.toDomain(savedProduct);
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll()
                .stream()
                .map(productPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepo.findById(id)
                .map(productPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productRepo.findByName(name)
                .map(productPersistenceMapper::toDomain);
    }

    @Override
    public List<Product> findByCategory(CategoryType category) {
        return productRepo.findByCategory(category)
                .stream()
                .map(productPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        productRepo.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepo.existsByName(name);
    }
}
