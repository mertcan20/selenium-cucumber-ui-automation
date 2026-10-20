# Selenium / Cucumber UI automation

Java 17+, Maven 3.9+, Selenium, Cucumber and JUnit. AI-assisted portfolio refresh of an existing N11 exercise.
The aim is explainable test design, independent scenarios, useful failure evidence and a build that fails on errors.
This repository is not evidence of production N11 employment or of the author's assessed seniority.

## Run

From this directory, with Chrome installed:

```sh
mvn -B clean verify
mvn -B '-Dcucumber.filter.tags=@local and @smoke' test
mvn -B '-Dcucumber.execution.dry-run=true' '-Dcucumber.filter.tags=@local or @external' test
```

`mvn clean verify` runs nine local Cucumber scenarios plus one registration/login page-object check in headless Chrome (ten tests total). The HTTP fixtures bind loopback only, on free ports.
Selenium Manager may download a matching driver on first use. A full JDK is required (`jdk.httpserver`).
Overrides: `-Dbrowser=edge`, `-Dbrowser=firefox`, `-Dheadless=false`.
No implicit waits, automatic scenario retries, or hidden failure-ignore flags are used.

## Evidence and scope

| Layer | Coverage | What it does not prove |
|---|---|---|
| Local real-browser regression | Valid/invalid/missing login input, cart amount/removal, zero/negative/over-stock quantity, stock/order changes and empty repeat checkout | Real authentication, payment, server idempotency, production security |
| N11 external login | Original page object and login steps; parser and step binding repaired | Live site compatibility until run with an authorized test account |

Reports: `target/cucumber-reports.html`, `target/cucumber.json`, `target/cucumber.xml`, `target/surefire-reports/`.
Failed browser scenarios attach screenshots. Capture errors cannot prevent browser/server cleanup.
The local fixture has public dummy credentials; it deliberately has no production authentication backend.

## N11 opt-in

Supply `N11_USERNAME` and `N11_PASSWORD` as environment variables, then:

```sh
mvn -B '-Dtest=runner.TestRunner' '-Dcucumber.filter.tags=@login' test
```

No account values are committed. External login is excluded from default/CI execution.
Only run it with a permitted test account. CAPTCHA, changed selectors and site protections may block execution.
The new register → same-account login journey is described in [REGISTER-LOGIN.md](docs/REGISTER-LOGIN.md). It fills registration fields, waits for operator consent/submission/verification, then logs in with the same credentials in a fresh browser. Use `@register-login` and visible browser mode. Live registration is not yet validated. Product-add remains a backlog item.
The original repository exposed account values: if real, rotate them; removing them from a new version does not remove Git history.

## Structure

- `features/`: behaviour and expected outcomes.
- `pages/`: browser interactions and explicit waits.
- `stepdefinitions/`: business steps and assertions.
- `support/LocalSite.java` and `site/index.html`: controlled fixture, reset per scenario.
- `.github/workflows/tests.yml`: proposed standalone repository CI; report upload runs even after failures.
- `docs/`: risks, known limitations and interview walkthrough.

CI configuration is supplied, but no GitHub run is claimed until it has actually been published and executed.
Versions remain pinned; passing functional tests is not a dependency vulnerability audit.
