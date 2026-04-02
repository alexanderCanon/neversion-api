package com.neversion.api.dashboard.application.port.in;

import java.util.List;
import java.util.UUID;

import com.neversion.api.dashboard.application.result.AccountSlotResult;

/**
 * Inbound port: get slots for an account with subscription data.
 */
public interface GetSlotsByAccountUseCase {
    List<AccountSlotResult> getByAccountId(UUID accountId);
}
