# PetCareBook — Pet Vaccination Reminder System

A Java Spring Boot + MySQL web application based strictly on the supplied project specification.

## Implemented requirements

- Register a pet with name, species, breed and date of birth.
- Associate each pet with an owner.
- Log vaccination events with vaccine name/type and date given.
- Calculate the next due date automatically using the vaccine type's fixed interval.
- List vaccinations due in the next 7 days.
- View a pet's complete vaccination history.
- Enforce that vaccination dates cannot be in the future in the service layer.
- Bean Validation and a global exception handler return clear error messages.
- REST API plus a simple browser-based web interface.
- Swagger/OpenAPI documentation.

## Technology

- Java 17
- Spring Boot 3.4.5
- Spring Web
- Spring Data JPA / Hibernate
- MySQL
- Thymeleaf
- Bean Validation
- Springdoc OpenAPI

## Database

The application uses a MySQL database named `petcarebook`. The JDBC URL includes `createDatabaseIfNotExist=true`, so the database is created automatically if the MySQL user has permission.

Default configuration in `application.properties`:
- host: localhost
- port: 3306
- database: petcarebook
- username: root
- password: root

Change the username/password before running if your local MySQL credentials are different.

## Run

1. Install Java 17+ and Maven.
2. Make sure MySQL is running.
3. Open a terminal in this project folder.
4. Run:

```bash
mvn clean spring-boot:run
```

Or build:

```bash
mvn clean package
java -jar target/petcarebook-1.0.0.jar
```

Open:
- Web application: http://localhost:8080/
- Swagger UI: http://localhost:8080/swagger-ui.html

## REST endpoints

- `GET /api/dashboard`
- `GET/POST /api/owners`
- `GET /api/owners/{id}`
- `GET/POST /api/pets?ownerId={ownerId}`
- `GET /api/pets/{id}`
- `GET/POST /api/vaccine-types`
- `GET /api/vaccine-types/{id}`
- `POST /api/vaccinations`
- `GET /api/vaccinations/{id}`
- `GET /api/vaccinations/pet/{petId}`
- `GET /api/vaccinations/due-next-7-days`

## Seeded vaccine types

On first startup, the application adds these types if they do not already exist:
- Rabies — 365 days
- DHPP — 365 days
- Bordetella — 365 days
- Feline FVRCP — 365 days
- Deworming — 180 days

The intervals are stored in the database and are used by the business logic; they are not hard-coded into vaccination records.

## Business rule example

If a vaccine has a 365-day interval and the vaccination was given on 2026-01-10, the service calculates:

`nextDueDate = 2026-01-10 + 365 days`

A request with a future vaccination date is rejected with HTTP 400 and a clear error message.

## Main relationships

Owner 1 --- * Pet
Pet 1 --- * VaccinationRecord
VaccineType 1 --- * VaccinationRecord
