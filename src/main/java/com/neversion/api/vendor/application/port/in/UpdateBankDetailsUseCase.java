package com.neversion.api.vendor.application.port.in;

/**
 * Inbound port for updating a vendor's bank and payment methods configuration.
 */
public interface UpdateBankDetailsUseCase {

    /**
     * Updates the bank_details JSON for the vendor owned by the caller.
     *
     * @param callerExternalId Supabase auth UID of the calling vendor
     * @param bankDetailsJson  raw JSON string representing the array of bank accounts
     * @return the persisted bank_details JSON
     */
    String updateBankDetails(String callerExternalId, String bankDetailsJson);
}
