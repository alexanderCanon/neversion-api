package com.neversion.panel.shared.domain.model.enums;

import com.neversion.panel.infrastructure.EnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AccountTypeConverter extends EnumConverter<AccountType> {

    public AccountTypeConverter() {
        super(AccountType.class);
    }
}
