package com.example.studysquad.Chating


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
     val firebaseDatabase: FirebaseDatabase,
     val firebaseAuth: FirebaseAuth
) : ViewModel() {

    val inprocesschats = mutableStateOf(false)
    val chats = mutableStateOf<List<ChatData>>(listOf()) // where this variable is accessed
    val userData = mutableStateOf<UserDatas?>(null)
    val chatuser = mutableStateOf<ChatUser?>(null)


    fun AddOnChat( userId: String, userName: String, userProfileImageUrl: String) {
        val currentUserUid = firebaseAuth.currentUser?.uid ?: return

        if (userId.isEmpty() || userId == currentUserUid) {
            // Handle case where user is trying to add themselves
            // Show appropriate message to the user
            Log.d("ChatViewModel", "You cannot add yourself")
            return
        }

        // Check if the user is already added
        val alreadyAdded = chats.value.any { chatData ->
            chatData.otherUserId?.uid == userId
        }
        if (alreadyAdded) {
            // Handle case where user is already added
            // Show appropriate message to the user
            Log.d("ChatViewModel", "User is already added")
            return
        }

        // Perform actions to add chat
        uploadChatInfo(userId, userName, userProfileImageUrl) {
            // Handle any additional actions after chat upload
            fetchChatData { fetchedChatData ->
                chats.value = fetchedChatData
            }
        }
    }

    fun searchUserByUsername(
        username: String,
        onUserFound: (Boolean, UserDatas?) -> Unit
    ) {
        val currentUserUid = firebaseAuth.currentUser?.uid ?: return
        val usersRef = firebaseDatabase.reference.child("Profiles").child("Users")
        val query = usersRef.orderByChild("username").equalTo(username)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (childSnapshot in snapshot.children) {
                        val otherUserId = childSnapshot.key ?: ""
                        if (otherUserId == currentUserUid) {
                            // Handle case where user is trying to add themselves
                            // Show appropriate message to the user
                            Log.d("ChatViewModel", "You cannot add yourself")
                            onUserFound(false, null)
                            return
                        }
                        val otherUserName =
                            childSnapshot.child("username").getValue(String::class.java) ?: ""
                        val otherUserProfileImageUrl =
                            childSnapshot.child("profileImageUrl").getValue(String::class.java)
                                ?: ""

                        val userData =
                            UserDatas(otherUserId, otherUserName, otherUserProfileImageUrl)
                        onUserFound(true, userData)

                        uploadChatInfo(otherUserId, otherUserName, otherUserProfileImageUrl) {
                            // Handle any additional actions after chat upload
                        }
                        // Call fetchChatData function inside the onUserFound callback
                        fetchChatData { chatData ->
                            this@ChatViewModel.chats.value = chatData // Update chats state with fetched chat data
                        }
                        return

                    }
                }
                // User not found
                onUserFound(false, null)
            }

            override fun onCancelled(error: DatabaseError) {
                // Error occurred, treat as user not found
                onUserFound(false, null)
            }
        })
    }


    fun onSendReply(otherUserId: String, message: String) {
        val uid = firebaseAuth.currentUser?.uid
        val time = Calendar.getInstance().time.toString()

        // Create message objects for sender and receiver
        val sentMessage = SendMessage(uid, message, time)
        val receivedMessage = ReceiveMessage(uid!!, message, time)

        // Generate unique message IDs for sender and receiver
        val senderMessageId = firebaseDatabase.reference
            .child("Messages")
            .child(uid)
            .child("Sent")
            .push()
            .key ?: ""

        val receiverMessageId = firebaseDatabase.reference
            .child("Messages")
            .child(otherUserId)
            .child("Received")
            .push()
            .key ?: ""

        // Store the sent message under sender's node
        firebaseDatabase.reference
            .child("Messages")
            .child(uid)
            .child(otherUserId)
            .child("Sent")
            .child(senderMessageId)
            .setValue(sentMessage)

        // Store the received message under receiver's node
        firebaseDatabase.reference
            .child("Messages")
            .child(otherUserId)
            .child(uid)
            .child("Received")
            .child(receiverMessageId)
            .setValue(receivedMessage)
    }


    private fun uploadChatInfo(
        otherUserId: String,
        otherUserName: String,
        otherUserProfileImageUrl: String,
        onChatUploaded: () -> Unit
    ) {
        val user = firebaseAuth.currentUser
        val userId = user?.uid ?: return // Ensure user is signed in

        val userRef = firebaseDatabase.reference.child("Profiles").child("Users").child(userId)
        userRef.get().addOnSuccessListener { snapshot ->
            val userName = snapshot.child("username").getValue(String::class.java) ?: ""
            val userProfileImageUrl =
                snapshot.child("profileImageUrl").getValue(String::class.java) ?: ""
            val currentUserChatRef =
                firebaseDatabase.reference.child("Chats").child(userId).child(otherUserId)
            val otherUserChatRef =
                firebaseDatabase.reference.child("Chats").child(otherUserId).child(userId)

            val currentUserChatData = mapOf(
                "uid" to otherUserId,
                "name" to otherUserName,
                "image" to otherUserProfileImageUrl
            )
            val otherUserChatData = mapOf(
                "uid" to userId,
                "name" to userName,
                "image" to userProfileImageUrl
            )

            currentUserChatRef.setValue(currentUserChatData)
            otherUserChatRef.setValue(otherUserChatData)
                .addOnSuccessListener {
                    onChatUploaded()

                }
                .addOnFailureListener { exception ->
                    // Handle failure
                }
        }
    }
    fun fetchChatData(onDataFetched: (List<ChatData>) -> Unit) {
        val user = firebaseAuth.currentUser
        val userId = user?.uid ?: return // Ensure user is signed in

        val currentUserChatRef = firebaseDatabase.reference.child("Chats").child(userId)
        currentUserChatRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatDataList = mutableListOf<ChatData>()

                // Iterate through each child node representing other users
                snapshot.children.forEach { otherUserSnapshot ->
                    val otherUserId = otherUserSnapshot.key ?: return@forEach // Get the other user's ID

                    // Retrieve chat data for the other user
                    val otherUserName = otherUserSnapshot.child("name").getValue(String::class.java) ?: ""
                    val otherUserProfileImageUrl = otherUserSnapshot.child("image").getValue(String::class.java) ?: ""

                    Log.d("OTHERUSERNAME","$otherUserName")
                    Log.d("OTHERUSERPROFILEIMAGE","$otherUserProfileImageUrl")
                    // Create ChatUser object for the other user
                    val otherUser = ChatUser(uid = otherUserId, name = otherUserName, image = otherUserProfileImageUrl)

                    // Create ChatData object for the other user
                    val chatData = ChatData(otherUserId = otherUser)

                    // Add chat data to the list
                    chatDataList.add(chatData)
                }

                // Pass the list of chat data to the callback
                onDataFetched(chatDataList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle failure
            }
        })
    }

    fun fetchUserData(otherUserId: String, onSuccess: (UserDatas) -> Unit) {
        val usersRef = firebaseDatabase.reference.child("Profiles").child("Users").child(otherUserId)
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val otherUserName = snapshot.child("username").getValue(String::class.java) ?: ""
                val otherUserProfileImageUrl = snapshot.child("profileImageUrl").getValue(String::class.java) ?: ""
                val userData = UserDatas(otherUserId,otherUserName, otherUserProfileImageUrl)
                Log.d("FETCHOTHERUSER","$userData")
                onSuccess(userData)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle failure
            }
        })
    }
    fun fetchSentMessages(otheruserId: String, onDataFetched: (List<SendMessage>) -> Unit) {
        val uid = firebaseAuth.currentUser?.uid
        val sentMessagesRef = firebaseDatabase.reference
            .child("Messages")
            .child(uid?:"")
            .child(otheruserId)
            .child("Sent")

        sentMessagesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val sentMessagesList = mutableListOf<SendMessage>()

                snapshot.children.forEach { messageSnapshot ->
                    val message = messageSnapshot.getValue(SendMessage::class.java)
                    message?.let {
                        sentMessagesList.add(it)
                    }
                }

                onDataFetched(sentMessagesList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle failure
            }
        })
    }

    // Function to fetch received messages
    fun fetchReceivedMessages(otheruserId: String, onDataFetched: (List<ReceiveMessage>) -> Unit) {
        val uid = firebaseAuth.currentUser?.uid
        val receivedMessagesRef = firebaseDatabase.reference
            .child("Messages")
            .child(uid?:"")
            .child(otheruserId)
            .child("Received")

        receivedMessagesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val receivedMessagesList = mutableListOf<ReceiveMessage>()

                snapshot.children.forEach { messageSnapshot ->
                    val message = messageSnapshot.getValue(ReceiveMessage::class.java)
                    message?.let {
                        receivedMessagesList.add(it)
                    }
                }

                onDataFetched(receivedMessagesList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle failure
            }
        })
    }
}





