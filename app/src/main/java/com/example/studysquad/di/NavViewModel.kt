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

    private val _navigateToMessageScreen = MutableLiveData<String?>()
    val navigateToMessageScreen: LiveData<String?> = _navigateToMessageScreen



    var otherUserId: String? = null
    var otherUserName: String? = null
    var otherUserProfileImageUrl: String? = null

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
    fun navigateToMessageScreen(uid: String?) {
        _navigateToMessageScreen.value = uid // Pass the uid to the LiveData
    }

    fun onNavigateToMessageScreen() {
        _navigateToMessageScreen.value = null // Clear the value
    }


}