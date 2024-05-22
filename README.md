# EventOrganizerSystem
 Event Organizer System
 
### Features
 1. JWT authentication & authorization enabled
 2. Authentication is based on the user credentials stored in database.
 3. Authorization is about the roles and access privilege management.
 4. In this project, authorization is by default same to all, excluding ADMIN Role.
 5. No restrictions on the CRUD operations. But granular control over the CRUD operations is also simply possible.
 6. OpenAPI & Swagger3.0 UI documentation added
 7. System is configured for H5 database in testing env, and PostgreSQL for main dev env.
 8. Helped a lot in integration tests.
 9. Great scope of testing remains.

### Setting up the system
- Downloading required dependencies and zip file from spring initializer, Refer pom.xml file
    ```
    spring-boot-starter-data-jpa
    spring-boot-starter-security
    spring-boot-starter-web
    jjwt-api
    jjwt-impl
    jjwt-jackson
    springdoc-openapi-starter-webmvc-ui
    lombok
    postgresql
    spring-boot-starter-test
    spring-security-test
    h2
    ```
- Postgres configurations - dev environment
  ```
  spring.application.name=events
  spring.datasource.url=jdbc:postgresql://localhost:5432/satish
  spring.datasource.username=postgres
  spring.datasource.password=postgres
  spring.jpa.hibernate.ddl-auto=create-drop
  spring.jpa.show-sql=true
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
  spring.jpa.properties.hibernate.format_sql=true
  ```
- If you are somebody, who wants to isolate both dev and test env datasets, also add H5 dependency to maven pom.xml
- And add these H5 configuration - test environment
- H2 DataSource configuration for testing
    ```
    spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    spring.datasource.driver-class-name=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=
    spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
    
    # Enable H2 Console for testing
    spring.h2.console.enabled=true
    spring.profiles.active=test
    ```
- Other than that, I configured swagger, api-docs path to /public/.
- It helped me to isolate the public apis from private apis.
    ```
    springdoc.api-docs.path=/public/v3/api-docs
    springdoc.swagger-ui.path=/public/swagger-ui.html
    ```