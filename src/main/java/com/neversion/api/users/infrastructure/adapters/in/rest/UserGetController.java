// package com.neversion.api.users.infrastructure.adapters.in.rest;

// import java.util.List;
// import java.util.UUID;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.neversion.api.users.application.port.in.GetUserUseCase;
// import com.neversion.api.users.domain.model.User;
// import
// com.neversion.api.users.infrastructure.adapters.in.rest.dto.UserResponse;
// import
// com.neversion.api.users.infrastructure.adapters.in.rest.mapper.UserMapper;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.tags.Tag;

// @RestController
// @RequestMapping("/api/v1/users")
// @Tag(name = "Users", description = "User user management")
// public class UserGetController {

// private final GetUserUseCase getUserUseCase;
// private final UserMapper userMapper;

// public UserGetController(GetUserUseCase getUserUseCase,
// UserMapper userMapper) {
// this.getUserUseCase = getUserUseCase;
// this.userMapper = userMapper;
// }

// @GetMapping("/{id}")
// @Operation(summary = "Get user by ID", description = "Retrieve a single
// user by its UUID")
// @ApiResponse(responseCode = "200", description = "User found")
// @ApiResponse(responseCode = "404", description = "User not found")
// public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id)
// {
// User user = getUserUseCase.getById(id);
// UserResponse response = userMapper.toResponse(user);
// return ResponseEntity.ok(response);
// }

// @GetMapping
// @Operation(summary = "Get users", description = "Retrieve users
// filtered by name, email, or active status")
// @ApiResponse(responseCode = "200", description = "Users retrieved
// successfully")
// public ResponseEntity<?> getUsers(
// @Parameter(description = "Filter by user name") @RequestParam(required =
// false) String name,
// @Parameter(description = "Filter by exact email address")
// @RequestParam(required = false) String email,
// @Parameter(description = "Filter by active status") @RequestParam(required =
// false) Boolean isActive) {

// if (email != null && !email.isBlank()) {
// User user = getUserUseCase.getByEmail(email);
// UserResponse response = userMapper.toResponse(user);
// return ResponseEntity.ok(response);
// }

// if (name != null && !name.isBlank()) {
// List<UserResponse> response = getUserUseCase.getByName(name).stream()
// .map(userMapper::toResponse)
// .toList();
// return ResponseEntity.ok(response);
// }

// if (isActive != null) {
// List<UserResponse> response =
// getUserUseCase.getByIsActive(isActive).stream()
// .map(userMapper::toResponse)
// .toList();
// return ResponseEntity.ok(response);
// }

// List<UserResponse> response = getUserUseCase.getAll().stream()
// .map(userMapper::toResponse)
// .toList();
// return ResponseEntity.ok(response);
// }
// }
