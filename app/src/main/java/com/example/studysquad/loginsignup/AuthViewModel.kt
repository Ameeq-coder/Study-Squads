package com.example.studysquad.loginsignup

import android.health.connect.datatypes.units.Length
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

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

    fun signup(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authResult.value = FirebaseAuthResult.Sucess
                } else {
                    _authResult.value = FirebaseAuthResult.Error(task.exception?.message)
                }
            }
    }
}













