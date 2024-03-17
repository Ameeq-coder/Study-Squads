package com.example.studysquad.Chating

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.studysquad.Navigations.Route
import com.example.studysquad.R
import com.example.studysquad.di.NavViewModel

@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel,
    navViewModel: NavViewModel,
    navController: NavController
) {
    val context = LocalContext.current

    val inprogress = chatViewModel.inprocesschats
    if (inprogress.value) {
        CustomProgressBar(progress = 0.5f)
    } else {
        val chats = chatViewModel.chats.value
        val userdata = chatViewModel.userData.value
        val chatUser = chatViewModel.chatuser.value
        val showdialog = remember {
            mutableStateOf(false)
        }
        val onFabClick: () -> Unit = { showdialog.value = true }
        val onDismiss: () -> Unit = { showdialog.value = false }
        val onAddChat: (String, String, String, String) -> Unit =
            { number, userId, userName, userProfileImageUrl ->
                chatViewModel.AddOnChat(userId, userName, userProfileImageUrl)
                showdialog.value = true
            }

        Scaffold(
            floatingActionButton = {
                FAB(
                    showDialog = showdialog.value,
                    onFabClick = onFabClick,
                    OnDismiss = onDismiss,
                    onAddChat = onAddChat,
                    chatViewModel = chatViewModel // Pass the ChatViewModel instance
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 10.dp, top = 10.dp),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        text = "Chats",
                        color = colorResource(id = R.color.lightblue),
                        textAlign = TextAlign.Center
                    )

                    if (chats.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterHorizontally)
                                    .padding(start = 10.dp),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
                                text = "No Chats Available",
                                color = colorResource(id = R.color.lightblue),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(chats) {
                                ChatItem(chatData = it, chatViewModel = chatViewModel, navController = navController)
                            }
                        }
                    }
                }
            }
        )
    }
    DisposableEffect(Unit) {
        chatViewModel.fetchChatData { fetchedChatData ->
            // Update the chat data in the view model
            chatViewModel.chats.value = fetchedChatData
        }

        // Dispose when the composable is removed from the composition
        onDispose {}
    }
}


@Composable
fun ChatItem(chatData: ChatData?, chatViewModel: ChatViewModel, navController: NavController) {
//    val navViewModel: NavViewModel = viewModel()
//    val navigateToMessageScreen by navViewModel.navigateToMessageScreen.observeAsState()

    val context = LocalContext.current

    val otherUser = chatData?.otherUserId
    val otherUserId = otherUser?.uid

    var otherUserName by remember(otherUserId) { mutableStateOf(otherUser?.name ?: "") }
    var otherUserProfileImageUrl by remember(otherUserId) { mutableStateOf(otherUser?.image ?: "") }

    // Update other user's information when chatData changes
    if (chatData != null) {
        otherUserName = otherUser?.name ?: ""
        otherUserProfileImageUrl = otherUser?.image ?: ""
    }



    // Display the user's information in a row
    CommonRow(imageurl = otherUserProfileImageUrl, name = otherUserName) {
      otherUser?.uid.let {
          navigateTo(navController,Route.MessageScreen.createRoute(otherUserId=it?:""))
      }
    }

    DisposableEffect(otherUserId) {
        if (otherUserId != null) {
            // Fetch chat data for this user when the otherUserId changes
            chatViewModel.fetchChatData { fetchedChatData: List<ChatData> ->
                // Update the user's information based on the fetched chat data
                otherUserName = chatData?.otherUserId?.name ?: ""
                otherUserProfileImageUrl = chatData?.otherUserId?.image ?: ""
                Toast.makeText(context, " $otherUserName", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, " $otherUserProfileImageUrl", Toast.LENGTH_SHORT).show()
            }
        }

        // Dispose when the composable is removed from the composition
        onDispose {}
    }
}


@Composable
fun FAB(
    showDialog: Boolean,
    onFabClick: () -> Unit,
    OnDismiss: () -> Unit,
    onAddChat: (String, String, String, String) -> Unit,
    chatViewModel: ChatViewModel // Pass the ChatViewModel instance as a parameter
) {

    val context = LocalContext.current

    val addchatMember = remember {
        mutableStateOf("")
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                OnDismiss.invoke()
                addchatMember.value = ""
            },
            confirmButton = {
                Button(onClick = {
                    chatViewModel.searchUserByUsername(addchatMember.value) { found, userData ->
                        if (found) {
                            // Add chat and update UI
                            onAddChat(
                                addchatMember.value, userData?.uid ?: "",
                                userData?.name ?: "",
                                userData?.image ?: ""
                            ) /// error on this line

                            OnDismiss.invoke()
                            addchatMember.value = ""
                        } else {
                            Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) {
                    Text(text = "Add Chat")
                }
            },
            title = { Text(text = "Add Chat ") },
            text = {
                OutlinedTextField(
                    value = addchatMember.value,
                    onValueChange = { addchatMember.value = it }
                )
            }
        )
    }

    if (!showDialog) { // Display FAB only if showDialog is false
        FloatingActionButton(
            onClick = { onFabClick() },
            contentColor = Color.White,
            content = {
                Image(Icons.Default.Add, contentDescription = "Add")
            },
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp),
            shape = CircleShape,
            modifier = Modifier
                .wrapContentSize()
                .padding(bottom = 40.dp)
        )
    }
}


@Composable
fun CustomProgressBar(progress: Float, barHeight: Dp = 8.dp) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(barHeight)
            .background(color = Color.LightGray, shape = RoundedCornerShape(percent = 50))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(barHeight)
                .background(color = Color(0xFF0096FF), shape = RoundedCornerShape(percent = 50))
        )
    }
}

@Composable
fun CommonRow(imageurl: String?, name: String?, onitemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .height(75.dp)
            .clickable { onitemClick.invoke() }, verticalAlignment = Alignment.CenterVertically
    ) {
        CommonImage(
            data = imageurl,
            modifier = Modifier
                .padding(8.dp)
                .size(50.dp)
                .clip(CircleShape)
                .background(
                    color = colorResource(id = R.color.lightblue)
                )
        )
        Text(
            text = name ?: "",
            fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 4.dp),
            color = colorResource(id = R.color.black),
        )
    }


}

@Composable
fun CommonImage(
    data: String?,
    modifier: Modifier = Modifier.wrapContentSize(),
    contentScale: ContentScale = ContentScale.Crop
) {
    val imagepainter = rememberImagePainter(data = data)
    Image(
        painter = imagepainter,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )


}


@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
//ChatScreen()
}