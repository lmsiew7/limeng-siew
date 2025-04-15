# Petstore API Test Automation – STORE Domain

This project is an automated API test suite for the **Petstore** `/store` domain. It covers CRUD operations and inventory validation.

## Prerequisites
- Java JDK 11 or higher
- Maven 3.6 or higher
- Allure CLI 
- Appium + Android Emulator
- Petstore API is up and running

---

## Approach
- Modular Test Design: separated tests, API calls (StoreApi), and test data (OrderData).
- Used json-schema-validator to enforce response structure, reduced test fragility and ensured API contract compliance.
- Used Config.BASE_URL with system properties for easy switching between environments.

## Tech Stack
- **Java**: Strong support for type safety and tooling
- **TestNG**: Powerful testing framework with annotations and parallel execution.
- **Maven**: Manages dependencies, plugins, and test lifecycles, easy to integrate with CI tools and Docker.
- **RestAssured**: Makes API testing expressive, easy to read and use. (supports request chaining, assertions)
- **Allure**: Generates rich and interactive HTML reports, easily integrate with TestNG.

---

## Getting Started

### 1. Install dependencies and run test
```bash
mvn clean install
```
> By default, tests run against:
```
http://localhost:8080/api/v3
```
> To run against the public Petstore API:
```bash
mvn test -DbaseUrl=https://petstore3.swagger.io/api/v3
```

---

## Allure Reporting

### 1. Generate the Allure Report
```bash
allure generate target/allure-results --clean -o target/allure-report
```

### 2. Open the Report
```bash
allure open target/allure-report
```

---

## Run with Docker

### 1. Build the Docker Image
```bash
docker build -t petstore-api-tests .
```

### 2. Run Tests in a Container
```bash
docker run --rm -it \
  -v $(pwd)/reports:/app/target/allure-report \
  petstore-api-tests
```

Allure report will save to `./reports`

### 3. Open the Allure Report
```bash
allure open reports
```

---

## Notes
- Only files named `*Test.java` will be picked up by TestNG.
- The Docker image size has been optimized using a multi-stage build and is executed with `java` instead of `mvn` to avoid unnecessary dependencies. It can be further minimized by adopting a more lightweight base image.