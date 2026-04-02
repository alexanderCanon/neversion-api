package com.neversion.api.account.infrastructure.adapters.in.rest.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.neversion.api.account.domain.model.Account;
import com.neversion.api.account.infrastructure.adapters.in.rest.dto.AccountRequest;
import com.neversion.api.account.infrastructure.adapters.in.rest.dto.AccountResponse;
import com.neversion.api.accountslot.domain.model.AccountSlot;
import com.neversion.api.accountslot.domain.model.enums.SlotStatus;
import com.neversion.api.accountslot.domain.port.out.AccountSlotRepositoryPort;
import com.neversion.api.shared.domain.model.enums.AccountStatus;

@Component
public class AccountMapper {

    private final AccountSlotRepositoryPort slotRepository;

    public AccountMapper(AccountSlotRepositoryPort slotRepository) {
        this.slotRepository = slotRepository;
    }

    public Account toDomain(AccountRequest request) {
        return request != null ? Account.builder()
                .email(request.email())
                .pass(request.pass())
                .inventoryId(request.inventoryId())
                .seller(request.seller())
                .priceSeller(request.priceSeller())
                .status(AccountStatus.valueOf(request.status()))
                .expirationDate(request.expirationDate())
                .build() : null;
    }

    public AccountResponse toResponse(Account account) {
        if (account == null) return null;

        List<AccountSlot> slots = slotRepository.findByAccountId(account.getId());
        int maxSlots = slots.size();
        int occupiedSlots = (int) slots.stream()
                .filter(s -> s.getStatus() == SlotStatus.OCCUPIED)
                .count();
        int availableSlots = maxSlots - occupiedSlots;

        return AccountResponse.builder()
                .id(account.getId())
                .email(account.getEmail())
                .pass(account.getPass())
                .inventoryId(account.getInventoryId())
                .seller(account.getSeller())
                .priceSeller(account.getPriceSeller())
                .status(account.getStatus().name())
                .expirationDate(account.getExpirationDate())
                .maxSlots(maxSlots)
                .occupiedSlots(occupiedSlots)
                .availableSlots(availableSlots)
                .build();
    }
}
