package com.neversion.api.subscription.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.neversion.api.subscription.domain.model.Subscription;
import com.neversion.api.subscription.domain.model.enums.SubStatus;
import com.neversion.api.subscription.domain.port.out.SubscriptionRepositoryPort;
import com.neversion.api.subscription.infrastructure.adapters.in.rest.dto.SubscriptionDashboardDTO;

@Repository
public class JpaSubscriptionAdapter implements SubscriptionRepositoryPort {

    private final SpringDataSubscriptionRepository subscriptionRepo;
    private final SubscriptionPersistenceMapper subscriptionMapper;

    public JpaSubscriptionAdapter(SpringDataSubscriptionRepository subscriptionRepo,
            SubscriptionPersistenceMapper subscriptionMapper) {
        this.subscriptionRepo = subscriptionRepo;
        this.subscriptionMapper = subscriptionMapper;
    }

    @Override
    public Subscription save(Subscription subscription) {
        SubscriptionEntity entity = subscriptionMapper.toEntity(subscription);
        SubscriptionEntity saved = subscriptionRepo.saveAndFlush(entity);
        return subscriptionMapper.toDomain(saved);
    }

    @Override
    public Optional<Subscription> findById(UUID id) {
        return subscriptionRepo.findById(id).map(subscriptionMapper::toDomain);
    }

    @Override
    public List<Subscription> findByStatus(SubStatus status) {
        return subscriptionRepo.findByStatus(status).stream()
                .map(subscriptionMapper::toDomain)
                .toList();
    }

    @Override
    public List<Subscription> findByUserGuestId(UUID userGuestId) {
        return subscriptionRepo.findByUserGuestId(userGuestId).stream()
                .map(subscriptionMapper::toDomain)
                .toList();
    }

    @Override
    public List<Subscription> findByAccountId(UUID accountId) {
        return subscriptionRepo.findByAccountId(accountId).stream()
                .map(subscriptionMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsActiveByAccountId(UUID accountId) {
        return subscriptionRepo.existsByAccountIdAndStatus(accountId, SubStatus.ACTIVE);
    }

    @Override
    public List<SubscriptionDashboardDTO> findDashboard() {
        return subscriptionRepo.findDashboard();
    }
}
