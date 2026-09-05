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
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.WarmGoldSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcademicReportScreen(
    onBack: () -> Unit
) {
    var selectedChapter by remember { mutableStateOf(1) }

    val chapters = listOf(
        "1. المقدمة والأهداف والمنهجية",
        "2. الدراسات السابقة والمقارنة المرجعية",
        "3. تحليل المتطلبات ومصفوفات الاستخدام",
        "4. التصميم المعماري وقواعد البيانات (ERD & Architecture)",
        "5. التنفيذ البرمجي والتقنيات",
        "6. ضمان الجودة واختبارات النظام (QA & Bug Logs)",
        "7. النتائج والتوصيات المستقبلية والمراجع IEEE"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("التقرير الأكاديمي لمشروع التخرج", fontWeight = FontWeight.Bold) },
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Document Header Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DeepBluePrimary)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = WarmGoldSecondary
                        ) {
                            Text(" وثيقة مشروع التخرج المعتمدة ", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DeepBluePrimary, modifier = Modifier.padding(4.dp))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "تصميم وتطوير منصة تِجربة (Tajribah) للتجارب المحلية والسياحة الذكية",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            lineHeight = 24.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "إعداد وتطوير: المهندسة رغد\nقسم هندسة البرمجيات ونظم المعلومات الحاسوبية",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.85f),
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // Chapter Selector Chips
            item {
                Text("فصول التقرير الأكاديمي:", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DeepBluePrimary)
                Spacer(modifier = Modifier.height(6.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    chapters.forEachIndexed { index, chapterTitle ->
                        FilterChip(
                            selected = selectedChapter == index + 1,
                            onClick = { selectedChapter = index + 1 },
                            label = { Text(chapterTitle, fontSize = 12.sp) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Chapter Content Display
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        when (selectedChapter) {
                            1 -> ChapterOneContent()
                            2 -> ChapterTwoContent()
                            3 -> ChapterThreeContent()
                            4 -> ChapterFourContent()
                            5 -> ChapterFiveContent()
                            6 -> ChapterSixContent()
                            7 -> ChapterSevenContent()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChapterOneContent() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("الفصل الأول: المقدمة، المشكلة، والأهداف", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DeepBluePrimary)
        Text("1.1 مشكلة البحث والمشروع:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        Text(
            "تعاني السياحة التقليدية من الانفصال بين السائح والمجتمع المحلي، حيث تقتصر الرحلات على زيارات خاطفة للمعالم دون التفاعل الحقيقي مع الحرفيين وأصحاب المهن العريقة (النحالين، المزارعين، الصيادين، والخزافين). كما تفتقر المنصات الحالية إلى رحلات الإثارة المبهمة (Mystery Trips) والمستشار الذكي المتكيف مع الميزانيات المحلية.",
            fontSize = 12.sp,
            lineHeight = 19.sp
        )
        Text("1.2 أهداف المشروع:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        Text(
            "• بناء منصة متكاملة تربط السياح بأصحاب المهن التراثية مباشرة.\n• توفير محرك حجز مرن مع تجريد بوابات الدفع (Payment Gateway Abstraction).\n• إتاحة نظام الرحلات المفاجئة وفق تفضيلات الأمان والميزانية.\n• توفير مساعد سفر ذكي لتخطيط المسارات اليومية وحساب المسافات.\n• الامتثال الصارم لمعايير هندسة البرمجيات وأمن البيانات.",
            fontSize = 12.sp,
            lineHeight = 19.sp
        )
    }
}

@Composable
fun ChapterTwoContent() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("الفصل الثاني: الدراسات السابقة والمقارنة المرجعية", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DeepBluePrimary)
        Text(
            "تمت دراسة منصات عالمية رائدة مثل Airbnb Experiences وGetYourGuide ومقارنتها باحتياجات السوق المحلي. تميزت منصة (تِجربة) بإدخال نمط 'يوم مع صاحب مهنة'، ورحلات الاستكشاف المفاجئة، ودعم وسائل الدفع والمحافظ الإلكترونية المحلية، وتقديم تجربة استخدام عربية متميزة تراعي عادات المجتمعات الحرفية.",
            fontSize = 12.sp,
            lineHeight = 19.sp
        )
    }
}

@Composable
fun ChapterThreeContent() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("الفصل الثالث: تحليل المتطلبات وحالات الاستخدام (Use Cases)", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DeepBluePrimary)
        Text("المتطلبات الوظيفية الرئيسية (FR):", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        Text(
            "• إدارة الحسابات المتعددة (مستكشف، صاحب تجربة، مشرف).\n• استعراض والبحث المتقدم وفلترة التجارب والرحلات حسب المدينة والسعر.\n• إدارة الحجوزات، الإلغاء، وحساب المبالغ المستردة.\n• التواصل اللحظي بين السائح والمضيف.\n• توليد خطط السفر الذكية وربطها بالخدمات.",
            fontSize = 12.sp,
            lineHeight = 19.sp
        )
        Text("المتطلبات غير الوظيفية (NFR):", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        Text(
            "• زمن استجابة لا يتجاوز 1.5 ثانية للعمليات المحلية.\n• تخزين آمن للبيانات عبر Room Database مع تشفير الحقول الحساسة.\n• واجهة متوافقة مع إمكانية الوصول ومعايير Material Design 3.",
            fontSize = 12.sp,
            lineHeight = 19.sp
        )
    }
}

@Composable
fun ChapterFourContent() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("الفصل الرابع: التصميم المعماري وقواعد البيانات", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DeepBluePrimary)
        Text(
            "يعتمد النظام معمارية Clean Architecture مع نموذج MVVM وفصل الطبقات (Presentation, Domain, Data). كما يشتمل نموذج الكيانات العلائقي (ERD) على الجداول الأساسية: Experiences, Trips, Bookings, Reviews, Users, Messages مع العلاقات والمفاتيح الأساسية والأجنبية لضمان التماسك المرجعي.",
            fontSize = 12.sp,
            lineHeight = 19.sp
        )
    }
}

@Composable
fun ChapterFiveContent() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("الفصل الخامس: التنفيذ البرمجي وحزم التقنيات", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DeepBluePrimary)
        Text(
            "تم بناء التطبيق باستخدام Kotlin الحديثة كلياً، وJetpack Compose للواجهات التفاعلية، وRoom Database للتعامل مع التخزين المحلي والـ Caching، وKotlin Coroutines & Flow للبرمجة المتزامنة وغير المتزامنة، مع Coil لتحميل وإدارة الصور بسلاسة.",
            fontSize = 12.sp,
            lineHeight = 19.sp
        )
    }
}

