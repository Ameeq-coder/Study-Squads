package com.example.studysquad.Chating

data class ChatData(
    val chatId: String? = "",
    val userId: ChatUser = ChatUser(),
    val otherUserId: ChatUser = ChatUser()
) {
    // Required no-argument constructor for Firebase deserialization
    constructor() : this("", ChatUser(), ChatUser())
}

data class ChatUser(
    val uid: String = "",
    val name: String = "",
    val image: String = ""
) {
    // Required no-argument constructor for Firebase deserialization
    constructor() : this("", "", "")
}




