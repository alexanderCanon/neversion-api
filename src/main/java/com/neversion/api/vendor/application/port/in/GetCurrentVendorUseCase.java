package com.neversion.api.vendor.application.port.in;

import com.neversion.api.vendor.domain.model.Vendor;

public interface GetCurrentVendorUseCase {

    Vendor getByCallerExternalId(String callerExternalId);
}
