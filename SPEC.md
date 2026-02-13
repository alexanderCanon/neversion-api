# Spring Boot Project Ecommerce "Neversion"

## Description
This project it will be used by admin panel

El proyecto debe seguir la arquitectura de puertos y adaptadores para mantener bajo acoplamiento entre la lógica de negocio y los componentes externos. Por defecto te encontraras con todas los modelos ya definidos, con su estructura de carpetas y sobre todo el paquete /domain/model ya tiene su record Java para el modelo puro, lo que vive en el centro. Todo el código debe ir en inglés y llevar comentarios para documentación solo si es necesario para que otros desarrolladores puedan entender que hace cada bloque de código

## Contexto del negocio
El backend gestiona principalmente servicios (productos digitales) con categorías

### Entidad principal: **Services**
Es el producto principal que se vende en el ecommerce

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
La estructura de paquetes la encuentras dentro del archivo STRUCTURE.md en la raiz del proyecto.

## Inyección de Dependencias
Se hace por medio de constructor, no con Autowired

## Casos de Uso
CREATE: Primera implementación, caso de uso, create, save...
READ: Segunda implementación, caso de uso, get, read...
UPDATE: Esto no se va a implementar por ahora
DELETE: Tercerca implementación, caso de uso, delete (soft delete)...

## Testing
Uso de JUnit y Mockito para Test. Utiliza assertThat de assertJ

```
import static org.assertj.core.api.Assertions.*;
```

## Configuración de aplicación
Por defecto el perfil activo es dev. No leas el archivo application-dev.yaml. De eso me encargo yo


## API Endpoints
/api/v1/... Maneja bien el versionado de los endpoint

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
5. **Records:** Usa Java Records para DTO de respuesta (si consideras que una clase podría ser Record entonces también puedes hacerlo para evitar boilerplate)

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
Para la orquestación, después podrá añadirse una base de datos local, redis o un frontend, por ahora lo dejemos solo con el backend, pero para tenerlo como referencia.
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
