package com.neversion.api.dashboard.application.port.out;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.neversion.api.dashboard.application.result.AccountSlotResult;
import com.neversion.api.dashboard.application.result.ProductSummaryResult;

/**
 * Outbound port for dashboard read-only queries.
 * Implemented by the persistence adapter using native SQL.
 */
public interface DashboardQueryPort {

    /**
     * Endpoint 1: products with account count filtered by category.
     */
    List<ProductSummaryResult> findProductsByCategory(String category);

    /**
     * Endpoint 2: raw account data for a product.
     * Returns a list of maps with keys: accountId, email, password, cutOffDate,
     * accountType, accountStatus, maxSlots, occupiedSlots.
     */
    List<Map<String, Object>> findAccountsByProductId(UUID productId);

    /**
     * Endpoint 3: slots for an account with subscription and customer data.
     */
    List<AccountSlotResult> findSlotsByAccountId(UUID accountId);
}
