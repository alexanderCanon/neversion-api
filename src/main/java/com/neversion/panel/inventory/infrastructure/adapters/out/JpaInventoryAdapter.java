package com.neversion.panel.inventory.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.domain.model.enums.AccountType;
import com.neversion.panel.inventory.domain.port.out.InventoryRepositoryPort;
import com.neversion.panel.inventory.infrastructure.adapters.out.mapper.InventoryPersistenceMapper;

@Repository
public class JpaInventoryAdapter implements InventoryRepositoryPort {

    private final SpringDataInventoryRepository inventoryRepository;
    private final InventoryPersistenceMapper inventoryPersistenceMapper;

    public JpaInventoryAdapter(SpringDataInventoryRepository inventoryRepository,
            InventoryPersistenceMapper inventoryPersistenceMapper) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryPersistenceMapper = inventoryPersistenceMapper;
    }

    @Override
    public Inventory save(Inventory inventory) {
        InventoryEntity entity = inventoryPersistenceMapper.toEntity(inventory);
        InventoryEntity saved = inventoryRepository.saveAndFlush(entity);
        InventoryEntity loaded = inventoryRepository.findById(saved.getId())
                .orElseThrow();
        return inventoryPersistenceMapper.toDomain(loaded);
    }

    @Override
    public List<Inventory> findAll() {
        return inventoryRepository.findAll()
                .stream()
                .map(inventoryPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Inventory> findByAccountType(AccountType accountType) {
        return inventoryRepository.findByAccountType(accountType)
                .stream()
                .map(inventoryPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Inventory> findByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .stream()
                .map(inventoryPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByProductId(Long productId) {
        return inventoryRepository.existsByProductId(productId);
    }

    @Override
    public Optional<Inventory> findById(Long id) {
        return inventoryRepository.findById(id)
                .map(inventoryPersistenceMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        inventoryRepository.deleteById(id);
    }
}
