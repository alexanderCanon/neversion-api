package com.neversion.api.vendor.application.service;

import com.neversion.api.exception.BusinessRuleException;
import com.neversion.api.shared.application.service.VendorSecurityService;
import com.neversion.api.vendor.domain.model.Vendor;
import com.neversion.api.vendor.domain.port.out.VendorRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateBankDetailsService unit tests")
class UpdateBankDetailsServiceUT {

    @Mock
    private VendorSecurityService vendorSecurityService;

    @Mock
    private VendorRepositoryPort vendorRepositoryPort;

    private UpdateBankDetailsService service;

    private static final String EXTERNAL_ID = "auth|vendor-123";
    private static final String VALID_JSON = "[{\"bank\":\"Banrural\",\"accountNumber\":\"4426313592\",\"accountType\":\"Ahorro en Quetzales\",\"holder\":\"Alexander Canon\"}]";

    @BeforeEach
    void setUp() {
        service = new UpdateBankDetailsService(vendorSecurityService, vendorRepositoryPort);
    }

    @Test
    @DisplayName("updateBankDetails - should update and persist valid bank accounts JSON")
    void updateBankDetails_shouldPersistValidJson() {
        Vendor vendor = Vendor.builder()
                .id(1L)
                .uuid(UUID.randomUUID())
                .userId(10L)
                .storeName("Tienda Test")
                .bankDetails("[]")
                .build();

        when(vendorSecurityService.resolveCallerVendor(EXTERNAL_ID)).thenReturn(vendor);
        when(vendorRepositoryPort.save(any(Vendor.class))).thenAnswer(inv -> inv.getArgument(0));

        String result = service.updateBankDetails(EXTERNAL_ID, VALID_JSON);

        assertThat(result).isEqualTo(VALID_JSON);

        ArgumentCaptor<Vendor> captor = ArgumentCaptor.forClass(Vendor.class);
        verify(vendorRepositoryPort).save(captor.capture());
        assertThat(captor.getValue().getBankDetails()).isEqualTo(VALID_JSON);
    }

    @Test
    @DisplayName("updateBankDetails - should throw when JSON is invalid")
    void updateBankDetails_shouldThrow_whenJsonInvalid() {
        assertThatThrownBy(() -> service.updateBankDetails(EXTERNAL_ID, "not-a-json"))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("bankDetails must be valid JSON");
    }

    @Test
    @DisplayName("updateBankDetails - should throw when JSON is not an array")
    void updateBankDetails_shouldThrow_whenNotArray() {
        assertThatThrownBy(() -> service.updateBankDetails(EXTERNAL_ID, "{\"bank\":\"Banrural\"}"))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("bankDetails must be a JSON array of accounts");
    }

    @Test
    @DisplayName("updateBankDetails - should throw when an account is missing required fields")
    void updateBankDetails_shouldThrow_whenMissingFields() {
        String invalidAccount = "[{\"bank\":\"Banrural\",\"accountNumber\":\"\"}]";
        assertThatThrownBy(() -> service.updateBankDetails(EXTERNAL_ID, invalidAccount))
                .isInstanceOf(BusinessRuleException.class);
    }
}
