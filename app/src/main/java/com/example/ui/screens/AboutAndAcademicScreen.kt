package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.DeveloperFooter
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.EmeraldGreenSuccess
import com.example.ui.theme.WarmGoldSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onOpenAcademicReport: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("حول منصة تِجربة والمشروع", fontWeight = FontWeight.Bold) },
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
            // App Branding Banner
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = DeepBluePrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_tajribah_logo_1788638291277),
                            contentDescription = "شعار تِجربة",
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "منصة تِجربة – Tajribah",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = WarmGoldSecondary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "منصة التجارب المحلية، الرحلات المفاجئة، والسفر الذكي",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.9f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "تم تصميم وتطوير التطبيق بواسطة المهندسة رغد حمود والمهندسة أسماء تاج الدين",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = WarmGoldSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Developer Official Card (Requirement 23)
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(WarmGoldSecondary, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Engineering, contentDescription = null, tint = DeepBluePrimary)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "فريق التطوير: المهندسة رغد حمود & المهندسة أسماء تاج الدين",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = DeepBluePrimary
                                )
                                Text(
                                    text = "المسمى: Software Engineers & Application Developers",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = WarmGoldSecondary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "الوصف: تم تصميم وتطوير هذا التطبيق وفق أحدث معايير هندسة البرمجيات وتجربة المستخدم، بهدف تقديم منصة عالمية للتجارب المحلية والسفر الذكي.",
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Academic Report Entry Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpenAcademicReport() }
                        .testTag("academic_report_button")
                ) {
                    Row(
                        modifier = Modifier.padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(DeepBluePrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.School, contentDescription = null, tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "التقرير الأكاديمي لمشروع التخرج",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = DeepBluePrimary
                            )
                            Text(
                                text = "استعراض الفصول العشرة والمخططات وجداول الاختبارات وفق معايير الجامعة",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(Icons.Default.ChevronLeft, contentDescription = null, tint = DeepBluePrimary)
                    }
                }
            }

            // Project Vision & Features Checklist
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "ركائز مشروع تِجربة الأساسية",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = DeepBluePrimary
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        PillarItem("التجارب الحية مع أصحاب المهن", "معايشة النحّال والمزارع والصياد والخزاف في بيئتهم الحقيقية.")
                        PillarItem("الرحلات المفاجئة (Mystery Trips)", "سياحة استكشافية قائمة على التشويق وحفظ أسرار المسار.")
                        PillarItem("مساعد السفر الذكي (AI Travel Advisor)", "بناء خطط يومية مخصصة حسب الميزانية وربطها بالخدمات.")
                        PillarItem("حماية الطرفين وسياسة الدفع الموثوقة", "طبقة Payment Abstraction مع سياسة استرجاع واضحة وإيصالات رقمية.")
                    }
                }
            }

            item {
                DeveloperFooter()
            }
        }
    }
}

@Composable
fun PillarItem(title: String, desc: String) {
    Row(modifier = Modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.Top) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldGreenSuccess, modifier = Modifier.size(18.dp).padding(top = 2.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = DeepBluePrimary)
            Text(desc, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
