package com.elmorshdi.newestask.view.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elmorshdi.newestask.data.localdata.model.Post
import com.elmorshdi.newestask.databinding.ItemBinding

class PostPagingAdapter(private var itemClicked: OnItemClick? = null) :
    PagingDataAdapter<Post, PostPagingAdapter.PostViewHolder>(postComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PostViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    interface OnItemClick {
        fun itemClicked(item: Post?)

    }

    inner class PostViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Post) = with(binding) {
            post = item
            binding.card.setOnClickListener {
                itemClicked?.itemClicked(post)
            }
        }
    }

    object postComparator : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}


