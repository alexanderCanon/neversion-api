# Proyecto con Java Spring Boot - Ecommerce "Your Coffe Eyes"

## Description

Genera un proyecto Backend para gestión de una cafetería. El proyecto debe seguir la arquitectura de puertos y adaptadores para mantener bajo acoplamiento entre la lógica de negocio y los componentes externos. Por ahora vamos a crear solo un modelo, productos. Todo el código debe ir en inglés y llevar comentarios para documentación solo si es necesario para que otros desarrolladores puedan entender que hace cada bloque de código

## Contexto del negocio
El backend gestiona principalmente inventario de productos de café, con las siguientes características:
- Tipo de café: molido y en grano
- Categoría: premium, balanceado, estándar
- Tipo de tueste: medio, claro, oscuro

### Entidad principal: **Productos**
Es el producto principal que se vende en la cafetería, después podrán ser implementados otros productos tales como, pan, sandwiches, malteadas...

## Stack Tecnológico
**Programming Language:** Java 17
**Framework:** Spring
**Tool:** Spring Boot 3.5.10
**Build Tool:** Maven
**Database:** PostgreSQL
**ORM:** Spring Data JPA + Hibernate
**Validation:** Spring Boot Starter Validation (Jakarta)
**Security:** Spring Security 6
**Serialization:** Spring Boot Starter JSON (Jackson)
**Boilerplate:** Lombok

## Primer paso
Revisa el pom.xml para asegurarnos de tener las dependencias correctas, agreguemos una breve descripción del proyecto, coloquemos la versión inicial 1.0.0 y un nombre general (app) para luego construir la imagen con un archivo Dockerfile

## Arquitectura hexagonal (adaptadores y puertos)
Estructura de paquetes:
- Vertical slicing
- Paquete **exceptions** para manejar respuestas de error, manejador global de excepciones, excepciones personalizadas (resource not found exception)
- Dentro de cada paquete del modelo general:
 - domain/ (nucleo del negocio)
  - model/ (Record para la clase Java pura, enums (si aplica))
  - port/out/ (Interfaz RepositoryPort para puerto de salida)
  - service/ (lógica de dominio pura si es necesaria)
 - application/ (casos de uso)
  - port/in/ (Interfaces para casos de uso)
  - service/ (Implementación de los casos de uso y anotación @Service, una clase por cada caso de uso)
 - infrastructure/ (capa de infraestructura, los adapters)
  - config/ (configuración de Spring Security)
  - adapters/
   - in/rest/ (API REST), aqui va una clase Controller para cada método POST, GET con la anotacion controller
    - dto/ (DTO para Request y Response)
    - mapper/ (clase con mapper manual para request, response, domain, lleva anotación component generico)
   - out/ (para la persistencia)
    - Entity (la entidad JPA con la anotacion Entity)
    - RepositoryAdapter que extiende de JPA Repository
    - JPAAdapter que implementa el RepositoryPort
    - PersistenceMapper, clase para mapeo manual entre la Entity y la clase de dominio con anotacion Component generico

## Inyección de Dependencias
Se hace por medio de constructor, no con Autowired

## Casos de Uso
Uno para crear producto y otro para obtener por medio de id y obtener todos

## Testing
Uso de JUnit y Mockito para Test, crea 2 test para los casos de uso (service) que sean descriptivos, utiliza assertThat de assertJ

```
import static org.assertj.core.api.Assertions.*;
```

## Configuración de aplicación
Perfiles activos: dev

```yaml
spring:
  application:
    name: 
  datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/store}
    username: ${DB_USER:postgres}
    password: ${DB_PASSWORD:password}
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: create-delete
    show-sql: true
    database: postgresql
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    properties:
      hibernate:
        format_sql: true
server:
  port: ${PORT:8080}
```

### Enviroment files
Crea 3 archivos, uno para template, para dev y para prod

## API Endpoints
/api/v1/...

Maneja bien el versionado de los endpoint

## Esquema de base de datos
Genera código DDL para la tabla inicial de acuerdo a las instrucciones previamente descritas, ten en cuenta que es la plantilla inicial para comenzar con el desarrollo del proyecto y también código para inserción de datos iniciales en la carpeta resource (data.sql y schema.sql).

## Principios de arquitectura aplicados
1. Dominio independiente
2. Puertos como contratos
 - in: definen qué puede hacer la aplicación
 - out: define qué necesita la aplicación del exterior
3. Adaptadores intercambiables
 - REST Adapter: implementa la interfaz http
 - JPA: implementa el puerto del repositorio
4. Flujo de dependencias
 - Controller -> Port In -> Service -> Port Out <- JpaAdapter
5. Mapper separados
 - DTO <-> Domain, Entity <-> Domain. NO Uso de MapStruct

## Notas importantes
1. **Soft delete:** la entidad usa SQLDelete y SQLRestriction
2. **Validacion:** Jakarta Validation para DTORequest, agrega todas las validaciones necesarias que crees conveniente, utiliza siempre NotBlank para String, y NotNull para el resto de campos donde aplique
3. **Seguridad:** CSRF deshabilidado (por ahora)
4. **Mapeo de Enum:** Usa JdbcTypeCode(SqlTypes.NAMED_ENUM) para compatibilidad con PostgreSQL ENUM
5. **Records:** Usa Java Records para DTO de respuesta y modelo de dominio para inmutabiliad (si consideras que una clase podría ser Record entonces también puedes hacerlo para evitar boilerplate)
6. **Inicia git:** Inicializa git, agrega el .gitignore y coloca todo lo que NO debe versionarse, en especial las variables de entorno y el application-dev.yaml para evitar exponer archivos sensibles. El commit inicial será siempre. "initial commit"

## Docker configuration
Puede variar dependiendo de las configuraciones del pom.xml
### `Dockerfile`
```dockerfile
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY . .

RUN ./mvnw package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/store-0.0.1-SNAPSHOT.jar"]
```

### `docker-compose.yaml`
Para la orquestación, después podrá añadirse una base de datos local, redis o un frontend, por ahora lo dejemos solo con el backend
```yaml
services:
  app:
    image: spring
    container_name: 
    ports:
      - "${PORT}:8080"
    environment:
      DB_URL: ${DB_URL}
      DB_USER: ${DB_USER}
      DB_PASSWORD: ${DB_PASSWORD}
      APP_PORT: 8080
```
