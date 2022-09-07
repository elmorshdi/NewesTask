package com.elmorshdi.newestask.data.repository

import androidx.paging.Pager
import com.elmorshdi.newestask.data.localdata.model.Post

interface Repository {
    suspend fun getPosts(): Pager<Int, Post>
    suspend fun getPostById(id: Int): Post
}