package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Booking
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.EmeraldGreenSuccess
import com.example.ui.theme.WarmGoldSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingsScreen(
    bookings: List<Booking>,
    onCancelBooking: (bookingId: String) -> Unit,
    onExploreClick: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("ALL") } // ALL, CONFIRMED, COMPLETED, CANCELLED
    var bookingToCancel by remember { mutableStateOf<Booking?>(null) }

    val filteredBookings = remember(bookings, selectedFilter) {
        if (selectedFilter == "ALL") bookings else bookings.filter { it.status == selectedFilter }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("سجل الحجوزات والرحلات", fontWeight = FontWeight.Bold) },
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
        ) {
            // Status Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedFilter == "ALL",
                    onClick = { selectedFilter = "ALL" },
                    label = { Text("الكل (${bookings.size})") }
                )
                FilterChip(
                    selected = selectedFilter == "CONFIRMED",
                    onClick = { selectedFilter = "CONFIRMED" },
                    label = { Text("مؤكدة") }
                )
                FilterChip(
                    selected = selectedFilter == "CANCELLED",
                    onClick = { selectedFilter = "CANCELLED" },
                    label = { Text("ملغية") }
                )
            }

            if (filteredBookings.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Outlined.EventNote,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "لا توجد حجوزات في هذا القسم حالياً",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "استكشف التجارب المحلية الفريدة واحجز مغامرتك القادمة الآن!",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onExploreClick,
                            colors = ButtonDefaults.buttonColors(containerColor = DeepBluePrimary)
                        ) {
                            Text("استعراض التجارب")
                        }
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredBookings) { booking ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = DeepBluePrimary.copy(alpha = 0.1f)
                                    ) {
                                        Text(
                                            text = booking.bookingReference,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = DeepBluePrimary,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (booking.status == "CONFIRMED") EmeraldGreenSuccess.copy(alpha = 0.15f) else Color.Red.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = if (booking.status == "CONFIRMED") "مؤكد" else "ملغي",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (booking.status == "CONFIRMED") EmeraldGreenSuccess else Color.Red,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = booking.itemTitle,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = DeepBluePrimary
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("الموعد: ${booking.date}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("الأفراد: ${booking.guestCount}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("طريقة الدفع: ${booking.paymentMethod}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("الإجمالي: $${booking.totalPrice.toInt()}", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = DeepBluePrimary)
                                }

                                if (booking.status == "CONFIRMED") {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    HorizontalDivider()
                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            "سياسة الإلغاء: استرداد كامل قبل 48 ساعة",
                                            fontSize = 10.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        OutlinedButton(
                                            onClick = { bookingToCancel = booking },
                                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                                        ) {
                                            Text("إلغاء الحجز", fontSize = 11.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (bookingToCancel != null) {
        AlertDialog(
            onDismissRequest = { bookingToCancel = null },
            title = { Text("تأكيد إلغاء الحجز", fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "هل أنت متأكد من رغبتك في إلغاء الحجز رقم (${bookingToCancel?.bookingReference})؟\n\nوفق سياسة الإلغاء المعتمدة، سيتم استرداد المبلغ بالكامل إلى محفظتك الإلكترونية لحماية التزامات الشريك والعميل."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        bookingToCancel?.let { onCancelBooking(it.id) }
                        bookingToCancel = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("نعم، إلغاء الحجز")
                }
            },
            dismissButton = {
                TextButton(onClick = { bookingToCancel = null }) { Text("تراجع") }
            }
        )
    }
}
