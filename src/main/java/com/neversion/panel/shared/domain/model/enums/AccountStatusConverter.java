package com.neversion.panel.shared.domain.model.enums;

import com.neversion.panel.infrastructure.EnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AccountStatusConverter extends EnumConverter<AccountStatus> {

    public AccountStatusConverter() {
        super(AccountStatus.class);
    }
}
