package com.neversion.api.vendor.application.service;

import com.neversion.api.shared.application.service.VendorSecurityService;
import com.neversion.api.vendor.application.port.in.GetCurrentVendorUseCase;
import com.neversion.api.vendor.domain.model.Vendor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetCurrentVendorService implements GetCurrentVendorUseCase {

    private final VendorSecurityService vendorSecurityService;

    public GetCurrentVendorService(VendorSecurityService vendorSecurityService) {
        this.vendorSecurityService = vendorSecurityService;
    }

    @Override
    @Transactional(readOnly = true)
    public Vendor getByCallerExternalId(String callerExternalId) {
        return vendorSecurityService.resolveCallerVendor(callerExternalId);
    }
}
