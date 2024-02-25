package com.example.studysquad.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject


class NavViewModel @Inject constructor() :ViewModel() {

    private val _navigateToSignUp = MutableLiveData<Boolean>()
    val navigateToSignUp: LiveData<Boolean> = _navigateToSignUp

    private val _navigateToCreateProfile = MutableLiveData<Boolean>()
    val navigateToCreateProfile: LiveData<Boolean> = _navigateToCreateProfile

    private val _navigateToPostScreen=MutableLiveData<Boolean>()
    val navigateToPostScreen:LiveData<Boolean> = _navigateToPostScreen

    fun navigateToSignUp() {
        _navigateToSignUp.value = true
    }

    fun onNavigateToSignUpComplete() {
        _navigateToSignUp.value = false
    }
    fun navigateToCreateProfile() {
        _navigateToCreateProfile.value = true
    }

    fun onNavigateToCreateProfileComplete() {
        _navigateToCreateProfile.value = false
    }

    fun navigateToPostScreen(){
        _navigateToPostScreen.value=true
    }
    fun OnnavigateToPostScreen(){
        _navigateToPostScreen.value=false
    }

}