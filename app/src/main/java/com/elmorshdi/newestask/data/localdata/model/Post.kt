package com.elmorshdi.newestask.data.localdata.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.elmorshdi.newestask.util.Constant.POST_TABLE_NAME
import com.elmorshdi.newestask.util.Converters

@Entity(tableName = POST_TABLE_NAME)
@TypeConverters(Converters::class)
data class Post(
    val body: String?,
    @PrimaryKey
    val id: Int?,
    val reactions: Int?,
    val tags: List<String?>?,
    val title: String?,
    val userId: Int?
)