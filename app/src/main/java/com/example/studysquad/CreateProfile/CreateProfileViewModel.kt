package com.example.studysquad.CreateProfile

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CreateProfileViewModel @Inject constructor(
private val firebaseAuth: FirebaseAuth,
private val firebaseStorage: FirebaseStorage,
private val firebaseDatabase: FirebaseDatabase
):ViewModel(){

    fun uploadUserProfile(username: String, imageUri: Uri,) {
        val user = Firebase.auth.currentUser
        val userId = user?.uid ?: return // Ensure user is signed in

        // Step 1: Upload profile image to Firebase Storage
        val imageRef = firebaseStorage.getReference().child("profile_images/$userId.jpg")
        imageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                // Step 2: Retrieve download URL of the uploaded image
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    // Step 3: Store user profile data in Firebase Realtime Database
                    val userData = mapOf(
                        "uid" to userId,
                        "username" to username,
                        "profileImageUrl" to downloadUrl.toString()
                    )

                    // Store the user data under the "Users" node with the user's UID
                    firebaseDatabase.getReference("Profiles").child("Users").child(userId).setValue(userData)
                        .addOnSuccessListener {
                            // Successfully uploaded user profile data
                        }
                        .addOnFailureListener { e ->
                            // Handle failure to upload user profile data
                        }
                }
            }
            .addOnFailureListener { e ->
                // Handle failure to upload profile image
            }

}


}