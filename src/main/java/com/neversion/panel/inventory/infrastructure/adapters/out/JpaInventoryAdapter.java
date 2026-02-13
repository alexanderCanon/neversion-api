package com.neversion.panel.inventory.infrastructure.adapters.out;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.domain.model.enums.AccountType;
import com.neversion.panel.inventory.domain.port.out.InventoryRepositoryPort;
import com.neversion.panel.inventory.infrastructure.adapters.out.mapper.InventoryPersistenceMapper;

@Repository
public class JpaInventoryAdapter implements InventoryRepositoryPort {
    private final InventoryRepositoryAdapter inventoryRepositoryAdapter;
    private final InventoryPersistenceMapper inventoryPersistenceMapper;

    public JpaInventoryAdapter(InventoryRepositoryAdapter inventoryRepositoryAdapter,
        InventoryPersistenceMapper inventoryPersistenceMapper) {
        this.inventoryRepositoryAdapter = inventoryRepositoryAdapter;
        this.inventoryPersistenceMapper = inventoryPersistenceMapper;
    }

    @Override
    public Inventory save(Inventory inventory) {
        InventoryEntity entity = inventoryPersistenceMapper.toEntity(inventory);
        InventoryEntity saved = inventoryRepositoryAdapter.saveAndFlush(entity);
        InventoryEntity loaded = inventoryRepositoryAdapter.findById(saved.getId())
            .orElseThrow();
        return inventoryPersistenceMapper.toDomain(loaded);
    }

    @Override
    public Optional<Inventory> findById(Long id) {
        return inventoryRepositoryAdapter.findById(id)
            .map(inventoryPersistenceMapper::toDomain);
    }

    @Override
    public List<Inventory> findBySeller(String seller) {
        return inventoryRepositoryAdapter.findBySeller(seller)
            .stream()
            .map(inventoryPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public List<Inventory> findByAccountType(AccountType accountType) {
        return inventoryRepositoryAdapter.findByAccountType(accountType)
            .stream()
            .map(inventoryPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public List<Inventory> findByExpirationDateBefore(LocalDate date) {
        return inventoryRepositoryAdapter.findByExpirationDateBefore(date)
            .stream()
            .map(inventoryPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public List<Inventory> findByIsActive(Boolean isActive) {
        return inventoryRepositoryAdapter.findByIsActive(isActive)
            .stream()
            .map(inventoryPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public List<Inventory> findAll() {
        return inventoryRepositoryAdapter.findAll()
            .stream()
            .map(inventoryPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public void deactivate(Long id) {
        inventoryRepositoryAdapter.deactivate(id);
    }
}
