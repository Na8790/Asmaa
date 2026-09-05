package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.navigation.MainTab
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.WarmGoldSecondary

@Composable
fun ExitConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "تأكيد الخروج",
                fontWeight = FontWeight.Bold,
                color = DeepBluePrimary
            )
        },
        text = {
            Text(
                text = "هل تريد الخروج من التطبيق؟",
                fontSize = 16.sp
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.testTag("exit_confirm_button")
            ) {
                Text("نعم")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("exit_cancel_button")
            ) {
                Text("إلغاء")
            }
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = MaterialTheme.colorScheme.surface
    )
}

@Composable
fun DeveloperFooter(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider(
            modifier = Modifier
                .width(120.dp)
                .padding(bottom = 10.dp),
            color = WarmGoldSecondary.copy(alpha = 0.5f),
            thickness = 1.5.dp
        )
        Text(
            text = "© جميع الحقوق محفوظة",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "تصميم وتطوير: المهندسة رغد",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = WarmGoldSecondary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TajribahBottomNavBar(
    currentRoute: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        tonalElevation = 8.dp,
        shadowElevation = 12.dp,
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            modifier = Modifier.height(68.dp)
        ) {
            MainTab.values().forEach { tab ->
                val isSelected = currentRoute == tab.route
                val icon = when (tab) {
                    MainTab.HOME -> if (isSelected) Icons.Filled.Home else Icons.Outlined.Home
                    MainTab.TRIPS -> if (isSelected) Icons.Filled.Explore else Icons.Outlined.Explore
                    MainTab.AI_PLANNER -> if (isSelected) Icons.Filled.AutoAwesome else Icons.Outlined.AutoAwesome
                    MainTab.BOOKINGS -> if (isSelected) Icons.Filled.CalendarMonth else Icons.Outlined.CalendarToday
                    MainTab.CHAT -> if (isSelected) Icons.Filled.Chat else Icons.Outlined.ChatBubbleOutline
                    MainTab.ABOUT -> if (isSelected) Icons.Filled.Info else Icons.Outlined.Info
                }

                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onTabSelected(tab.route) },
                    icon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = tab.titleAr,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = tab.titleAr,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = DeepBluePrimary,
                        indicatorColor = DeepBluePrimary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_tab_${tab.route}")
                )
            }
        }
    }
}

@Composable
fun RatingStars(rating: Double, reviewCount: Int? = null) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "تقييم",
            tint = WarmGoldSecondary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = String.format("%.1f", rating),
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
        if (reviewCount != null) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "($reviewCount)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
