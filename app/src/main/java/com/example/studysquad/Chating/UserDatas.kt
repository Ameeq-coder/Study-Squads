package com.example.studysquad.Chating

data class UserDatas(
    var uid:String?="",
    var name:String?="",
    var image:String?=""
){
    fun tomap()= mapOf(
        "uid" to uid,
        "username" to name,
        "profileImageUrl" to image
    )




}