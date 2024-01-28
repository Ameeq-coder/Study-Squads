package com.example.studysquad.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton

@Module
@InstallIn(Singleton::class)
object AppModules {

    @Provides
    @Singleton
    fun firebaseauth():FirebaseAuth=FirebaseAuth.getInstance()

}