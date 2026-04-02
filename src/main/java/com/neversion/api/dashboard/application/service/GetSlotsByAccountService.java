package com.neversion.api.dashboard.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.neversion.api.dashboard.application.port.in.GetSlotsByAccountUseCase;
import com.neversion.api.dashboard.application.port.out.DashboardQueryPort;
import com.neversion.api.dashboard.application.result.AccountSlotResult;

@Service
public class GetSlotsByAccountService implements GetSlotsByAccountUseCase {

    private final DashboardQueryPort dashboardQueryPort;

    public GetSlotsByAccountService(DashboardQueryPort dashboardQueryPort) {
        this.dashboardQueryPort = dashboardQueryPort;
    }

    @Override
    public List<AccountSlotResult> getByAccountId(UUID accountId) {
        return dashboardQueryPort.findSlotsByAccountId(accountId);
    }
}
