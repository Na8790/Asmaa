package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChatMessage
import com.example.ui.theme.DeepBluePrimary
import com.example.ui.theme.EmeraldGreenSuccess
import com.example.ui.theme.WarmGoldSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    currentHostName: String = "العم ناصر الحميري (خبير النحل)",
    onCallHost: () -> Unit = {}
) {
    var messageInput by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            ChatMessage("1", currentHostName, false, "أهلاً بك يا بني في تِجربة! يسعدني استضافتك غداً في المناحل الجبلية.", "10:14 ص"),
            ChatMessage("2", "أنت", true, "حياك الله عم ناصر، متحمس جداً للتجربة! هل تتوفر بدلات وقائية للأطفال؟", "10:16 ص"),
            ChatMessage("3", currentHostName, false, "نعم بكل تأكيد، لدينا بدلات حماية كاملة مطابقة لجميع المقاسات وبإشراف مباشر.", "10:18 ص")
        )
    }

    val quickReplies = listOf(
        "أين نقطة التجمع بالتحديد؟",
        "هل المكان مناسب للأطفال؟",
        "ما هي الملابس المناسبة للمسار؟",
        "هل توجد شبكة هاتف أو إنترنت في الموقع؟"
    )

    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(WarmGoldSecondary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = DeepBluePrimary)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(currentHostName, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(EmeraldGreenSuccess, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("متصل الآن • موثق في تِجربة", fontSize = 10.sp, color = Color.White.copy(alpha = 0.8f))
                            }
                        }
                    }
                },
                actions = {
                    IconButton(onClick = onCallHost) {
                        Icon(Icons.Default.Call, contentDescription = "اتصال صوتي", tint = Color.White)
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
        ) {
            // Messages List
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(messages) { msg ->
                    ChatBubble(message = msg)
                }
            }

            // Quick Replies Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                quickReplies.forEach { reply ->
                    SuggestionChip(
                        onClick = {
                            messages.add(
                                ChatMessage(
                                    id = System.currentTimeMillis().toString(),
                                    senderName = "أنت",
                                    isFromUser = true,
                                    text = reply,
                                    time = "الآن"
                                )
                            )
                        },
                        label = { Text(reply, fontSize = 11.sp) }
                    )
                }
            }

            // Input Bar
            Surface(
                tonalElevation = 4.dp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = messageInput,
                        onValueChange = { messageInput = it },
                        placeholder = { Text("اكتب رسالتك للمضيف...", fontSize = 13.sp) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("chat_message_input"),
                        shape = RoundedCornerShape(24.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DeepBluePrimary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            if (messageInput.isNotBlank()) {
                                messages.add(
                                    ChatMessage(
                                        id = System.currentTimeMillis().toString(),
                                        senderName = "أنت",
                                        isFromUser = true,
                                        text = messageInput,
                                        time = "الآن"
                                    )
                                )
                                messageInput = ""
                            }
                        },
                        modifier = Modifier
                            .size(46.dp)
                            .background(DeepBluePrimary, CircleShape)
                            .testTag("chat_send_button")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "إرسال", tint = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val isUser = message.isFromUser
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Surface(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isUser) 16.dp else 2.dp,
                bottomEnd = if (isUser) 2.dp else 16.dp
            ),
            color = if (isUser) DeepBluePrimary else MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = message.text,
                    fontSize = 13.sp,
                    color = if (isUser) Color.White else MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = message.time,
                        fontSize = 9.sp,
                        color = if (isUser) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (isUser) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            Icons.Default.DoneAll,
                            contentDescription = "مقروءة",
                            tint = WarmGoldSecondary,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}
