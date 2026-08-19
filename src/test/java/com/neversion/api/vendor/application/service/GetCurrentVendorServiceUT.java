package com.neversion.api.vendor.application.service;

import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.shared.application.service.VendorSecurityService;
import com.neversion.api.vendor.domain.model.Vendor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetCurrentVendorService unit tests")
class GetCurrentVendorServiceUT {

    @Mock
    private VendorSecurityService vendorSecurityService;

    private GetCurrentVendorService service;

    private static final String EXTERNAL_ID = "auth|vendor-123";

    @BeforeEach
    void setUp() {
        service = new GetCurrentVendorService(vendorSecurityService);
    }

    @Test
    @DisplayName("getByCallerExternalId - should return vendor when caller exists")
    void getByCallerExternalId_shouldReturnVendor() {
        Vendor vendor = Vendor.builder()
                .id(1L)
                .uuid(UUID.randomUUID())
                .userId(10L)
                .storeName("Tienda Test")
                .discountCfg("{\"enabled\":true}")
                .rewardsCfg("{\"enabled\":true,\"earn_pct\":2.0}")
                .build();

        when(vendorSecurityService.resolveCallerVendor(EXTERNAL_ID)).thenReturn(vendor);

        Vendor result = service.getByCallerExternalId(EXTERNAL_ID);

        assertThat(result).isNotNull();
        assertThat(result.getStoreName()).isEqualTo("Tienda Test");
        assertThat(result.getUuid()).isEqualTo(vendor.getUuid());
    }

    @Test
    @DisplayName("getByCallerExternalId - should throw when vendor not found")
    void getByCallerExternalId_shouldThrow_whenNotFound() {
        when(vendorSecurityService.resolveCallerVendor(EXTERNAL_ID))
                .thenThrow(new ResourceNotFoundException("Vendor not found"));

        assertThatThrownBy(() -> service.getByCallerExternalId(EXTERNAL_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
