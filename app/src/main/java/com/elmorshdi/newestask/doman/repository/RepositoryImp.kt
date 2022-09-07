package com.elmorshdi.newestask.doman.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.elmorshdi.newestask.data.localdata.dao.Dao
import com.elmorshdi.newestask.data.localdata.model.Post
import com.elmorshdi.newestask.data.repository.RemoteMediator
import com.elmorshdi.newestask.data.repository.Repository
import javax.inject.Inject

class RepositoryImp @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
    private val postRemoteMediator: RemoteMediator,
    private val dao: Dao
) : Repository {


    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPosts(): Pager<Int, Post> {
        return Pager(
            config = PagingConfig(10, enablePlaceholders = true),
            remoteMediator = postRemoteMediator
        ) {
            dao.getAllPosts()
        }
    }

    override suspend fun getPostById(id: Int): Post {
        return dao.getPostById(id = id)
    }

    override suspend fun filterByUser(id: Int): List<Post> {

        return dao.filterByUser(id)
    }


}