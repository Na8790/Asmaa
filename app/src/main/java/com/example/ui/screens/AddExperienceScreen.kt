package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.data.model.ExperienceCategory
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.WarmGoldSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExperienceScreen(
    onBack: () -> Unit,
    onSubmit: (Experience) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(ExperienceCategory.HANDICRAFTS) }
    var hostName by remember { mutableStateOf("") }
    var hostTitle by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var locationDesc by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("35") }
    var durationText by remember { mutableStateOf("4") }
    var groupSizeText by remember { mutableStateOf("6") }
    var description by remember { mutableStateOf("") }
    var requirements by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("إضافة تجربة مهنية جديدة", fontWeight = FontWeight.Bold) },
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
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("بيانات التجربة والحرفة:", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DeepBluePrimary)

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("عنوان التجربة (مثال: يوم مع نحّال، قطاف البن)") },
                modifier = Modifier.fillMaxWidth().testTag("add_title_input"),
                singleLine = true
            )

            Text("نوع الحرفة أو التجربة:", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            var expandedCat by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedCat,
                onExpandedChange = { expandedCat = !expandedCat }
            ) {
                OutlinedTextField(
                    value = selectedCategory.titleAr,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCat) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedCat,
                    onDismissRequest = { expandedCat = false }
                ) {
                    ExperienceCategory.values().forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat.titleAr) },
                            onClick = {
                                selectedCategory = cat
                                expandedCat = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = hostName,
                onValueChange = { hostName = it },
                label = { Text("اسم صاحب المهنة أو المضيف") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = hostTitle,
                onValueChange = { hostTitle = it },
                label = { Text("اللقب أو الخبرة (مثال: خبير النحل البري منذ 25 سنة)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("المدينة أو القرية") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = priceText,
                    onValueChange = { priceText = it },
                    label = { Text("السعر للفرد ($)") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = durationText,
                    onValueChange = { durationText = it },
                    label = { Text("المدة (بالساعات)") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = groupSizeText,
                    onValueChange = { groupSizeText = it },
                    label = { Text("أقصى عدد أفراد") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            OutlinedTextField(
                value = locationDesc,
                onValueChange = { locationDesc = it },
                label = { Text("وصف الموقع الدقيق ونقطة التجمع") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("الوصف التفصيلي لما سيعيشه السائح") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            OutlinedTextField(
                value = requirements,
                onValueChange = { requirements = it },
                label = { Text("متطلبات السلامة والملابس المطلوبة") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    val exp = Experience(
                        id = "exp_${System.currentTimeMillis()}",
                        title = title.ifBlank { "تجربة حرفية تراثية أصيلة" },
                        category = selectedCategory,
                        hostName = hostName.ifBlank { "المعلم الحرفي" },
                        hostTitle = hostTitle.ifBlank { "صاحب مهنة متوارثة" },
                        city = city.ifBlank { "صنعاء القديمة" },
                        locationDescription = locationDesc.ifBlank { "حي الحرفيين التراثي" },
                        pricePerPerson = priceText.toDoubleOrNull() ?: 35.0,
                        durationHours = durationText.toIntOrNull() ?: 4,
                        maxGroupSize = groupSizeText.toIntOrNull() ?: 6,
                        rating = 5.0,
                        reviewCount = 1,
                        description = description.ifBlank { "تجربة غنية بالتعلم الميداني والتفاعل الإنساني المباشر مع أصحاب المهن اليدوية." },
                        requirements = requirements.ifBlank { "ملابس مريحة، كاميرا توثيق، وروح المغامرة." },
                        imageUrl = "https://images.unsplash.com/photo-1565193566173-7a0ee3dbe261?w=800",
                        isFeatured = true
                    )
                    onSubmit(exp)
                    showSuccessDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("submit_new_experience_button"),
                colors = ButtonDefaults.buttonColors(containerColor = DeepBluePrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("إرسال التجربة للاعتماد والنشر", fontWeight = FontWeight.Bold)
            }
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("تم إضافة التجربة بنجاح!", fontWeight = FontWeight.Bold, color = DeepBluePrimary) },
            text = { Text("تم حفظ التجربة في قاعدة بيانات تِجربة بنجاح، وأصبحت متاحة في القوائم الرئيسية.") },
            confirmButton = {
                Button(onClick = { showSuccessDialog = false; onBack() }) {
                    Text("العودة للرئيسية")
                }
            }
        )
    }
}
