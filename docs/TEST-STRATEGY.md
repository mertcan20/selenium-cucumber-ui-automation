# Test strategy and limits

The original failure was test harness initialization, not evidence of an application defect.
Repair sequence: feature syntax -> binding dry-run -> real browser -> deliberate failure -> report.

| Risk | Input/state | Assertion | Priority |
|---|---|---|---|
| Unauthenticated access after rejected login | Wrong user/password, missing email | Error text AND shop remains hidden | High |
| Wrong payable amount | Two items at 25 TRY | Count 2 and total 50.00; remove returns count/total to zero | High |
| Invalid quantity | 0, -1, stock+1 | Explicit error and unchanged cart | High |
| Incorrect inventory/order side effects | Buy one, then empty checkout | Stock 1, one order, cart 0; retry leaves stock/order count unchanged | High |

Each scenario gets a fresh server/port, browser and page state. Default execution is serial.
Thread-local resources isolate scenario state but are not a claim that parallel execution has been validated.
Stable IDs are part of the local fixture contract; external N11 selectors are not under our control.
Explicit waits wait for relevant elements, not arbitrary sleep durations.

## Known gaps

- This is a browser exercise; no payment provider, persistence, server-side authorization, SQL or network-race simulation.
- Empty repeat checkout is not proof of server-side idempotency under concurrent requests.
- Additional cases: fractional quantity, reload/back navigation, accessibility, keyboard operation, mobile viewport.
- N11 live selectors/account completion need verification. The register-login journey is implemented with operator consent/submission/SMS/CAPTCHA; the same page objects passed a controlled local browser check. See `REGISTER-LOGIN.md`. SMS/CAPTCHA are not bypassed.
- No performance or OWASP compliance claim. Security/load testing require a scoped, authorized target.

## Findings repaired

1. `N11Register.feature` lacked a Feature header: runner initialization failed. Replaced with one valid external login feature; incomplete registration moved to this backlog.
2. Username step had an extra space: binding was undefined. Step text now matches.
3. `testFailureIgnore=true` made errors look like a successful build: removed; a seeded price defect is used to verify the failure gate.
4. Credentials were in tracked properties: new source uses environment variables. History remediation/rotation remains a separate owner action.

## Explain it yourself

1. Follow one scenario: feature -> step -> page -> browser -> assertion.
2. Explain why an error message alone is insufficient when the shop might still be visible.
3. Explain what local fixtures cannot prove about N11.
4. Reproduce a failed assertion, locate the screenshot, and distinguish product, test, and environment faults.
5. State what AI changed and what you personally reviewed/executed. Do not claim unperformed work.
