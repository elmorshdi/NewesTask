package com.elmorshdi.newestask.di

import android.content.Context
import androidx.room.Room
import com.elmorshdi.newestask.data.localdata.AppDatabase
import com.elmorshdi.newestask.data.localdata.dao.Dao
import com.elmorshdi.newestask.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext app: Context
    ): AppDatabase = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        Constant.DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideDao(appDatabase: AppDatabase): Dao = appDatabase.getDao()

    @Singleton
    @Provides
    fun provideRemoteKeyDao(appDatabase: AppDatabase): RemoteKeyDao = appDatabase.getRemoteKeyDao()
}