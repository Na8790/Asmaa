package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.*
import com.example.ui.components.RatingStars
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.EmeraldGreenSuccess
import com.example.ui.theme.WarmGoldSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    experiences: List<Experience>,
    trips: List<Trip>,
    localGuides: List<LocalGuide>,
    rentalCars: List<RentalCar>,
    souvenirs: List<SouvenirItem>,
    userRole: String,
    onExperienceClick: (String) -> Unit,
    onTripsClick: () -> Unit,
    onMysteryTripClick: () -> Unit,
    onAIPlannerClick: () -> Unit,
    onAdminClick: () -> Unit,
    onAddExperienceClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<ExperienceCategory?>(null) }
    var selectedCity by remember { mutableStateOf<String?>(null) }
    var showFilterSheet by remember { mutableStateOf(false) }
    var maxPriceFilter by remember { mutableStateOf(100f) }

    val filteredExperiences = remember(experiences, searchQuery, selectedCategory, selectedCity, maxPriceFilter) {
        experiences.filter { exp ->
            val matchQuery = searchQuery.isBlank() || exp.title.contains(searchQuery, ignoreCase = true) || exp.city.contains(searchQuery, ignoreCase = true) || exp.description.contains(searchQuery, ignoreCase = true)
            val matchCat = selectedCategory == null || exp.category == selectedCategory
            val matchCity = selectedCity == null || exp.city.contains(selectedCity!!, ignoreCase = true)
            val matchPrice = exp.pricePerPerson <= maxPriceFilter
            matchQuery && matchCat && matchCity && matchPrice
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Top Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DeepBluePrimary)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "تِجربة",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = WarmGoldSecondary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = WarmGoldSecondary.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = if (userRole == "ADMIN") "مشرف عام" else if (userRole == "HOST") "صاحب مهنة" else "مستكشف",
                                    fontSize = 11.sp,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "مرحباً بك! اكتشف أصالة المهن وسحر السفر",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (userRole == "ADMIN") {
                            IconButton(
                                onClick = onAdminClick,
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(WarmGoldSecondary, CircleShape)
                                    .testTag("admin_dashboard_icon")
                            ) {
                                Icon(Icons.Default.AdminPanelSettings, contentDescription = "لوحة التحكم", tint = DeepBluePrimary)
                            }
                        }
                        if (userRole == "HOST" || userRole == "ADMIN") {
                            IconButton(
                                onClick = onAddExperienceClick,
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(EmeraldGreenSuccess, CircleShape)
                                    .testTag("add_experience_icon")
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "إضافة تجربة جديدة", tint = Color.White)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Search Box
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("ابحث عن حرفة، نحّال، مزارع، أو مدينة...", fontSize = 13.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "بحث", tint = DeepBluePrimary) },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Close, contentDescription = "مسح")
                                }
                            }
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = WarmGoldSecondary,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("home_search_input")
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = { showFilterSheet = true },
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(WarmGoldSecondary)
                            .testTag("filter_button")
                    ) {
                        Icon(Icons.Default.Tune, contentDescription = "تصفية", tint = DeepBluePrimary)
                    }
                }
            }
        }

        // Hero Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hero_authentic_journey_1788638342855),
                    contentDescription = "بانر التجارب المحلية",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(DeepBluePrimary.copy(alpha = 0.85f), Color.Transparent)
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 20.dp, end = 60.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = WarmGoldSecondary
                    ) {
                        Text(
                            text = " تجارب حية واقعية ",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepBluePrimary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "عِش يوماً حقيقياً مع أصحاب المهن",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "نحّال، مزارع، صياد، صانع فخار، وخبير تحميص البن",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }

        // Category Filter Horizontal Row
        item {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "تصنيفات التجارب المحلية",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = DeepBluePrimary
                    )
                    if (selectedCategory != null) {
                        TextButton(onClick = { selectedCategory = null }) {
                            Text("عرض الكل", fontSize = 12.sp, color = WarmGoldSecondary)
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ExperienceCategory.values().forEach { cat ->
                        val isSelected = selectedCategory == cat
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedCategory = if (isSelected) null else cat
                            },
                            label = { Text(cat.titleAr, fontSize = 12.sp) },
                            leadingIcon = {
                                Icon(
                                    imageVector = if (isSelected) Icons.Filled.Check else Icons.Outlined.Workspaces,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = DeepBluePrimary,
                                selectedLabelColor = Color.White,
                                selectedLeadingIconColor = WarmGoldSecondary
                            )
                        )
                    }
                }
            }
        }

        // Feature Banner: AI Travel Planner & Mystery Trips Cards
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Mystery Trip Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DeepBluePrimary),
                    modifier = Modifier
                        .weight(1f)
                        .height(130.dp)
                        .clickable { onMysteryTripClick() }
                        .testTag("card_mystery_trip")
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Explore, contentDescription = null, tint = WarmGoldSecondary)
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = WarmGoldSecondary.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    "تشويق",
                                    fontSize = 10.sp,
                                    color = WarmGoldSecondary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Column {
                            Text("الرحلات المفاجئة", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                            Text("وجهة سرية تُكشف لاحقاً!", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                        }
                    }
                }

                // AI Travel Assistant Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier
                        .weight(1f)
                        .height(130.dp)
                        .clickable { onAIPlannerClick() }
                        .testTag("card_ai_planner")
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = DeepBluePrimary)
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = DeepBluePrimary.copy(alpha = 0.1f)
                            ) {
                                Text(
                                    "ذكاء اصطناعي",
                                    fontSize = 10.sp,
                                    color = DeepBluePrimary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Column {
                            Text("المستشار الذكي", fontWeight = FontWeight.Bold, color = DeepBluePrimary, fontSize = 14.sp)
                            Text("خطة يومية حسب ميزانيتك", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        // Experiences List
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "التجارب المحلية المتاحة (${filteredExperiences.size})",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = DeepBluePrimary
                )
            }
        }

        if (filteredExperiences.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Outlined.SearchOff, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("لا توجد تجارب مطابقة لمعايير البحث الحالية", fontWeight = FontWeight.SemiBold)
                        TextButton(onClick = {
                            searchQuery = ""
                            selectedCategory = null
                            selectedCity = null
                            maxPriceFilter = 100f
                        }) {
                            Text("إعادة ضبط الفلاتر", color = WarmGoldSecondary)
                        }
                    }
                }
            }
        } else {
            items(filteredExperiences) { exp ->
                ExperienceCard(
                    experience = exp,
                    onClick = { onExperienceClick(exp.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .testTag("experience_card_${exp.id}")
                )
            }
        }

        // Trips Spotlight Section
        item {
            Column(modifier = Modifier.padding(vertical = 12.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "الرحلات السياحية المنظمة",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = DeepBluePrimary
                    )
                    TextButton(onClick = onTripsClick) {
                        Text("استعراض الكل", fontSize = 12.sp, color = WarmGoldSecondary)
                    }
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(trips) { trip ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .width(260.dp)
                                .clickable { onTripsClick() }
                        ) {
                            Column {
                                Box(modifier = Modifier.height(130.dp).fillMaxWidth()) {
                                    AsyncImage(
                                        model = trip.imageUrl,
                                        contentDescription = trip.title,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(bottomStart = 8.dp),
                                        color = if (trip.isSurpriseTrip) WarmGoldSecondary else DeepBluePrimary,
                                        modifier = Modifier.align(Alignment.TopEnd)
                                    ) {
                                        Text(
                                            text = if (trip.isSurpriseTrip) " رحلة مفاجئة " else " ${trip.durationDays} أيام ",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (trip.isSurpriseTrip) DeepBluePrimary else Color.White,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = trip.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = trip.city,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RatingStars(trip.rating, trip.reviewCount)
                                        Text(
                                            text = "$${trip.price.toInt()}",
                                            fontWeight = FontWeight.ExtraBold,
                                            color = DeepBluePrimary,
                                            fontSize = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Local Guides & Transport Section
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    text = "مرشدون محليون معتمدون",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = DeepBluePrimary
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(localGuides) { guide ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier.width(200.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .background(DeepBluePrimary, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(guide.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text(guide.city, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(guide.specialty, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    RatingStars(guide.rating)
                                    Text("$${guide.dailyRate.toInt()}/يوم", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = DeepBluePrimary)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Souvenirs & Artisan Gifts
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    text = "هدايا وتحف حرفية من أصحاب المهن",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = DeepBluePrimary
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(souvenirs) { item ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.width(170.dp)
                        ) {
                            Column {
                                AsyncImage(
                                    model = item.imageUrl,
                                    contentDescription = item.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.height(100.dp).fillMaxWidth()
                                )
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(item.title, fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    Text(item.artisanName, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("$${item.price.toInt()}", fontWeight = FontWeight.Bold, color = DeepBluePrimary, fontSize = 13.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Filter Bottom Sheet
    if (showFilterSheet) {
        ModalBottomSheet(
            onDismissRequest = { showFilterSheet = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text("تصفية التجارب والرحلات", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), color = DeepBluePrimary)
                Spacer(modifier = Modifier.height(16.dp))

                Text("المدينة أو الوجهة:", fontWeight = FontWeight.SemiBold)
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("صنعاء القديمة", "حراز", "وصاب العالي", "المخا", "حضرموت", "إب").forEach { city ->
                        FilterChip(
                            selected = selectedCity == city,
                            onClick = { selectedCity = if (selectedCity == city) null else city },
                            label = { Text(city) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("أقصى سعر للفرد: $${maxPriceFilter.toInt()}", fontWeight = FontWeight.SemiBold)
                Slider(
                    value = maxPriceFilter,
                    onValueChange = { maxPriceFilter = it },
                    valueRange = 20f..150f,
                    colors = SliderDefaults.colors(
                        thumbColor = WarmGoldSecondary,
                        activeTrackColor = DeepBluePrimary
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { showFilterSheet = false },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepBluePrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("تطبيق الفلترة")
                }
            }
        }
    }
}

@Composable
fun ExperienceCard(
    experience: Experience,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                AsyncImage(
                    model = experience.imageUrl,
                    contentDescription = experience.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Category Pill
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = DeepBluePrimary.copy(alpha = 0.9f),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                ) {
                    Text(
                        text = experience.category.titleAr,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                // Price Tag
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = WarmGoldSecondary,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "$${experience.pricePerPerson.toInt()} / فرد",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = DeepBluePrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = experience.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = DeepBluePrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.PersonPin, contentDescription = null, tint = WarmGoldSecondary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${experience.hostName} • ${experience.city}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingStars(experience.rating, experience.reviewCount)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${experience.durationHours} ساعات",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
