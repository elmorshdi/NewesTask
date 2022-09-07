package com.elmorshdi.newestask.data.localdata.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elmorshdi.newestask.data.localdata.model.Post
import com.elmorshdi.newestask.util.Constant.POST_TABLE_NAME

@Dao
interface Dao {
    @Query("SELECT * FROM $POST_TABLE_NAME ORDER BY id ")
    fun getAllPosts(): PagingSource<Int, Post>


    @Query("SELECT * FROM $POST_TABLE_NAME WHERE id=:id LIMIT 1")
    fun getPostById(id: Int): Post

    @Query("SELECT * FROM $POST_TABLE_NAME WHERE userId  LIKE :id")
    fun filterByUser(id: Int): List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<Post>)

    @Query("DELETE FROM $POST_TABLE_NAME")
    suspend fun deleteAllPosts()


}