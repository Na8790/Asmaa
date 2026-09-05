package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.AITravelPlanRequest
import com.example.data.model.AITravelPlanResult
import com.example.domain.ai.AITravelPlannerEngine
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.EmeraldGreenSuccess
import com.example.ui.theme.WarmGoldSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AITravelPlannerScreen(
    onBookItinerary: (title: String, totalCost: Double) -> Unit
) {
    var budget by remember { mutableStateOf(600f) }
    var travelers by remember { mutableStateOf(2) }
    var daysCount by remember { mutableStateOf(3) }
    var city by remember { mutableStateOf("صنعاء وحراز") }
    var tripType by remember { mutableStateOf("استكشاف ثقافي وحرفي") }
    var luxuryLevel by remember { mutableStateOf("متوسط أصيل ومميز") }
    var transport by remember { mutableStateOf("سيارة دفع رباعي مجهزة مع سائق محلي") }

    var planResult by remember { mutableStateOf<AITravelPlanResult?>(null) }
    var isGenerating by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("مساعد السفر الذكي", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DeepBluePrimary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        if (planResult == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.banner_ai_planner_1788638373730),
                        contentDescription = "مساعد السفر الذكي",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.45f)))
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(18.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = WarmGoldSecondary
                        ) {
                            Text(" مدعوم بذكاء تِجربة ", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DeepBluePrimary, modifier = Modifier.padding(4.dp))
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("خطط لرحلتك المتكاملة في ثوانٍ", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text("نحلل ميزانيتك ونربطك بتجارب المهن ووسائل النقل المناسبة", fontSize = 12.sp, color = Color.White.copy(alpha = 0.9f))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text("الوجهة المستهدفة:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("صنعاء وحراز", "حضرموت ودوعن", "إب واللواء الأخضر", "المخا والساحل").forEach { c ->
                        FilterChip(
                            selected = city == c,
                            onClick = { city = c },
                            label = { Text(c, fontSize = 12.sp) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("الميزانية الإجمالية التقديرية: $${budget.toInt()}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Slider(
                    value = budget,
                    onValueChange = { budget = it },
                    valueRange = 200f..2500f,
                    colors = SliderDefaults.colors(thumbColor = WarmGoldSecondary, activeTrackColor = DeepBluePrimary)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("مدة الرحلة (أيام): $daysCount", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { if (daysCount > 1) daysCount-- }) { Icon(Icons.Default.Remove, contentDescription = null) }
                            Text("$daysCount", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            IconButton(onClick = { if (daysCount < 7) daysCount++ }) { Icon(Icons.Default.Add, contentDescription = null) }
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text("عدد المسافرين: $travelers", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { if (travelers > 1) travelers-- }) { Icon(Icons.Default.Remove, contentDescription = null) }
                            Text("$travelers", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            IconButton(onClick = { if (travelers < 10) travelers++ }) { Icon(Icons.Default.Add, contentDescription = null) }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("نوع الرحلة والاهتمام:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                listOf("استكشاف ثقافي وحرفي", "مغامرة وتخييم في الطبيعة", "استرخاء وتذوق ريفي").forEach { t ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = tripType == t, onClick = { tripType = t })
                        Text(t, fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("وسيلة النقل المفضلة:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                listOf(
                    "سيارة دفع رباعي مجهزة مع سائق محلي",
                    "حافلة فان عائلية سياحية",
                    "بدون تنقل (اعتماد التنقل المحلي الذاتي)"
                ).forEach { tr ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = transport == tr, onClick = { transport = tr })
                        Text(tr, fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val req = AITravelPlanRequest(
                            budget = budget.toDouble(),
                            travelerCount = travelers,
                            durationDays = daysCount,
                            city = city,
                            tripType = tripType,
                            luxuryLevel = luxuryLevel,
                            transportNeeded = transport
                        )
                        planResult = AITravelPlannerEngine.generateItinerary(req)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("generate_ai_plan_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepBluePrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = WarmGoldSecondary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("إنشاء خطة السفر الذكية المخصصة", fontWeight = FontWeight.Bold)
                }
            }
        } else {
            // Display Itinerary
            val result = planResult!!
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = DeepBluePrimary)
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("الخطة المقترحة لرحلتك", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = WarmGoldSecondary)
                                IconButton(onClick = { planResult = null }) {
                                    Icon(Icons.Default.Refresh, contentDescription = "تعديل المعايير", tint = Color.White)
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(result.summary, fontSize = 13.sp, color = Color.White, lineHeight = 20.sp)
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("التكلفة التقديرية الإجمالية:", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                                Text("$${result.totalEstimatedCost.toInt()}", color = WarmGoldSecondary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = DeepBluePrimary, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("المسافات والنقل المقترح:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(result.transportationNote, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }

                items(result.days) { day ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = DeepBluePrimary
                                ) {
                                    Text(" اليوم ${day.dayNumber} ", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp, modifier = Modifier.padding(4.dp))
                                }
                                Text("تكلفة اليوم: ~$${day.estimatedDayCost.toInt()}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = EmeraldGreenSuccess)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(day.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DeepBluePrimary)

                            Spacer(modifier = Modifier.height(10.dp))
                            ItineraryTimeRow(icon = Icons.Default.WbSunny, time = "الصباح:", desc = day.morningActivity)
                            ItineraryTimeRow(icon = Icons.Default.Restaurant, time = "الغداء:", desc = day.lunchRecommendation)
                            ItineraryTimeRow(icon = Icons.Default.Handyman, time = "بعد الظهر:", desc = day.afternoonExperience)
                            ItineraryTimeRow(icon = Icons.Default.NightsStay, time = "المساء:", desc = day.eveningCamp)
                        }
                    }
                }

                item {
                    // Disclaimer & Booking Button
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        Text(
                            result.disclaimer,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 16.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = {
                                onBookItinerary("باقة السفر الذكية: ${result.city}", result.totalEstimatedCost)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = DeepBluePrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().height(50.dp)
                        ) {
                            Text("تثبيت وحجز هذه الخطة كرحلة معتمدة", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItineraryTimeRow(icon: androidx.compose.ui.graphics.vector.ImageVector, time: String, desc: String) {
    Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.Top) {
        Icon(imageVector = icon, contentDescription = null, tint = WarmGoldSecondary, modifier = Modifier.size(16.dp).padding(top = 2.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = time, fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.width(55.dp))
        Text(text = desc, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
    }
}
