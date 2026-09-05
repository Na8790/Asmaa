package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Trip
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.WarmGoldSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MysteryTripScreen(
    onBack: () -> Unit,
    onBookMysteryTrip: (budget: Double, thrillType: String, guests: Int) -> Unit
) {
    var thrillType by remember { mutableStateOf("مغامرة جبلية وتخييم") }
    var budgetSlider by remember { mutableStateOf(180f) }
    var travelersCount by remember { mutableStateOf(2) }
    var showBookingConfirmation by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("الرحلات المفاجئة (Mystery Trip)", fontWeight = FontWeight.Bold) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Mystery Banner Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(18.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.banner_mystery_trip_1788638357981),
                    contentDescription = "بانر الرحلة المفاجئة",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.45f))
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "وجهة سرية بانتظارك!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarmGoldSecondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "لا نخبرك إلى أين تسافر إلا قبل الانطلاق بـ 24 ساعة عبر إشعار غامض في تطبيقك",
                        fontSize = 12.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Mystery Mechanism Card
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.HelpOutline, contentDescription = null, tint = DeepBluePrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("كيف تعمل الرحلة المفاجئة؟", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("1. تحدد نمط المغامرة والميزانية التي تناسبك.", fontSize = 12.sp)
                    Text("2. يقوم خبراؤنا بتجهيز سيارة، مرشد محلي، تجربة مهنية حصرية، ومكان إقامة ساحر.", fontSize = 12.sp)
                    Text("3. نرسل لك تلميحات بالملابس والمعدات المطلوبة قبل 48 ساعة دون ذكر اسم الوجهة.", fontSize = 12.sp)
                    Text("4. ينفتح صندوق المفاجأة والموقع الدقيق قبل الموعد بـ 24 ساعة!", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("اختر نمط الإثارة والمغامرة:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            listOf("مغامرة جبلية وتخييم", "استرخاء بحري وصيد تقليدي", "استكشاف ثقافي وحرف عتيقة", "سفاري صحراوي ونجوم").forEach { thrill ->
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (thrillType == thrill) DeepBluePrimary.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surface
                    ),
                    border = if (thrillType == thrill) CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(WarmGoldSecondary)) else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = thrillType == thrill,
                            onClick = { thrillType = thrill }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(thrill, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("الميزانية التقديرية للفرد: $${budgetSlider.toInt()}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Slider(
                value = budgetSlider,
                onValueChange = { budgetSlider = it },
                valueRange = 100f..400f,
                colors = SliderDefaults.colors(
                    thumbColor = WarmGoldSecondary,
                    activeTrackColor = DeepBluePrimary
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("عدد المسافرين: $travelersCount", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { if (travelersCount > 1) travelersCount-- }) {
                    Icon(Icons.Default.RemoveCircleOutline, contentDescription = null)
                }
                Text("$travelersCount", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = { if (travelersCount < 8) travelersCount++ }) {
                    Icon(Icons.Default.AddCircleOutline, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    onBookMysteryTrip(budgetSlider.toDouble(), thrillType, travelersCount)
                    showBookingConfirmation = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = DeepBluePrimary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("submit_mystery_trip_button")
            ) {
                Icon(Icons.Default.LockReset, contentDescription = null, tint = WarmGoldSecondary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("تأكيد حجز الرحلة المفاجئة", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }

    if (showBookingConfirmation) {
        AlertDialog(
            onDismissRequest = { showBookingConfirmation = false },
            title = { Text("تم تثبيت حجز الرحلة المفاجئة!", fontWeight = FontWeight.Bold, color = DeepBluePrimary) },
            text = {
                Text(
                    "تهانينا! بدأت مغامرتك الغامضة. رقم الحجز السري: TJR-MYSTERY-${(1000..9999).random()}.\nستصلك أول بطاقة تلميح بالمعدات خلال 24 ساعة عبر صفحة حجوزاتي.",
                    lineHeight = 22.sp
                )
            },
            confirmButton = {
                Button(onClick = { showBookingConfirmation = false; onBack() }) {
                    Text("تم والعودة")
                }
            }
        )
    }
}
