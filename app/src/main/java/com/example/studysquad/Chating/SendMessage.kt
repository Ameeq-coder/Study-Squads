package com.example.studysquad.Chating

data class SendMessage(
    var sendBy:String?="",
    var message:String?="",
    val timestamp: String?=""
)

data class ReceiveMessage(
    var receiveBy:String?="",
    var message: String?="",
    var timestamp: String?=""
)
