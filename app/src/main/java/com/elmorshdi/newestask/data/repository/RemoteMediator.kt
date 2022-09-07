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
    private val database: AppDatabase,
    private val articlesDataSource: ApiService,
) : RemoteMediator<Int, Post>() {
    private val articlesDao = database.getDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
        /*val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)
        return if (System.currentTimeMillis() - articlesDao.lastUpdated() >= cacheTimeout) {
            // Cached data is up-to-date, so there is no need to re-fetch from network.
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network; returning LAUNCH_INITIAL_REFRESH here
            // will also block ArticlesRemoteMediator's APPEND and PREPEND from running until REFRESH
            // succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }*/
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

            val response = articlesDataSource.getAllPosts(loadKey ?: 0, 10)

            if (response.isSuccessful) {
                val articlesResponse = response.body()!!
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        articlesDao.deleteByQuery()
                    }

                    articlesDao.insertAll(articlesResponse.posts)
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