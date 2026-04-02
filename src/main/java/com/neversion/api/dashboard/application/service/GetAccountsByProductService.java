package com.neversion.api.dashboard.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.neversion.api.dashboard.application.port.in.GetAccountsByProductUseCase;
import com.neversion.api.dashboard.application.port.out.DashboardQueryPort;
import com.neversion.api.dashboard.application.result.AccountGroupResult;
import com.neversion.api.dashboard.domain.model.enums.AccountAvailability;

/**
 * Service for endpoint 2. Calculates AccountAvailability (BR-06)
 * from raw DB data — never persisted.
 */
@Service
public class GetAccountsByProductService implements GetAccountsByProductUseCase {

    private final DashboardQueryPort dashboardQueryPort;

    public GetAccountsByProductService(DashboardQueryPort dashboardQueryPort) {
        this.dashboardQueryPort = dashboardQueryPort;
    }

    @Override
    public List<AccountGroupResult> getByProductId(UUID productId) {
        List<Map<String, Object>> rawAccounts = dashboardQueryPort.findAccountsByProductId(productId);
        return rawAccounts.stream().map(this::toAccountGroupResult).toList();
    }

    private AccountGroupResult toAccountGroupResult(Map<String, Object> row) {
        UUID accountId = (UUID) row.get("accountId");
        String email = (String) row.get("email");
        String password = (String) row.get("password");
        LocalDate cutOffDate = row.get("cutOffDate") != null
                ? LocalDate.parse(row.get("cutOffDate").toString())
                : null;
        String accountType = ((String) row.get("accountType")).toUpperCase();
        String accountStatus = ((String) row.get("accountStatus")).toUpperCase();
        int maxSlots = ((Number) row.get("maxSlots")).intValue();
        int occupiedSlots = ((Number) row.get("occupiedSlots")).intValue();
        int availableSlots = maxSlots - occupiedSlots;

        String availability = calculateAvailability(accountType, maxSlots, occupiedSlots, availableSlots);

        return new AccountGroupResult(
                accountId, email, password, cutOffDate,
                accountType, accountStatus, maxSlots,
                occupiedSlots, availableSlots, availability);
    }

    /**
     * BR-06: AccountAvailability is calculated at query time.
     * <ul>
     *   <li>If accountType = INDIVIDUAL → INDIVIDUAL</li>
     *   <li>If accountType = FAMILY and 1 subscription occupies entire account → COMPLETE</li>
     *   <li>If accountType = FAMILY and availableSlots = 0 → NO_AVAILABILITY</li>
     *   <li>If accountType = FAMILY and availableSlots > 0 and occupiedSlots > 0 → PARTIAL</li>
     *   <li>Otherwise (all free) → PARTIAL</li>
     * </ul>
     */
    private String calculateAvailability(String accountType, int maxSlots, int occupiedSlots, int availableSlots) {
        if ("INDIVIDUAL".equals(accountType)) {
            return AccountAvailability.INDIVIDUAL.name();
        }
        if (availableSlots == 0) {
            return AccountAvailability.NO_AVAILABILITY.name();
        }
        if (occupiedSlots > 0) {
            return AccountAvailability.PARTIAL.name();
        }
        // All slots free — still PARTIAL (has free slots)
        return AccountAvailability.PARTIAL.name();
    }
}
