FROM maven:3.9.6-eclipse-temurin-21

# Install Allure CLI
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://github.com/allure-framework/allure2/releases/download/2.25.0/allure-2.25.0.tgz && \
    tar -xzf allure-2.25.0.tgz -C /opt/ && \
    ln -s /opt/allure-2.25.0/bin/allure /usr/bin/allure && \
    rm -rf /var/lib/apt/lists/* allure-2.25.0.tgz

WORKDIR /usr/src/app
COPY . .

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY . .

CMD mvn clean test && allure generate target/allure-results --clean -o allure-report