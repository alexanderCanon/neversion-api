package com.neversion.api.account.infrastructure.adapters.out;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.neversion.api.account.domain.model.Account;
import com.neversion.api.account.domain.port.out.AccountRepositoryPort;
import com.neversion.api.account.infrastructure.adapters.out.mapper.AccountPersistenceMapper;
import com.neversion.api.shared.domain.model.enums.AccountType;

@Repository
public class JpaAccountAdapter implements AccountRepositoryPort {

    private final SpringDataAccountAdapter accountRepo;
    private final AccountPersistenceMapper accountMapper;

    public JpaAccountAdapter(SpringDataAccountAdapter accountRepo,
            AccountPersistenceMapper accountMapper) {
        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = accountMapper.toEntity(account);
        AccountEntity saved = accountRepo.save(entity);
        AccountEntity loaded = accountRepo.findById(saved.getId())
                .orElseThrow();
        return accountMapper.toDomain(loaded);
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return accountRepo.findById(id)
                .map(accountMapper::toDomain);
    }

    @Override
    public List<Account> findBySeller(String seller) {
        return accountRepo.findBySeller(seller)
                .stream()
                .map(accountMapper::toDomain)
                .toList();
    }

    @Override
    public List<Account> findByAccountType(AccountType accountType) {
        return accountRepo.findByAccountType(accountType)
                .stream()
                .map(accountMapper::toDomain)
                .toList();
    }

    @Override
    public List<Account> findByExpirationDateBefore(LocalDate date) {
        return accountRepo.findByExpirationDateBefore(date)
                .stream()
                .map(accountMapper::toDomain)
                .toList();
    }

    @Override
    public List<Account> findByIsActive(Boolean isActive) {
        return accountRepo.findByIsActive(isActive)
                .stream()
                .map(accountMapper::toDomain)
                .toList();
    }

    @Override
    public List<Account> findAll() {
        return accountRepo.findAll()
                .stream()
                .map(accountMapper::toDomain)
                .toList();
    }

    @Override
    public void deactivate(UUID id) {
        accountRepo.deactivate(id);
    }
}
