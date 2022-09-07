package com.elmorshdi.newestask.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.elmorshdi.newestask.data.localdata.model.Post
import com.elmorshdi.newestask.data.network.ApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DataSource @Inject constructor(private val service: ApiService) : PagingSource<Int, Post>() {

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = service.getAllPosts(nextPageNumber, 10)
            val data = response.body()?.posts!!
            LoadResult.Page(
                data = data,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (data.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}