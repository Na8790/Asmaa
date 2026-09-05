package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import com.example.ui.theme.EmeraldGreenSuccess
import com.example.ui.theme.WarmGoldSecondary

@Composable
fun BookingConfirmationDialog(
    itemTitle: String,
    pricePerPerson: Double,
    guestCount: Int,
    date: String,
    timeSlot: String,
    onConfirmBooking: (paymentMethod: String, totalPrice: Double) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedPaymentMethod by remember { mutableStateOf("محفظة إلكترونية محلية (فلوسك / كاش)") }
    var couponCode by remember { mutableStateOf("") }
    var discountPercent by remember { mutableStateOf(0) }
    var isProcessing by remember { mutableStateOf(false) }
    var paymentStatus by remember { mutableStateOf<String?>(null) } // null, "SUCCESS", "FAILED"

    val subtotal = pricePerPerson * guestCount
    val discountAmount = subtotal * (discountPercent / 100.0)
    val platformFee = subtotal * 0.05 // 5% service fee
    val finalTotal = (subtotal - discountAmount + platformFee).coerceAtLeast(0.0)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = DeepBluePrimary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("تأكيد الحجز والدفع الرقمي", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DeepBluePrimary)
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(itemTitle, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("الموعد: $date ($timeSlot)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("عدد الأفراد: $guestCount", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                HorizontalDivider()

                Text("اختر وسيلة الدفع (طبقة Payment Gateway المعتمدة):", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                listOf(
                    "محفظة إلكترونية محلية (فلوسك / كاش)",
                    "بطاقة مدى / فيزا وماستركارد (Stripe Gateway)",
                    "Apple Pay السريع",
                    "الدفع عند الوصول لصاحب التجربة (نقداً)"
                ).forEach { method ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedPaymentMethod == method,
                            onClick = { selectedPaymentMethod = method }
                        )
                        Text(method, fontSize = 12.sp)
                    }
                }

                HorizontalDivider()

                // Coupon Code Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = couponCode,
                        onValueChange = { couponCode = it },
                        placeholder = { Text("كود خصم (جرب: TAJRIBAH10)", fontSize = 11.sp) },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Button(
                        onClick = {
                            if (couponCode.trim().equals("TAJRIBAH10", ignoreCase = true)) {
                                discountPercent = 10
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = WarmGoldSecondary)
                    ) {
                        Text("تطبيق", fontSize = 11.sp, color = DeepBluePrimary, fontWeight = FontWeight.Bold)
                    }
                }

                if (discountPercent > 0) {
                    Text("تم تطبيق خصم 10% بنجاح!", color = EmeraldGreenSuccess, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("السعر الأساسي:", fontSize = 12.sp)
                            Text("$${subtotal.toInt()}", fontSize = 12.sp)
                        }
                        if (discountPercent > 0) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("الخصم:", fontSize = 12.sp, color = EmeraldGreenSuccess)
                                Text("-$${discountAmount.toInt()}", fontSize = 12.sp, color = EmeraldGreenSuccess)
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("رسوم تأمين وخدمة المنصة (5%):", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("$${platformFee.toInt()}", fontSize = 11.sp)
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("المجموع الإجمالي:", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DeepBluePrimary)
                            Text("$${finalTotal.toInt()}", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = DeepBluePrimary)
                        }
                    }
                }

                Text(
                    "سياسة الاسترجاع: يحق للمستخدم إلغاء الحجز واسترداد كامل المبلغ حتى 48 ساعة قبل موعد الزيارة.",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirmBooking(selectedPaymentMethod, finalTotal)
                },
                colors = ButtonDefaults.buttonColors(containerColor = DeepBluePrimary),
                modifier = Modifier.testTag("confirm_final_booking_button")
            ) {
                Text("إتمام الحجز وإصدار الإيصال")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إلغاء") }
        },
        shape = RoundedCornerShape(18.dp)
    )
}
