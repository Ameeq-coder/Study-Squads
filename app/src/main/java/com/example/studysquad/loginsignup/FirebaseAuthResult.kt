package com.example.studysquad.loginsignup

sealed class FirebaseAuthResult {
object Sucess: FirebaseAuthResult()
data class Error(val message:String?):FirebaseAuthResult()
}