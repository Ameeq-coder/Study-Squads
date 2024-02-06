package com.example.studysquad.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Provides
    @Singleton
    fun firebaseauth():FirebaseAuth=FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun firebaserealtime():FirebaseDatabase=FirebaseDatabase.getInstance()

}