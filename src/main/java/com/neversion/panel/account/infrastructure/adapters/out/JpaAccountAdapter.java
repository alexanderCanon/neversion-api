package com.neversion.panel.account.infrastructure.adapters.out;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.neversion.panel.account.domain.model.Account;
import com.neversion.panel.account.domain.port.out.AccountRepositoryPort;
import com.neversion.panel.account.infrastructure.adapters.out.mapper.AccountPersistenceMapper;
import com.neversion.panel.shared.domain.model.enums.AccountType;

@Repository
public class JpaAccountAdapter implements AccountRepositoryPort {
    private final AccountRepositoryAdapter accountRepositoryAdapter;
    private final AccountPersistenceMapper accountPersistenceMapper;

    public JpaAccountAdapter(AccountRepositoryAdapter accountRepositoryAdapter,
            AccountPersistenceMapper accountPersistenceMapper) {
        this.accountRepositoryAdapter = accountRepositoryAdapter;
        this.accountPersistenceMapper = accountPersistenceMapper;
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = accountPersistenceMapper.toEntity(account);
        AccountEntity saved = accountRepositoryAdapter.saveAndFlush(entity);
        AccountEntity loaded = accountRepositoryAdapter.findById(saved.getId())
                .orElseThrow();
        return accountPersistenceMapper.toDomain(loaded);
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return accountRepositoryAdapter.findById(id)
                .map(accountPersistenceMapper::toDomain);
    }

    @Override
    public List<Account> findBySeller(String seller) {
        return accountRepositoryAdapter.findBySeller(seller)
                .stream()
                .map(accountPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Account> findByAccountType(AccountType accountType) {
        return accountRepositoryAdapter.findByAccountType(accountType)
                .stream()
                .map(accountPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Account> findByExpirationDateBefore(LocalDate date) {
        return accountRepositoryAdapter.findByExpirationDateBefore(date)
                .stream()
                .map(accountPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Account> findByIsActive(Boolean isActive) {
        return accountRepositoryAdapter.findByIsActive(isActive)
                .stream()
                .map(accountPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Account> findAll() {
        return accountRepositoryAdapter.findAll()
                .stream()
                .map(accountPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public void deactivate(UUID id) {
        accountRepositoryAdapter.deactivate(id);
    }
}
