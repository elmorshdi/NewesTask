package com.elmorshdi.newestask.data.network.model

import com.elmorshdi.newestask.data.localdata.model.Post

data class Dto(
    val limit: Int?,
    val posts: List<Post>,
    val skip: Int?,
    val total: Int?
)