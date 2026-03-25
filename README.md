# JavaAutomation

A **Java test automation skeleton** for end-to-end checks across **web (browser)**, **HTTP APIs**, and **mobile (Appium)**. The same project layout and configuration style apply to every channel so you can add tests without tying page objects or API calls to a particular runner.

## Capabilities

| Channel | Technology | Entry points |
|--------|------------|--------------|
| **WEB** | Selenium 4 + WebDriverManager | `WebDriverFactory`, `WebSession`, `WebDriverCommon`, `framework.webelements.*` |
| **API** | REST Assured | `ApiTestClient.fromConfig(...)` |
| **MOBILE** | Appium Java Client | `MobileDriverFactory`, `MobileSession` |

### Context-agnostic usage

- **Web:** UI elements resolve the active driver through `WebSession` (thread-local). Tests call `WebSession.bind(driver, waitSeconds)` after creating a driver and `WebSession.unbind()` in teardown (quit is handled there).
- **API:** No browser; use `ApiTestClient` built from `config.properties` (`api.baseUri`, optional headers).
- **Mobile:** Same pattern as web via `MobileSession` and an `AppiumDriver` from `MobileDriverFactory`.

The active **logical** channel can be documented or gated in your suites using `AutomationChannel` and `AutomationEnvironment.currentChannel(config)` (for example, to skip web-only tests when `-Dautomation.channel=API`).

## Requirements

- **JDK 11+** for compiling the project (`sourceCompatibility` / `targetCompatibility`).
- Set **`JAVA_HOME`** (or ensure `java` is on `PATH`) before `./gradlew`. The wrapper uses **Gradle 8.5**, which supports current JDKs; **Gradle 7.1 + JDK 17** fails while compiling settings scripts with `Unsupported class file major version 61`.
- **Web:** Chrome, Firefox, or Edge (managed drivers via WebDriverManager).
- **API:** None beyond network access to your `api.baseUri`.
- **Mobile:** Appium server reachable at `mobile.hubUrl`, matching capabilities in config, and a device or emulator.

## Configuration

Defaults live in `src/main/resources/config.properties`.

| Key | Purpose |
|-----|---------|
| `automation.channel` | `WEB`, `API`, or `MOBILE` (documentation / your own filtering); override with `-Dautomation.channel=...` |
| `url` | Browser base URL for web samples |
| `wait` | Default explicit wait (seconds) for `WebSession` |
| `browser` | `chrome`, `firefox`, or `edge` |
| `browser.headless` | `true` / `false` |
| `browser.acceptInsecureCerts` | `true` / `false` |
| `api.baseUri` | REST Assured base URI |
| `api.authHeaderName` / `api.authHeaderValue` | Optional static header (e.g. bearer token) |
| `mobile.hubUrl` | Appium server URL |
| `mobile.platform` | `android` or `ios` |
| `mobile.deviceName`, `mobile.platformVersion` | Device / OS |
| `mobile.app` | Path to `.apk` / `.ipa` or leave empty with `appPackage` / `appActivity` (Android) |
| `mobile.appPackage` / `mobile.appActivity` | Android launch |
| `mobile.bundleId` | iOS bundle id |

## Run tests

```bash
./gradlew test
```

- **Mobile sample** (`ExampleMobileSessionTest`) is **skipped** unless you enable it:

  ```bash
  export RUN_MOBILE_E2E=true   # Unix
  set RUN_MOBILE_E2E=true      # Windows cmd
  ```

  Start Appium and align `config.properties` with your device before running.

- **Web sample** hits Google and may need cookie/locale adjustments in your environment.

## Project layout

```
src/main/java/framework/
  context/          # AutomationChannel, WebSession, AutomationEnvironment
  api/              # ApiTestClient
  mobile/           # MobileDriverFactory, MobileSession
  utils/config/     # PropertiesUtils
  utils/web/        # WebDriverFactory, WebDriverCommon
  webelements/      # Thin element wrappers (use WebSession)
src/main/java/pageObjects/
src/main/java/tasks/
src/test/java/testcases/web|api|mobile/
```

## Adding your own E2E

1. **Web:** Add page objects under `pageObjects`, workflows under `tasks`, tests under `testcases.web`. Bind `WebSession` in `@Before` and unbind in `@After`.
2. **API:** Use `ApiTestClient.fromConfig(new PropertiesUtils())` and assert on `ValidatableResponse`.
3. **Mobile:** Build the driver with `MobileDriverFactory`, `MobileSession.bind(driver)`, interact with `MobileSession.driver()`, then `MobileSession.unbind()`.

Keep environment-specific values in `config.properties` or externalize with profiles later; avoid hard-coding URLs or credentials in test code.
