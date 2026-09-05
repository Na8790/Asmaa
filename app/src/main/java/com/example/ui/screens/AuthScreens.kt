package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.DeveloperFooter
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.WarmGoldSecondary

@Composable
fun AuthScreen(
    onAuthSuccess: (userRole: String) -> Unit
) {
    var isRegisterTab by remember { mutableStateOf(false) }
    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var roleSelection by remember { mutableStateOf("EXPLORER") } // EXPLORER, HOST, ADMIN
    var showOtpDialog by remember { mutableStateOf(false) }
    var otpCode by remember { mutableStateOf("") }
    var feedbackMessage by remember { mutableStateOf<String?>(null) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "منصة تِجربة",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = DeepBluePrimary
        )
        Text(
            text = "بوابتك للتجارب المحلية الأصيلة والسفر الذكي",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Role Selector Pill
        Text(
            text = "اختر نوع الحساب:",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = roleSelection == "EXPLORER",
                onClick = { roleSelection = "EXPLORER" },
                label = { Text("مستكشف / سائح") },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                selected = roleSelection == "HOST",
                onClick = { roleSelection = "HOST" },
                label = { Text("صاحب تجربة / مهنة") },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                selected = roleSelection == "ADMIN",
                onClick = { roleSelection = "ADMIN" },
                label = { Text("مشرف عام") },
                modifier = Modifier.weight(0.9f)
            )
        }

        // Tab Selector
        TabRow(
            selectedTabIndex = if (isRegisterTab) 1 else 0,
            containerColor = Color.Transparent,
            contentColor = DeepBluePrimary
        ) {
            Tab(
                selected = !isRegisterTab,
                onClick = { isRegisterTab = false },
                text = { Text("تسجيل الدخول", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = isRegisterTab,
                onClick = { isRegisterTab = true },
                text = { Text("إنشاء حساب جديد", fontWeight = FontWeight.Bold) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (isRegisterTab) {
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("الاسم الكامل") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("fullname_input")
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        OutlinedTextField(
            value = emailOrPhone,
            onValueChange = { emailOrPhone = it },
            label = { Text("البريد الإلكتروني أو رقم الهاتف") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("email_phone_input")
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("كلمة المرور") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("password_input")
        )

        if (feedbackMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = feedbackMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (isRegisterTab) {
                    showOtpDialog = true
                } else {
                    onAuthSuccess(roleSelection)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("auth_submit_button"),
            colors = ButtonDefaults.buttonColors(containerColor = DeepBluePrimary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = if (isRegisterTab) "إرسال رمز التحقق (OTP)" else "تسجيل الدخول",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // OTP Direct Shortcut Button
        OutlinedButton(
            onClick = { showOtpDialog = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Sms, contentDescription = null, tint = WarmGoldSecondary)
            Spacer(modifier = Modifier.width(8.dp))
            Text("تسجيل الدخول برمز التحقق السريع (OTP)")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(
                text = " أو المتابعة عبر ",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            HorizontalDivider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { onAuthSuccess(roleSelection) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Google", fontWeight = FontWeight.SemiBold)
            }
            OutlinedButton(
                onClick = { onAuthSuccess(roleSelection) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Apple", fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Developer footer requirement (login & register screens)
        DeveloperFooter()
    }

    if (showOtpDialog) {
        AlertDialog(
            onDismissRequest = { showOtpDialog = false },
            title = {
                Text("التحقق عبر رمز الهاتف (OTP)", fontWeight = FontWeight.Bold, color = DeepBluePrimary)
            },
            text = {
                Column {
                    Text(
                        "تم إرسال رمز تحقق مؤقت إلى رقمك. الرمز التجريبي للاختبار: 1234",
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = otpCode,
                        onValueChange = { if (it.length <= 4) otpCode = it },
                        label = { Text("رمز التحقق (4 أرقام)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("otp_code_input")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showOtpDialog = false
                        onAuthSuccess(roleSelection)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepBluePrimary)
                ) {
                    Text("تأكيد ودخول")
                }
            },
            dismissButton = {
                TextButton(onClick = { showOtpDialog = false }) {
                    Text("إلغاء")
                }
            }
        )
    }
}
