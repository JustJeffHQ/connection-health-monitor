package com.justjeffhq.chm.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.justjeffhq.chm.core.model.DiagnosticCheck
import com.justjeffhq.chm.core.model.DiagnosticReport
import com.justjeffhq.chm.core.model.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreFlightScreen(
    report: DiagnosticReport,
    onRunChecksClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Connection Health Monitor") }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onRunChecksClick,
                content = { Text("Run Pre-Flight Check") }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                OverallBanner(status = report.overallStatus)
            }
            items(report.checks, key = { it.id }) { check ->
                DiagnosticCheckCard(check = check)
            }
            item { Spacer(modifier = Modifier.height(72.dp)) }
        }
    }
}

@Composable
private fun OverallBanner(status: Status) {
    val (label, containerColor) = when (status) {
        Status.PASS -> "READY FOR INSTALL HANDOFF" to Color(0xFF1B5E20)
        Status.WARN -> "MINOR FRICTION DETECTED" to Color(0xFFE65100)
        Status.FAIL -> "HIGH RISK: FIX BEFORE CLICKING" to Color(0xFFB71C1C)
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = label,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun DiagnosticCheckCard(check: DiagnosticCheck) {
    val badgeColor = when (check.status) {
        Status.PASS -> Color(0xFF2E7D32)
        Status.WARN -> Color(0xFFEF6C00)
        Status.FAIL -> Color(0xFFC62828)
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = check.category.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Surface(
                    color = badgeColor,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = check.status.name,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = check.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = check.summary,
                style = MaterialTheme.typography.bodyMedium
            )
            check.remediationAction?.let { action ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Fix: $action",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}
