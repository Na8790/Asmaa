package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Experience
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.EmeraldGreenSuccess
import com.example.ui.theme.WarmGoldSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    experiencesCount: Int,
    tripsCount: Int,
    bookingsCount: Int,
    onBack: () -> Unit
) {
    var platformCommission by remember { mutableStateOf(12f) }
    var pendingExperienceStatus by remember { mutableStateOf("PENDING") } // PENDING, APPROVED, REJECTED

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("لوحة التحكم العامة للمنصة", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "رجوع", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DeepBluePrimary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // System Developer Credit Banner (Mandated by Requirement 23)
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = DeepBluePrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = WarmGoldSecondary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "System Designed & Developed by Eng. Raghad Hammoud & Eng. Asmaa Taj Al-Din",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = WarmGoldSecondary
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "نظام الإدارة المركزي ومراقبة جودة الخدمات والتجارب السياحية",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                }
            }

            // Metric Cards Grid
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AdminMetricCard(
                        title = "الحجوزات النشطة",
                        value = "$bookingsCount",
                        icon = Icons.Default.ConfirmationNumber,
                        color = DeepBluePrimary,
                        modifier = Modifier.weight(1f)
                    )
                    AdminMetricCard(
                        title = "تجارب المهن",
                        value = "$experiencesCount",
                        icon = Icons.Default.Workspaces,
                        color = WarmGoldSecondary,
                        modifier = Modifier.weight(1f)
                    )
                    AdminMetricCard(
                        title = "الرحلات المجدولة",
                        value = "$tripsCount",
                        icon = Icons.Default.TravelExplore,
                        color = EmeraldGreenSuccess,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Platform Commission Setting
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("عمولة المنصة على المعاملات:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("${platformCommission.toInt()}%", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = DeepBluePrimary)
                        }
                        Slider(
                            value = platformCommission,
                            onValueChange = { platformCommission = it },
                            valueRange = 5f..25f,
                            colors = SliderDefaults.colors(thumbColor = WarmGoldSecondary, activeTrackColor = DeepBluePrimary)
                        )
                        Text(
                            "يتم استقطاع النسبة لتغطية تكاليف فحص السلامة، ودعم المرشدين، وتأمين التخييم.",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Moderation / Approvals Queue
            item {
                Text("طلبات إضافة تجارب قيد المراجعة والتدقيق:", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = DeepBluePrimary)
            }

            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("تجربة صناعة الجنابي والفضيات التراثية", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (pendingExperienceStatus == "PENDING") WarmGoldSecondary.copy(alpha = 0.2f) else EmeraldGreenSuccess.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    if (pendingExperienceStatus == "PENDING") "قيد المراجعة" else "تمت الموافقة",
                                    fontSize = 10.sp,
                                    color = if (pendingExperienceStatus == "PENDING") DeepBluePrimary else EmeraldGreenSuccess,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("مقدم الطلب: المعلم صالح الصايغ (سوق الملح، صنعاء)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("السعر المقترح: $40 للفرد • المدة: 3 ساعات", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        if (pendingExperienceStatus == "PENDING") {
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { pendingExperienceStatus = "APPROVED" },
                                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreenSuccess),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("اعتماد ونشر التجربة")
                                }
                                OutlinedButton(
                                    onClick = { pendingExperienceStatus = "REJECTED" },
                                    modifier = Modifier.weight(0.7f)
                                ) {
                                    Text("طلب تعديل")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminMetricCard(title: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(6.dp))
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DeepBluePrimary)
            Text(title, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
