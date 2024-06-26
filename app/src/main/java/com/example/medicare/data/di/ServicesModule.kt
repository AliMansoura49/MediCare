package com.example.medicare.data.di

import com.example.medicare.data.services.AccountService
import com.example.medicare.data.services.NotificationService
import com.example.medicare.data.services.StorageService
import com.example.medicare.data.services.impl.AccountServiceImpl
import com.example.medicare.data.services.impl.NotificationServiceImpl
import com.example.medicare.data.services.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServicesModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
    @Binds
    abstract fun provideNotificationsService(impl: NotificationServiceImpl) : NotificationService
}