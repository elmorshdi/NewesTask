package com.elmorshdi.newestask.data.localdata

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elmorshdi.newestask.data.localdata.dao.Dao
import com.elmorshdi.newestask.data.localdata.model.Post
import com.elmorshdi.newestask.util.Converters

@Database(entities = [Post::class, RemoteKey::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): Dao
    abstract fun getRemoteKeyDao(): RemoteKeyDao
}