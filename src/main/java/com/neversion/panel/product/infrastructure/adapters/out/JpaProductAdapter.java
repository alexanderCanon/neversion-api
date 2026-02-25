package com.neversion.panel.product.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.product.domain.port.out.ProductRepositoryPort;
import com.neversion.panel.product.infrastructure.adapters.out.mapper.ProductPersistenceMapper;

@Repository
public class JpaProductAdapter implements ProductRepositoryPort {

    private final ProductRepositoryAdapter productRepo;
    private final ProductPersistenceMapper productPersistenceMapper;

    public JpaProductAdapter(ProductRepositoryAdapter productRepo,
            ProductPersistenceMapper productPersistenceMapper) {
        this.productRepo = productRepo;
        this.productPersistenceMapper = productPersistenceMapper;
    }

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = productPersistenceMapper.toEntity(product);
        ProductEntity savedPlatform = productRepo.save(productEntity);
        return productPersistenceMapper.toDomain(savedPlatform);
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll()
                .stream()
                .map(productPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepo.findById(id)
                .map(productPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productRepo.findByName(name)
                .map(productPersistenceMapper::toDomain);
    }

    // @Override
    // public List<Product> findByCategory() {
    // return productRepositoryAdapter.findByCategory()
    // .stream()
    // .map(productPersistenceMapper::toDomain)
    // .toList();
    // }

}
