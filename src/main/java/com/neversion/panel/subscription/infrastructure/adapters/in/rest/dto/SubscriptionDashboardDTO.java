package com.neversion.panel.subscription.infrastructure.adapters.in.rest.dto;

import java.time.LocalDate;

/**
 * Projection interface for the subscription dashboard native query (CU-A07).
 * Spring Data JPA will auto-map the query result columns to these getter
 * methods.
 */
public interface SubscriptionDashboardDTO {
    String getEmail();

    String getPassword();

    String getProfileName();

    String getPin();

    String getServiceName();

    LocalDate getPurchaseDate();

    LocalDate getRenewalDate();

    String getStatus();
}