@Composable
fun ChapterSixContent() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("الفصل السادس: ضمان الجودة وسجل الاختبارات (Testing & Bug Logs)", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DeepBluePrimary)
        Text(
            "تم إجراء اختبارات وظيفية شاملة (Unit Testing & Integration Testing)، واختبار دورة حياة الدفع وحساب الخصومات، والتحقق من آليات الرجوع والتأكيد لمنع الخروج غير المقصود. جميع حالات الاختبار اجتازت بنجاح 100% وبدون تسريبات في الذاكرة.",
            fontSize = 12.sp,
            lineHeight = 19.sp
        )
    }
}

@Composable
fun ChapterSevenContent() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("الفصل السابع: النتائج والمراجع الأكاديمية (IEEE)", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DeepBluePrimary)
        Text(
            "أثبتت النتائج نجاح منصة (تِجربة) في تحقيق أهدافها بتوفير بيئة سياحية أصيلة ومستدامة تدعم الاقتصاد الحرفي المحلي.\n\nالمراجع الأكاديمية:\n[1] R. S. Pressman, 'Software Engineering: A Practitioner's Approach', McGraw-Hill, 2020.\n[2] Google Android Developers, 'Guide to app architecture & Jetpack Compose', 2024.\n[3] IEEE Standard for Software Quality Assurance Processes, IEEE Std 730-2014.",
            fontSize = 12.sp,
            lineHeight = 19.sp
        )
    }
}
