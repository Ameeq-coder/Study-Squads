package com.example.studysquad.post

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImagePickerViewModel @Inject constructor():ViewModel() {
val selectedImageUri=mutableStateOf<Uri?>(null)
}