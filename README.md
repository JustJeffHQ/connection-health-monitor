# Connection Health Monitor (CHM)

**Pre-flight attribution & device environment diagnostics for Android.**

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Platform: Android](https://img.shields.io/badge/Platform-Android%2010%2B-green.svg)]()
[![Status: RFC / Blueprint](https://img.shields.io/badge/Status-RFC%20%2F%20Architecture%20Blueprint-orange.svg)]()

---

## Why Connection Health Monitor Exists

Mobile app installs, cashback portals, QA test builds, and offerwall milestones rely on a fragile chain of client-side handoffs—browser redirect, Google Play Install Referrer API, Mobile Measurement Partner (MMP) SDK initialization, and background postback delivery. 

When any link in that chain silently fails on the client device, attribution drops without an error message. Common culprits include:
* **Aggressive OEM Background Killing:** Samsung Game Optimizing Service (GOS), Xiaomi MIUI battery savers, or Low Memory Killer (LMK) terminating the browser or target app mid-handshake.
* **DNS & Network Interference:** Forgotten Private DNS ad-blockers (e.g., NextDNS, AdGuard), VPN tunnels, or captive Wi-Fi portals silently dropping MMP telemetry endpoints.
* **Advertising ID & Play Services State:** Reset, zeroed-out, or rate-limited Google Advertising ID (GAID) states, or stale Google Play Services caches.
* **Malformed Deep Links & Redirect Chains:** Stripped click IDs or broken intent handoffs before the Play Store opens.

**Connection Health Monitor** is a lightweight, client-side **Pre-Flight Diagnostic & Telemetry Readiness Tool**. It checks your device's environment *before* you start an install or test flow, giving you a clear **Green / Yellow / Red** readiness readout.

---

## Strict Non-Goals & Fair-Play Policy

To protect user privacy, maintain platform integrity, and ensure complete transparency, **CHM adheres to strict boundaries**:
1. **No Spoofing or Manipulation:** CHM does **not** spoof GAIDs, simulate clicks, inject fake install referrers, or alter network packets.
2. **No Traffic Interception (Zero-MITM):** CHM does **not** run a packet-sniffing VPN or proxy to intercept third-party app traffic.
3. **100% Local & Auditable:** All environment checks run locally on your device. No device fingerprints, advertising IDs, or personal telemetry are transmitted to external servers.

---

## Core Diagnostic Modules

| Module | What It Checks | Why It Matters |
| :--- | :--- | :--- |
| **1. Environment & Network** | Active VPN interfaces, Private DNS (`dns.google`, custom blocklists), captive portals, and IPv4/IPv6 dual-stack stability. | Ad-blocking DNS rules silently blackhole postbacks to MMP domains. |
| **2. Endpoint Reachability** | Non-invasive DNS resolution and TLS handshake latency to standard attribution domains (AppsFlyer, Adjust, Singular, Kochava, Branch). | Confirms attribution servers are reachable before starting a session. |
| **3. Memory & OEM Watchdog** | Available RAM (`ActivityManager.MemoryInfo`), thermal throttling status, battery optimization exemptions, and OEM killer heuristics. | Prevents the OS from killing the browser or app during store-to-app handoffs. |
| **4. Referrer & ID Readiness** | Google Play Services availability, GAID visibility/limit-ad-tracking status, and local `InstallReferrerClient` test verification. | Verifies the OS can pass attribution tokens cleanly to newly installed apps. |

---

## Project Status: Open Blueprint & Call for Contributors

This repository is currently in the **Blueprint / Architecture RFC** phase. The goal is to build a clean, zero-bloat Kotlin + Jetpack Compose utility that anyone can audit, compile, or extend.

### How You Can Contribute
* **OEM Battery/LMK Heuristics:** Submit device-specific checks for Samsung OneUI, Pixel, Motorola, OnePlus, and Xiaomi background restrictions.
* **Endpoint Lists:** Help maintain an open, community-verified JSON list of standard MMP and telemetry hostnames for reachability pings.
* **UI / Jetpack Compose:** Help build out the pre-flight dashboard cards and exportable diagnostic report formatter.

See [`ARCHITECTURE.md`](ARCHITECTURE.md) for the full module breakdown and technical specifications.

---

## License

Released under the [MIT License](LICENSE). Built and maintained by [JustJeffHQ](https://github.com/JustJeffHQ).

