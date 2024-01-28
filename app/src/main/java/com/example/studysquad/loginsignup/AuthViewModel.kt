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
    fun login(email:String,password:String){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task->
            if (task.isSuccessful){
                authresults.value=FirebaseAuthResult.Sucess
            }else{
                authresults.value=FirebaseAuthResult.Error(task.exception?.message)
            }
        }
    }
    fun signup(email:String,password:String){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task->
            if (task.isSuccessful){
                authresults.value=FirebaseAuthResult.Sucess
            }else{
                authresults.value=FirebaseAuthResult.Error(task.exception?.message)
            }
        }
    }
}