package com.neversion.api.vendor.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neversion.api.exception.BusinessRuleException;
import com.neversion.api.shared.application.service.VendorSecurityService;
import com.neversion.api.vendor.application.port.in.UpdateBankDetailsUseCase;
import com.neversion.api.vendor.domain.model.Vendor;
import com.neversion.api.vendor.domain.port.out.VendorRepositoryPort;

/**
 * Application service for updating a vendor's bank accounts and payment methods configuration.
 * <p>
 * Validates the JSON structure before persisting:
 * <ul>
 *   <li>Must be parseable JSON.</li>
 *   <li>Must be a JSON array.</li>
 *   <li>Each account must have non-empty {@code bank}, {@code accountNumber}, {@code accountType}, and {@code holder}.</li>
 * </ul>
 */
@Service
public class UpdateBankDetailsService implements UpdateBankDetailsUseCase {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final VendorSecurityService vendorSecurityService;
    private final VendorRepositoryPort vendorRepositoryPort;

    public UpdateBankDetailsService(
            VendorSecurityService vendorSecurityService,
            VendorRepositoryPort vendorRepositoryPort) {
        this.vendorSecurityService = vendorSecurityService;
        this.vendorRepositoryPort = vendorRepositoryPort;
    }

    @Override
    @Transactional
    public String updateBankDetails(String callerExternalId, String bankDetailsJson) {
        validateStructure(bankDetailsJson);

        Vendor vendor = vendorSecurityService.resolveCallerVendor(callerExternalId);
        vendor.setBankDetails(bankDetailsJson);
        Vendor saved = vendorRepositoryPort.save(vendor);
        return saved.getBankDetails();
    }

    private void validateStructure(String json) {
        JsonNode root;
        try {
            root = OBJECT_MAPPER.readTree(json);
        } catch (Exception e) {
            throw new BusinessRuleException("bankDetails must be valid JSON: " + e.getMessage());
        }

        if (!root.isArray()) {
            throw new BusinessRuleException("bankDetails must be a JSON array of accounts");
        }

        for (JsonNode item : root) {
            if (!item.hasNonNull("bank") || item.get("bank").asText().trim().isEmpty()) {
                throw new BusinessRuleException("Each account in bankDetails must have a non-empty 'bank'");
            }
            if (!item.hasNonNull("accountNumber") || item.get("accountNumber").asText().trim().isEmpty()) {
                throw new BusinessRuleException("Each account in bankDetails must have a non-empty 'accountNumber'");
            }
            if (!item.hasNonNull("accountType") || item.get("accountType").asText().trim().isEmpty()) {
                throw new BusinessRuleException("Each account in bankDetails must have a non-empty 'accountType'");
            }
            if (!item.hasNonNull("holder") || item.get("holder").asText().trim().isEmpty()) {
                throw new BusinessRuleException("Each account in bankDetails must have a non-empty 'holder'");
            }
        }
    }
}
