package com.elmorshdi.newestask.di

import com.elmorshdi.newestask.data.localdata.AppDatabase
import com.elmorshdi.newestask.data.network.ApiService
import com.elmorshdi.newestask.data.repository.RemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideRemoteMediator(
        database: AppDatabase,
        apiService: ApiService
    ): RemoteMediator =
        RemoteMediator(
            database,
            apiService
        )


}