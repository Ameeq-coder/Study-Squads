package com.example.studysquad.loginsignup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : ViewModel() {
    private lateinit var databaseReference: DatabaseReference

    private val _authResult = MutableLiveData<FirebaseAuthResult>()
    val authResult: LiveData<FirebaseAuthResult> = _authResult

    fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authResult.value = FirebaseAuthResult.Sucess
                    callback(true)
                } else {
                    _authResult.value = FirebaseAuthResult.Error(task.exception?.message)
                    callback(false)
                }
            }
    }

    fun signup(email: String, password: String,conpass: String, callback: (Boolean) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authResult.value = FirebaseAuthResult.Sucess
                    RealtimeDatabase(email,password,conpass)
                    callback(true)
                } else {
                    _authResult.value = FirebaseAuthResult.Error(task.exception?.message)
                    callback(false)
                }
            }
    }

    fun RealtimeDatabase(email: String, password: String, conpass: String) {
        val currentUser = firebaseAuth.currentUser
        val userId = currentUser?.uid
        if (userId != null) {
            databaseReference = firebaseDatabase.getReference("Users")
            val userDetailsMap = mutableMapOf<String, Any>(
                "userId" to userId,
                "email" to email,
                "pass" to password,
                "conpass" to conpass
            )
            databaseReference.child(userId).setValue(userDetailsMap)
        }
    }

}









