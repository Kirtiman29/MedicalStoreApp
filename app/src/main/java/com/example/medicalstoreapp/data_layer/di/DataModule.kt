package com.example.medicalstoreapp.data_layer.di

import android.content.Context
import com.example.medicalstoreapp.data_layer.userPrefrence.UserPrefrenceManager
import com.example.medicalstoreapp.repo.repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {


    @Singleton
    @Provides
    fun provideRepo() = repo()


    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context) = context


    @Singleton
    @Provides
    fun provideUserPreferenceManager(
        @ApplicationContext context: Context
    ) = UserPrefrenceManager(
        context = context
    )


}