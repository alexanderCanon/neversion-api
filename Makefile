# =============================================================================
# NEVERSION - API (Spring Boot)
# Usage: make <target> [ARGS]
# =============================================================================
 
SHELL := /bin/bash
.SHELLFLAGS := -eu -o pipefail -c
 
.DEFAULT_GOAL := help
 
# =============================================================================
# HELP
# =============================================================================
 
.PHONY: help
help: ## Show all available commands
	@echo ""
	@echo "  NEVERSION API - Command Hub"
	@echo ""
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) \
		| awk 'BEGIN {FS = ":.*?## "}; {printf "  \033[36m%-30s\033[0m %s\n", $$1, $$2}'
	@echo ""
 
# =============================================================================
# MAVEN
# =============================================================================
 
.PHONY: compile build test package clean
 
compile: ## Compile source code only
	./mvnw compile
 
build: ## Full build (compile + test + package)
	./mvnw clean install
 
test: ## Run all tests
	./mvnw test
 
package: ## Package without running tests
	./mvnw package -DskipTests
 
clean: ## Clean build artifacts
	./mvnw clean
 
# =============================================================================
# DOCKER
# =============================================================================
 
.PHONY: up down logs build-image
 
up: ## Start production stack (detached). Usage: make up
	docker compose -f compose.prod.yml up -d --build
 
down: ## Stop and remove containers
	docker compose -f compose.prod.yml down
 
logs: ## Follow container logs. Usage: make logs | make logs SERVICE=api
	@if [ -z "$(SERVICE)" ]; then \
		docker compose -f compose.prod.yml logs -f; \
	else \
		docker compose -f compose.prod.yml logs -f $(SERVICE); \
	fi
 
build-image: ## Build Docker image only (no compose)
	docker compose -f compose.prod.yml build
 
# =============================================================================
# DEV
# =============================================================================
 
.PHONY: dev
 
dev: ## Run locally with Spring Boot dev tools (hot reload)
	./mvnw spring-boot:run