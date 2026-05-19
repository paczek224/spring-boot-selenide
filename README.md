# spring-boot-selenide

A UI test automation framework that combines **Spring Boot** with **Selenide**, demonstrating how to write clean, maintainable browser tests using the Page Object Model pattern, custom assertions, and integrated Allure reporting.

---

## What this project does

- Boots a Spring context before tests to provide dependency injection and configuration management
- Uses Selenide for concise, readable browser interactions
- Applies the **Page Object Model** — each page is a Spring-managed bean with typed element accessors
- Generates rich **Allure reports** with automatic screenshots on failure
- Runs tests **in parallel** (2 forks) via Maven Surefire
- Logs test pass/fail results via a custom JUnit 5 extension

---

## Tech stack

| Layer                | Technology                    | Version                  |
|----------------------|-------------------------------|--------------------------|
| Language             | Java                          | 25                       |
| Framework            | Spring Boot                   | 4.0.6                    |
| Browser automation   | Selenide                      | 7.2.0                    |
| Test runner          | JUnit 5                       | (via Spring Boot Test)   |
| Test reporting       | Allure                        | 2.34.0                   |
| Allure integration   | allure-selenide, allure-junit5| 2.34.0                   |
| AOP weaving          | AspectJ Weaver                | 1.9.25.1                 |
| Code style           | Checkstyle                    | 3.6.0 (plugin)           |
| Build tool           | Maven                         | (via wrapper)            |
| Boilerplate reduction| Lombok                        | (managed by Spring Boot) |

---

## Prerequisites

- **Java 25** (or newer) installed and on `PATH`
- **Google Chrome** installed (default browser)
- Internet access (tests run against `https://www.w3schools.com`)
- No Selenium Grid or WebDriver binary setup needed — Selenide downloads the driver automatically

---

## Code style (Checkstyle)

Checkstyle runs automatically during the **`validate`** phase — before compilation — so the build fails fast on any style violation. The rules are defined in `src/main/checkstyle/checkstyle.xml`.

### What is enforced

| Category         | Rules                                                           |
|------------------|-----------------------------------------------------------------|
| Imports          | No unused imports, no redundant imports                         |
| Modifiers        | Correct modifier order, no redundant modifiers                  |
| Class structure  | Declaration order, one statement per line, no empty statements  |
| Whitespace       | Whitespace around operators/tokens, no trailing whitespace      |
| Curly braces     | Opening brace at end of line, closing brace on same line        |
| File formatting  | Newline at end of file, no multiple trailing blank lines        |
| Correctness      | `equals`/`hashCode` paired, no illegal instantiation           |

### Commands

Run checkstyle only (without compiling or testing):

```bash
./mvnw checkstyle:check
```

Generate an HTML report at `target/reports/checkstyle.html`:

```bash
./mvnw checkstyle:checkstyle
```

Skip checkstyle when needed (e.g. during a WIP build):

```bash
./mvnw test -Dcheckstyle.skip=true
```

---

## Project structure

```
src/
├── main/
│   ├── java/
│   │   └── com/github/paczek224/selenide/
│   │       ├── SelenideApplication.java       # Spring Boot entry point
│   │       ├── config/
│   │       │   └── WebDriverConfig.java       # Maps application.yaml → Selenide config
│   │       └── page/
│   │           ├── AbstractPage.java          # Base class for all page objects
│   │           └── DemoPage.java              # W3Schools HTML Forms page object
│   └── resources/
│       └── application.yaml                   # Browser & URL configuration
└── test/
    └── java/
        └── com/github/paczek224/selenide/
            ├── SelenideApplicationTests.java          # Base test class (Allure + teardown)
            ├── assertions/
            │   └── ElementAssertions.java             # Fluent custom assertions
            ├── extension/
            │   └── TestResultLogger.java              # JUnit 5 extension — logs PASSED/FAILED
            └── demo/
                ├── Demo1Test.java                     # Test: fill first name field
                └── Demo2Test.java                     # Test: fill last name field
```

---

## Configuration

All browser settings live in `src/main/resources/application.yaml` under the `selenide` prefix, which maps directly to `WebDriverConfig` via `@ConfigurationProperties(prefix = "selenide")`:

```yaml
selenide:
  base-url: https://www.w3schools.com
  browser: chrome
  browserSize: "1920x1080"
  headless: false
  timeout: 8000
```

To run tests **headlessly** (e.g. in CI), change `headless: false` to `headless: true`, or pass a system property at runtime:

```bash
./mvnw test -D"selenide.headless"=true
```

---

## Running the tests

### Run all tests

```bash
./mvnw test
```

### Run a single test class

```bash
./mvnw test -Dtest=Demo1Test
```

### Run tests headlessly

```bash
./mvnw test -D"selenide.headless"=true
```

### Run tests in a different browser (e.g. Firefox)

```bash
./mvnw test -D"selenide.browser"=firefox
```

---

## Allure report

### Generate and open the report after a test run

```bash
./mvnw allure:serve
```

This builds the report from `target/allure-results/` and opens it in your default browser automatically.

### Generate a static report (without opening)

```bash
./mvnw allure:report
```

The report is written to `target/site/allure-maven-plugin/`.

---

## Output artifacts

| Path                                | Contents                          |
|-------------------------------------|-----------------------------------|
| `target/allure-results/`            | Raw Allure JSON data              |
| `target/site/allure-maven-plugin/`  | Generated HTML report             |
| `target/reports/screenshots/`       | Screenshots captured by Selenide  |
| `target/downloads/`                 | Files downloaded during tests     |
| `target/surefire-reports/`          | JUnit XML results                 |

---

## How to add a new page and test

1. **Create a page object** in `src/main/java/.../page/` extending `AbstractPage`, annotate with `@Component`, declare `SelenideElement` fields in the constructor.
2. **Inject the page** into your test class with `@Autowired`.
3. **Extend `SelenideApplicationTests`** in your test class — this wires up Allure listeners and browser teardown.
4. **Use `assertThat(page).elementHasValue(...)`** from `ElementAssertions` for readable assertions.

Example:

```java
@Feature("My Feature")
public class MyPageTest extends SelenideApplicationTests {

    @Autowired
    private MyPage myPage;

    @Test
    @Story("User can do X")
    public void shouldDoX() {
        myPage.openPage();
        myPage.clickSomething();
        assertThat(myPage).elementHasValue(MyPage::getSomeInput, "expected");
    }
}
```