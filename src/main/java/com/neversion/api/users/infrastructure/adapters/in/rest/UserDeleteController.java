// package com.neversion.api.users.infrastructure.adapters.in.rest;

// import java.util.UUID;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import
// com.neversion.api.users.application.port.in.DeactivateUserUseCase;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.tags.Tag;

// @RestController
// @RequestMapping("/api/v1/users")
// @Tag(name = "Users")
// public class UserDeleteController {

// private final DeactivateUserUseCase deactivateUserUseCase;

// public UserDeleteController(DeactivateUserUseCase
// deactivateUserUseCase) {
// this.deactivateUserUseCase = deactivateUserUseCase;
// }

// @DeleteMapping("/{id}")
// @Operation(summary = "Deactivate a user", description = "Soft-delete a
// user by its UUID")
// @ApiResponse(responseCode = "204", description = "User deactivated
// successfully")
// @ApiResponse(responseCode = "404", description = "User not found")
// public ResponseEntity<Void> deactivateUser(@PathVariable UUID id) {
// deactivateUserUseCase.deactivate(id);
// return ResponseEntity.noContent().build();
// }
// }
