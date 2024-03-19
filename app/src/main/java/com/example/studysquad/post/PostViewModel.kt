package com.example.studysquad.post

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage,
    private val firebaseDatabase: FirebaseDatabase
):ViewModel() {
    // Function to upload post data and image
    fun createPost(postContent: String, imageUri: Uri) {
        // Retrieve user information
        val user = firebaseAuth.currentUser
        val userId = user?.uid ?: return // Ensure user is signed in


        val userRef = firebaseDatabase.reference.child("Profiles").child("Users").child(userId)
        userRef.get().addOnSuccessListener { snapshot ->
            val userName = snapshot.child("username").getValue(String::class.java)
            val userProfileImageUrl = snapshot.child("profileImageUrl").getValue(String::class.java)


            // Upload post content to Firebase Realtime Database
            val MypostRef = firebaseDatabase.reference.child("Myposts").child(userId).push()
            val MypostData = PostData(
                userId,
                userName,
                userProfileImageUrl.toString(),
                postContent,
                imageUri.toString(),
                0
            )

            MypostRef.setValue(MypostData)

            val postRef = firebaseDatabase.reference.child("Posts").push()
            val postData = PostData(
                userId,
                userName,
                userProfileImageUrl.toString(),
                postContent,
                imageUri.toString(),
            0
            )
            postRef.setValue(postData)


            // Upload post image to Firebase Storage
            val imageRef = firebaseStorage.reference.child("post_images").child(MypostRef.key!!)
            imageRef.putFile(imageUri)
                .addOnSuccessListener { taskSnapshot ->
                    // Get the download URL of the uploaded image
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        // Update post data in Firebase Realtime Database with image URL
                        MypostRef.child("imageUrl").setValue(uri.toString())
                    }
                }
        }


    }
}