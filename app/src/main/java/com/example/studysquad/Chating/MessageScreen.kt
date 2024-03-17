package com.example.studysquad.Chating

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studysquad.R

@Composable
fun MessageScreen(
    navController: NavController,
    otherUserId: String,
    chatViewModel: ChatViewModel
) {
    val context = LocalContext.current

    var userName by remember { mutableStateOf("") }
    var userProfileImageUrl by remember { mutableStateOf("") }
    var receivedMessage by remember { mutableStateOf<List<ReceiveMessage>>(emptyList()) }
    var SentMessage by remember { mutableStateOf<List<SendMessage>>(emptyList()) }


    val allMessages = mutableListOf<Any>()
    allMessages.addAll(SentMessage)
    allMessages.addAll(receivedMessage)

    LaunchedEffect(otherUserId) {
        chatViewModel.fetchUserData(otherUserId) { userData ->
            userName = userData.name ?: ""
            userProfileImageUrl = userData.image ?: ""

        }
        chatViewModel.fetchReceivedMessages(otherUserId) { messages ->
            receivedMessage = messages
        }
        chatViewModel.fetchSentMessages(otherUserId) { messages ->
            SentMessage = messages
        }

    }

    var reply by rememberSaveable { mutableStateOf("") }
    val onSendReply = {
        chatViewModel.onSendReply(otherUserId, reply)
        reply = ""
    }
    Scaffold(
        topBar = {
            // ChatHeader goes here
            ChatHeader(name = userName, imageUrl = userProfileImageUrl) {}
        },
        bottomBar = {
            // ReplyBox goes here
            ReplyBox(reply = reply, onReplyChange = { reply = it }, onSendReply = onSendReply)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                LazyColumn {
                    items(allMessages) { message ->
                        when (message) {
                            is SendMessage -> RecMessageItem(message = message)
                            is ReceiveMessage -> SentMessageItem(message = message)
                        }
                    }
                }
            }
            }
        }
    }

@Composable
fun ChatHeader(name: String, imageUrl: String, onBackClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Rounded.ArrowBack, contentDescription = null,
            Modifier
                .clickable {
                    onBackClicked.invoke()
                }
                .padding(8.dp)
        )
        CommonImage(
            data = imageUrl,
            modifier = Modifier
                .padding(8.dp)
                .size(50.dp)
                .clip(CircleShape)
        )
        Text(text = name, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 4.dp))
    }
}

@Composable
fun ReplyBox(reply: String, onReplyChange: (String) -> Unit, onSendReply: () -> Unit) {
    val customColors = OutlinedTextFieldDefaults.colors(
        unfocusedTextColor = colorResource(id = R.color.black),
        focusedTextColor = colorResource(id = R.color.black),
        cursorColor = colorResource(id = R.color.lightblue), // Change the cursor color
        focusedBorderColor = Color.Gray, // Change the color when the field is focused
        unfocusedBorderColor = colorResource(id = R.color.lightblue), // Change the color when the field is not focused , // Change the text color
    )
    val context = LocalContext.current

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = reply,
                onValueChange = onReplyChange,
                colors = customColors,
                label = { Text(text = "Send Message", color = Color.Black) },
            )
          IconButton(onClick = {
              if(reply.isEmpty()){
                  Toast.makeText(context,"Enter Message To Send",Toast.LENGTH_SHORT).show()
              }else{
                  onSendReply()
              }
          }) {
              Icon(
                  imageVector = Icons.Default.Send,
                  contentDescription = "Send"
              )
          }
        }
    }
}


@Composable
fun SentMessageItem(message: ReceiveMessage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = message.message ?: "",
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(color = colorResource(id = R.color.second_color))
                .padding(12.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RecMessageItem(message: SendMessage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = message.message ?: "",
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(color = colorResource(id = R.color.first_color))
                .padding(12.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}