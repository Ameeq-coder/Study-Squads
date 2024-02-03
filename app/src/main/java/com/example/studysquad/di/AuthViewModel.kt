package com.example.studysquad.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject


class AuthViewModel @Inject constructor() :ViewModel() {

    private val _navigateToSignUp = MutableLiveData<Boolean>()
    val navigateToSignUp: LiveData<Boolean> = _navigateToSignUp

    fun navigateToSignUp() {
        _navigateToSignUp.value = true
    }

    fun onNavigateToSignUpComplete() {
        _navigateToSignUp.value = false
    }


}