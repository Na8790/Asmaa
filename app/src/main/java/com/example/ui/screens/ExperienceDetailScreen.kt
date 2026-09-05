package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.Experience
import com.example.data.model.Review
import com.example.ui.components.RatingStars
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.EmeraldGreenSuccess
import com.example.ui.theme.WarmGoldSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExperienceDetailScreen(
    experience: Experience?,
    reviews: List<Review>,
    onBack: () -> Unit,
    onStartChatWithHost: (hostName: String) -> Unit,
    onBookClick: (experience: Experience, guests: Int, date: String, slot: String) -> Unit,
    onAddReview: (rating: Int, comment: String) -> Unit
) {
    if (experience == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("جاري تحميل تفاصيل التجربة...")
        }
        return
    }

    var guestsCount by remember { mutableStateOf(2) }
    var selectedDate by remember { mutableStateOf("غداً، 10:00 صباحاً") }
    var showReviewDialog by remember { mutableStateOf(false) }
    var reviewRating by remember { mutableStateOf(5) }
    var reviewText by remember { mutableStateOf("") }
    var isFavorite by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            Surface(
                tonalElevation = 8.dp,
                shadowElevation = 16.dp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "المجموع التقديري",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "$${(experience.pricePerPerson * guestsCount).toInt()}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = DeepBluePrimary
                        )
                        Text(
                            text = "($guestsCount أفراد)",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Button(
                        onClick = {
                            onBookClick(experience, guestsCount, selectedDate, "الفترة الصباحية")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DeepBluePrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .testTag("book_now_button")
                    ) {
                        Icon(Icons.Default.ConfirmationNumber, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("تأكيد الحجز الآن", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header Image & Controls
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                ) {
                    AsyncImage(
                        model = experience.imageUrl,
                        contentDescription = experience.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Top Bar Back and Favorite
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .windowInsetsPadding(WindowInsets.statusBars)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier
                                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                                .testTag("detail_back_button")
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "رجوع", tint = Color.White)
                        }

                        IconButton(
                            onClick = { isFavorite = !isFavorite },
                            modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape)
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "المفضلة",
                                tint = if (isFavorite) Color.Red else Color.White
                            )
                        }
                    }
                }
            }

            // Title & Core Metadata
            item {
                Column(modifier = Modifier.padding(20.dp)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = WarmGoldSecondary.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = " ${experience.category.titleAr} ",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepBluePrimary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = experience.title,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = DeepBluePrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = WarmGoldSecondary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${experience.city} (${experience.locationDescription})",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        RatingStars(experience.rating, experience.reviewCount)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Host Card with Direct Chat Button
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(DeepBluePrimary, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = WarmGoldSecondary, modifier = Modifier.size(28.dp))
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = experience.hostName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = DeepBluePrimary
                                )
                                Text(
                                    text = experience.hostTitle,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            IconButton(
                                onClick = { onStartChatWithHost(experience.hostName) },
                                modifier = Modifier
                                    .background(DeepBluePrimary.copy(alpha = 0.1f), RoundedCornerShape(10.dp))
                                    .testTag("chat_with_host_button")
                            ) {
                                Icon(Icons.Default.Chat, contentDescription = "تواصل مع صاحب التجربة", tint = DeepBluePrimary)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Key Badges (Duration, Group, Time)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        KeyBadge(
                            icon = Icons.Default.Timer,
                            title = "المدة",
                            subtitle = "${experience.durationHours} ساعات",
                            modifier = Modifier.weight(1f)
                        )
                        KeyBadge(
                            icon = Icons.Default.Groups,
                            title = "الحد الأقصى",
                            subtitle = "${experience.maxGroupSize} أفراد",
                            modifier = Modifier.weight(1f)
                        )
                        KeyBadge(
                            icon = Icons.Default.EventAvailable,
                            title = "المواعيد",
                            subtitle = "متاحة يومياً",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Description
                    Text(
                        text = "عن التجربة",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = DeepBluePrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = experience.description,
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Requirements
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Checklist, contentDescription = null, tint = EmeraldGreenSuccess)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("ما الذي تحتاجه للتجربة؟", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(experience.requirements, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Map & Distance Card (Requirement 8)
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
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Map, contentDescription = null, tint = DeepBluePrimary)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("الموقع والمسافة", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = EmeraldGreenSuccess.copy(alpha = 0.15f)
                                ) {
                                    Text(" يبعد 8.4 كم عنك ", fontSize = 11.sp, color = EmeraldGreenSuccess, fontWeight = FontWeight.Bold, modifier = Modifier.padding(4.dp))
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("الموقع: ${experience.locationDescription}، ${experience.city}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("زمن الوصول التقديري بالسيارة: 22 دقيقة", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Guests Selector
                    Text("عدد الأفراد:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconButton(
                            onClick = { if (guestsCount > 1) guestsCount-- },
                            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "إنقاص")
                        }
                        Text(
                            text = "$guestsCount",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = { if (guestsCount < experience.maxGroupSize) guestsCount++ },
                            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "زيادة")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Reviews Section (Requirement 9)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "التقييمات والآراء (${reviews.size})",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = DeepBluePrimary
                        )
                        TextButton(onClick = { showReviewDialog = true }) {
                            Icon(Icons.Default.RateReview, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("إضافة تقييم", fontSize = 12.sp, color = WarmGoldSecondary)
                        }
                    }
                }
            }

            items(reviews) { review ->
                ReviewItemCard(
                    review = review,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }
        }
    }

    if (showReviewDialog) {
        AlertDialog(
            onDismissRequest = { showReviewDialog = false },
            title = { Text("تقييم تجربة: ${experience.title}", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
            text = {
                Column {
                    Text("اختر التقييم بالنجوم:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        (1..5).forEach { star ->
                            IconButton(onClick = { reviewRating = star }) {
                                Icon(
                                    imageVector = if (star <= reviewRating) Icons.Filled.Star else Icons.Outlined.StarBorder,
                                    contentDescription = null,
                                    tint = WarmGoldSecondary
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = reviewText,
                        onValueChange = { reviewText = it },
                        label = { Text("اكتب تعليقك أو نصيحتك للمسافرين") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (reviewText.isNotBlank()) {
                            onAddReview(reviewRating, reviewText)
                            showReviewDialog = false
                            reviewText = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepBluePrimary)
                ) {
                    Text("نشر التقييم")
                }
            },
            dismissButton = {
                TextButton(onClick = { showReviewDialog = false }) { Text("إلغاء") }
            }
        )
    }
}

@Composable
fun KeyBadge(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = DeepBluePrimary, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = subtitle, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DeepBluePrimary)
        }
    }
}

@Composable
fun ReviewItemCard(review: Review, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(review.userName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                RatingStars(review.rating.toDouble())
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(review.comment, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(6.dp))
            Text(review.date, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

            if (review.hostReply != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("رد صاحب التجربة:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DeepBluePrimary)
                        Text(review.hostReply, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}
