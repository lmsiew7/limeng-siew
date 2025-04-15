# Stage 1: Build and package
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .
RUN mvn clean package -DskipTests
RUN mvn dependency:copy-dependencies -DincludeScope=test -DoutputDirectory=target/dependency

# Stage 2: Minimal runtime image
FROM eclipse-temurin:17-alpine

ENV API_BASE_URL=http://host.docker.internal:8080/api/v3

# Install Allure CLI
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://github.com/allure-framework/allure2/releases/download/2.25.0/allure-2.25.0.tgz && \
    tar -xzf allure-2.25.0.tgz -C /opt/ && \
    ln -s /opt/allure-2.25.0/bin/allure /usr/bin/allure && \
    rm -rf /var/lib/apt/lists/* allure-2.25.0.tgz

WORKDIR /app

# Copy files from build stage
COPY --from=build /app/target/classes target/classes
COPY --from=build /app/target/test-classes target/test-classes
COPY --from=build /app/target/dependency target/dependency
COPY --from=build /app/testng.xml testng.xml

# Run tests and generate report
CMD java -cp "target/classes:target/test-classes:target/dependency/*" \
    org.testng.TestNG testng.xml && \
    allure generate allure-results --clean -o target/allure-report
