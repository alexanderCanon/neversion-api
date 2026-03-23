package com.neversion.api.subscription.infrastructure.adapters.out;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.neversion.api.subscription.domain.model.enums.SubStatus;
import com.neversion.api.subscription.infrastructure.adapters.in.rest.dto.SubscriptionDashboardDTO;

public interface SpringDataSubscriptionRepository extends JpaRepository<SubscriptionEntity, UUID> {

    /**
     * Derived query for anti-overbooking check (BR-06).
     */
    boolean existsByAccountIdAndStatus(UUID accountId, SubStatus status);

    List<SubscriptionEntity> findByStatus(SubStatus status);

    List<SubscriptionEntity> findByUserGuestId(UUID userGuestId);

    List<SubscriptionEntity> findByAccountId(UUID accountId);

    /**
     * Dashboard master view (CU-A07).
     */
    @Query(value = """
            SELECT
                a.email         AS email,
                a.pass          AS password,
                sl.profile_name AS profileName,
                sl.pin          AS pin,
                p.name          AS serviceName,
                s.purchase_date AS purchaseDate,
                s.renewal_date  AS renewalDate,
                s.status::text  AS status
            FROM subscriptions s
            JOIN accounts a ON s.account_id = a.id
            JOIN inventory i ON a.inventory_id = i.id
            JOIN products p ON i.product_id = p.id
            LEFT JOIN account_slots sl ON s.account_slot_id = sl.id
            ORDER BY s.purchase_date DESC
            """, nativeQuery = true)
    List<SubscriptionDashboardDTO> findDashboard();
}
