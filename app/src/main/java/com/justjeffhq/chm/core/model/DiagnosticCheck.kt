package com.justjeffhq.chm.core.model

enum class Status {
    PASS,   // Optimal for clean attribution handoff
    WARN,   // Potential friction (e.g., high RAM pressure, IPv6 fallback)
    FAIL    // High probability of dropped attribution (e.g., active blocklist DNS)
}

data class DiagnosticCheck(
    val id: String,
    val category: String,
    val title: String,
    val status: Status,
    val summary: String,
    val technicalDetail: String,
    val remediationAction: String? = null
)

data class DiagnosticReport(
    val timestampMillis: Long = System.currentTimeMillis(),
    val checks: List<DiagnosticCheck>
) {
    val overallStatus: Status
        get() = when {
            checks.any { it.status == Status.FAIL } -> Status.FAIL
            checks.any { it.status == Status.WARN } -> Status.WARN
            else -> Status.PASS
        }
}
