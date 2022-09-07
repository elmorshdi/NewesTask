
package com.elmorshdi.newestask.view.fragments.filter
import android.view.LayoutInflater
import android.view.ViewGroup
 import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elmorshdi.newestask.data.localdata.model.Post
import com.elmorshdi.newestask.databinding.ItemBinding


class FilterAdapter(
    private val interaction: Interaction? = null
) : androidx.recyclerview.widget.ListAdapter<Post, FilterAdapter.FilterViewHolder>(FilterDiffCallBack()) {

    class FilterDiffCallBack : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    class FilterViewHolder constructor(
        private val binding: ItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itme: Post) = with(itemView) {
            binding.post=itme
            //Notify the listener on item click
            itemView.setOnClickListener {
                interaction?.onItemSelected(itme)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return FilterViewHolder(
            binding, interaction
        )
    }


    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }


    interface Interaction {
        fun onItemSelected(post: Post)

    }


}
