package com.neversion.api.dashboard.infrastructure.adapters.in.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.api.dashboard.application.port.in.GetAccountsByProductUseCase;
import com.neversion.api.dashboard.application.port.in.GetProductsSummaryUseCase;
import com.neversion.api.dashboard.application.port.in.GetSlotsByAccountUseCase;
import com.neversion.api.dashboard.application.result.AccountGroupResult;
import com.neversion.api.dashboard.application.result.AccountSlotResult;
import com.neversion.api.dashboard.application.result.ProductSummaryResult;
import com.neversion.api.product.domain.model.enums.CategoryType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard", description = "Master dashboard for admin monitoring")
public class DashboardController {

    private final GetProductsSummaryUseCase getProductsSummaryUseCase;
    private final GetAccountsByProductUseCase getAccountsByProductUseCase;
    private final GetSlotsByAccountUseCase getSlotsByAccountUseCase;

    public DashboardController(GetProductsSummaryUseCase getProductsSummaryUseCase,
            GetAccountsByProductUseCase getAccountsByProductUseCase,
            GetSlotsByAccountUseCase getSlotsByAccountUseCase) {
        this.getProductsSummaryUseCase = getProductsSummaryUseCase;
        this.getAccountsByProductUseCase = getAccountsByProductUseCase;
        this.getSlotsByAccountUseCase = getSlotsByAccountUseCase;
    }

    @GetMapping
    @Operation(summary = "Get streaming products summary",
            description = "Returns products filtered by category with account count per product")
    @ApiResponse(responseCode = "200", description = "Product summaries retrieved")
    public ResponseEntity<List<ProductSummaryResult>> getProductsSummary(
            @Parameter(description = "Product category (STREAMING, SOFTWARE, etc.)")
            @RequestParam CategoryType category) {
        List<ProductSummaryResult> result = getProductsSummaryUseCase.getByCategory(category);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/products/{productId}/accounts")
    @Operation(summary = "Get accounts for a product",
            description = "Returns all accounts for a product with slot availability")
    @ApiResponse(responseCode = "200", description = "Account groups retrieved")
    public ResponseEntity<List<AccountGroupResult>> getAccountsByProduct(
            @Parameter(description = "Product UUID") @PathVariable UUID productId) {
        List<AccountGroupResult> result = getAccountsByProductUseCase.getByProductId(productId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/accounts/{accountId}/slots")
    @Operation(summary = "Get slots for an account",
            description = "Returns slots with subscription and customer data for an account")
    @ApiResponse(responseCode = "200", description = "Account slots retrieved")
    public ResponseEntity<List<AccountSlotResult>> getSlotsByAccount(
            @Parameter(description = "Account UUID") @PathVariable UUID accountId) {
        List<AccountSlotResult> result = getSlotsByAccountUseCase.getByAccountId(accountId);
        return ResponseEntity.ok(result);
    }
}
