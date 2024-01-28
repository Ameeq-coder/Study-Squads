package com.example.studysquad.loginsignup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) :ViewModel(){
 private val authresults= MutableLiveData<FirebaseAuthResult>()
 val authResult:LiveData<FirebaseAuthResult> = authresults


}