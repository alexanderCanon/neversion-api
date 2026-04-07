package com.neversion.api.inventory.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.neversion.api.inventory.domain.model.Inventory;
import com.neversion.api.inventory.domain.port.out.InventoryRepositoryPort;
import com.neversion.api.inventory.infrastructure.adapters.out.mapper.InventoryPersistenceMapper;
import com.neversion.api.shared.domain.model.enums.AccountType;

@Repository
public class JpaInventoryAdapter implements InventoryRepositoryPort {

    private final SpringDataInventoryRepository inventoryRepo;
    private final InventoryPersistenceMapper inventoryMapper;

    public JpaInventoryAdapter(SpringDataInventoryRepository inventoryRepo,
            InventoryPersistenceMapper inventoryMapper) {
        this.inventoryRepo = inventoryRepo;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public Inventory save(Inventory inventory) {
        InventoryEntity entity = inventoryMapper.toEntity(inventory);
        InventoryEntity saved = inventoryRepo.saveAndFlush(entity);
        InventoryEntity loaded = inventoryRepo.findById(saved.getId())
                .orElseThrow();
        return inventoryMapper.toDomain(loaded);
    }

    @Override
    public List<Inventory> findAll() {
        return inventoryRepo.findAll()
                .stream()
                .map(inventoryMapper::toDomain)
                .toList();
    }

    @Override
    public List<Inventory> findByAccountType(AccountType accountType) {
        return inventoryRepo.findByAccountType(accountType)
                .stream()
                .map(inventoryMapper::toDomain)
                .toList();
    }

    @Override
    public List<Inventory> findByProductId(UUID productId) {
        return inventoryRepo.findByProductId(productId)
                .stream()
                .map(inventoryMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByProductId(UUID productId) {
        return inventoryRepo.existsByProductId(productId);
    }

    @Override
    public Optional<Inventory> findById(Long id) {
        return inventoryRepo.findById(id)
                .map(inventoryMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        inventoryRepo.deleteById(id);
    }
}
