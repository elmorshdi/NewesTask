package com.elmorshdi.newestask.data.network

import com.elmorshdi.newestask.data.network.model.Dto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("posts")
    suspend fun getAllPosts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Response<Dto>
}