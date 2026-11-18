# System Architecture Blueprint

**Connection Health Monitor (CHM)** is designed as a modular, offline-first Android application written in **Kotlin** using **Jetpack Compose** and **Clean Architecture** principles.

Every diagnostic check is isolated inside an independent analyzer module that outputs a standardized `CheckResult` (`PASS`, `WARN`, `FAIL`), allowing the UI and future CLI/export tools to consume results uniformly.

---

## 1. High-Level Module Structure

```text
app/
├── src/main/java/com/justjeffhq/chm/
│   ├── core/
│   │   ├── model/          # CheckResult, Severity, DiagnosticReport
│   │   └── engine/         # PreFlightRunner (coroutined parallel execution)
│   ├── modules/
│   │   ├── network/        # VPN, Private DNS, Captive Portal & MMP reachability
│   │   ├── system/         # RAM pressure, Thermal API, Battery & OEM checks
│   │   ├── attribution/    # GAID state & Play Install Referrer test harness
│   │   └── linkaudit/      # Optional URL redirect & query-parameter inspector
│   ├── data/
│   │   └── endpoints.json  # Bundled, auditable list of MMP test hostnames
│   └── ui/
│       ├── dashboard/      # Pre-Flight Readiness Screen (Green/Yellow/Red cards)
│       └── detail/         # Remediation steps & plain-text report export
