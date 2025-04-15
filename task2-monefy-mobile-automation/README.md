# Monefy Android App Automation

This project is an automated UI testing framework for the [Monefy](https://play.google.com/store/apps/details?id=com.monefy.app.lite) Android app. 

---

## Test Scenarios Covered

- Add a new expense using the category icon and verify balance update  
- Add a new income transaction and confirm the updated balance  
- Add a new account and ensure it appears in the account list

---

## Prerequisites

- Java 11+  
- Maven 3+  
- Appium Server (running at `http://127.0.0.1:4723`)  
- Android emulator or real device  
- Allure CLI (`npm install -g allure-commandline` or `brew install allure`)

---

## Framework Design

### Scalable

- Each test class targets a specific feature or flow for clarity.  
- Easily extensible architecture: new features can be tested by creating new page objects (extending `BasePage`) and adding test classes with minimal effort.

### Maintainable

- The modular structure eases UI changes. For instance, if the Expense screen UI changes, only the `ExpensePage` class needs to be updated.  
- Shared functionality (like setup/teardown and driver initialization) is centralized in `BaseTest` and `BasePage`, simplifying updates across the suite.

---

## Tech Stack
### Approach
- Follows the **Page Object Model (POM)** design pattern.
- `BasePage` handles common UI actions.
- Each screen (e.g., `AccountPage`) extends `BasePage`.
- `BaseTest` handles test setup and teardown 

### Reasoning
- **Java**: Robust and widely used for Appium.
- **Appium**: Supports cross-platform mobile automation.
- **TestNG**: Powerful testing framework with annotations and parallel execution.
- **Maven**: Build and dependency management tool
- **Allure**: Generates rich and interactive HTML reports, easily integrate with TestNG.

---

## Getting Started

### 1. Install Project Dependencies
```bash
mvn clean install
```

### 2. Start Android Emulator
Ensure your Android emulator or physical device is up and running. You can verify this using the adb devices command:
```bash
adb devices
```     
> This will list all connected devices. A properly running emulator should appear as something like:
```bash
List of devices attached
emulator-5554	device
```
       
### 3. Start Appium Server
```bash
appium
```

### 4. Run the Test Suite
```bash
mvn clean test
```

---

## Allure Reporting

### 1. Generate the Report
```bash
allure generate target/allure-results --clean -o target/allure-report
```

### 2. Open the Report
```bash
allure open target/allure-report
```

---

## Running in Docker

### Build Image
```bash
docker build -t android-test .
```

### Run Tests
```bash
docker run -it --rm \
  -e APPIUM_SERVER=http://host.docker.internal:4723 \
  -e APK_PATH=${PWD}/apk/monefy.apk \
  -v ${PWD}/reports:/usr/src/app/allure-report \
  android-test
```

> `host.docker.internal` is supported on macOS and Windows only.  
> Ensure that `APK_PATH` is accessible by the Appium server.

---

## Notes
- `BaseTest.java` defines the path to the APK file.
- The project currently uses an older version of the Monefy APK for stability
