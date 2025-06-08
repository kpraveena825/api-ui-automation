
# API & UI Test Automation Framework

This repository contains a **Test Automation Framework** built using **Selenium WebDriver**, **Rest Assured**, and **Java** for comprehensive testing of **Web UI** and **RESTful APIs**.

---

## 📋 Prerequisites

Ensure the following tools are installed:

- Java Development Kit (JDK) 11 or higher
- An IDE such as IntelliJ IDEA, Eclipse, or VS Code
- Maven 3.x

---

## 🛠️ Tech Stack

| Layer           | Tools / Libraries          |
|-----------------|----------------------------|
| Language        | Java                       |
| UI Testing      | Selenium WebDriver         |
| API Testing     | Rest Assured               |
| Test Runners    | TestNG, JUnit              |
| Build Tool      | Maven                      |
| Reporting       | Cucumber HTML Reporter     |
| Design Pattern  | Page Object Model (POM)    |

---

## ⚙️ Setup

### 1. Clone the Repository

```bash
git clone https://github.com/kpraveena825/api-ui-automation.git

### 2. import the repository

### 3. Install Dependencies

### 4. Running Tests

### 5. Project Structure

src/
├── main/
│   └── java/
│       └── org.apiframework/
│           ├── pageobjects/        # Page Object classes for UI elements
│           ├── utility/            # Utility classes
│           └── ApiFunctions.java   # API-related logic and constants
├── test/
│   ├── java/
│   │   ├── features/               # Gherkin feature files
│   │   ├── stepDefinitions/        # Step definitions mapping feature steps to code
│   │   └── options/                # Test runner (TestRunner.java)
│   └── resources/                  # Test data files (Expected JSON, actual response data)
├── pom.xml                         # Project dependencies and build configuration

### 6. Reporting

target/cucumber-report-html/cucumber-html-reports/feature-overview.html
