package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.Trip
import com.example.ui.components.RatingStars
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.WarmGoldSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripsScreen(
    trips: List<Trip>,
    onMysteryTripClick: () -> Unit,
    onBookTrip: (trip: Trip) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: All Trips, 1: Mystery Trips

    val displayedTrips = remember(trips, selectedTab) {
        if (selectedTab == 1) trips.filter { it.isSurpriseTrip } else trips
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("الرحلات السياحية والاستكشافية", fontWeight = FontWeight.Bold) },
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
            // Mystery Spotlight Banner
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = DeepBluePrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onMysteryTripClick() }
                        .testTag("trips_mystery_banner")
                ) {
                    Box(modifier = Modifier.fillMaxWidth().height(160.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.banner_mystery_trip_1788638357981),
                            contentDescription = "الرحلات المفاجئة",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.5f))
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(20.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = WarmGoldSecondary
                            ) {
                                Text(
                                    " مغامرة غامضة بالكامل ",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepBluePrimary,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                "الرحلات المفاجئة (Mystery Trips)",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                "حدد ميزانيتك، ودعنا نختار لك وجهة غير متوقعة!",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
            }

            // Tab Switcher
            item {
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = DeepBluePrimary
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("جميع الرحلات المجدولة", fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("الرحلات المفاجئة فقط", fontWeight = FontWeight.Bold) }
                    )
                }
            }

            items(displayedTrips) { trip ->
                TripDetailCard(
                    trip = trip,
                    onBookClick = { onBookTrip(trip) }
                )
            }
        }
    }
}

@Composable
fun TripDetailCard(
    trip: Trip,
    onBookClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(160.dp)) {
                AsyncImage(
                    model = trip.imageUrl,
                    contentDescription = trip.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Surface(
                    shape = RoundedCornerShape(bottomStart = 10.dp),
                    color = if (trip.isSurpriseTrip) WarmGoldSecondary else DeepBluePrimary,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(
                        text = if (trip.isSurpriseTrip) " رحلة استكشافية غامضة " else " ${trip.durationDays} أيام ",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (trip.isSurpriseTrip) DeepBluePrimary else Color.White,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(trip.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DeepBluePrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = WarmGoldSecondary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(trip.city, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(16.dp), tint = DeepBluePrimary)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(trip.guideName, fontSize = 11.sp, maxLines = 1)
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.DirectionsCar, contentDescription = null, modifier = Modifier.size(16.dp), tint = DeepBluePrimary)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(trip.transportationType, fontSize = 11.sp, maxLines = 1)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text("البرنامج والمحطات:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(trip.itinerarySummary, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("السعر الشامل", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("$${trip.price.toInt()}", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = DeepBluePrimary)
                    }

                    Button(
                        onClick = onBookClick,
                        colors = ButtonDefaults.buttonColors(containerColor = DeepBluePrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("حجز الرحلة")
                    }
                }
            }
        }
    }
}
