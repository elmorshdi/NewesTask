package com.elmorshdi.newestask.data.repository


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.elmorshdi.newestask.data.localdata.AppDatabase
import com.elmorshdi.newestask.data.localdata.model.Post
import com.elmorshdi.newestask.data.network.ApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class RemoteMediator @Inject constructor(
    private val db: AppDatabase,
    private val apiService: ApiService,
) : RemoteMediator<Int, Post>() {
    private val dao = db.getDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH

    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Post>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                    lastItem.id
                }
            }

            val response = apiService.getAllPosts(loadKey ?: 0, 10)

            if (response.isSuccessful) {
                val articlesResponse = response.body()!!
                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        dao.deleteAllPosts()
                    }

                    dao.insertAll(articlesResponse.posts)
                }
            }

            MediatorResult.Success(endOfPaginationReached = response.body()!!.posts.last().id!! >= response.body()!!.total!!)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}