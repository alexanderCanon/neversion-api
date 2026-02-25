**Modelos de Dominio (Reglas de Negocio):**
1. **Product**: Entidad principal.
   - Campos: `id` (Long/Integer), `name` (String), `description` (String), `imageUrl` (String), `category` (Enum: CategoryType).
2. **Plan**: Variante o suscripción asociada a un producto (Relación Muchos a Uno).
   - Campos: `id` (Long), `productId` (Long - referencia al padre), `priceAmount` (BigDecimal), `duration` (String, ej. "30 dias", "60 dias", "90 dias"), `accountType` (Enum: INDIVIDUAL, FAMILIAR).
3. **Regla de Descuento**: Si un Plan se crea con una duración de "90 dias" o más, el sistema debe calcular un 3% de descuento sobre el precio base mensual proporcional. Este precio final descontado es el que se debe guardar en `priceAmount` en la base de datos.

**Flujos requeridos (Casos de Uso separados):**
- Flujo A: Crear un Producto (independiente).
- Flujo B: Agregar un Plan a un Producto existente (usando el ID del producto en la URL).

**Estructura de la Arquitectura Hexagonal requerida:**
Por favor, genera el código organizado en los siguientes paquetes/capas, separando estrictamente el dominio de los frameworks:

1. **Domain Layer**: 
   - Clases de dominio `Product` y `Plan` (POJOs limpios, sin anotaciones de Spring/JPA).
   - Enums: `CategoryType`, `AccountType`.
   - Lógica del cálculo del descuento dentro del dominio.
2. **Application Layer (Ports & Use Cases)**:
   - Interfaces (Puertos de entrada/salida): `ProductRepositoryPort`, `PlanRepositoryPort`.
   - Clases de Casos de Uso: `CreateProductUseCase`, `CreatePlanUseCase`.
3. **Infrastructure Layer (Adapters)**:
   - **Inbound (Web)**: REST Controllers (`ProductController`), DTOs de Request y Response usando `records`, y Mappers (puedes usar MapStruct o mappers manuales estáticos). 
     - Endpoints: `POST /api/products` y `POST /api/products/{productId}/plans`.
   - **Outbound (Persistence)**: Entidades JPA (`ProductEntity`, `PlanEntity` con `@ManyToOne` y `@OneToMany`), interfaces de Spring Data JPA, y las clases Adapter que implementan los Puertos del dominio.

**Instrucciones finales:**
Genera el código paso a paso, asegurando el uso de `records` para los DTOs, validaciones de Jakarta (`@NotBlank`, `@NotNull`), y un manejo limpio de excepciones. No uses relaciones bidireccionales en los DTOs para evitar recursividad infinita (HttpMessageNotWritableException). Empieza por el Dominio, luego Aplicación y finalmente Infraestructura.