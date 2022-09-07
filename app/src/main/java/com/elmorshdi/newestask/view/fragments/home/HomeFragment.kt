package com.elmorshdi.newestask.view.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.elmorshdi.newestask.data.localdata.model.Post
import com.elmorshdi.newestask.databinding.FragmentHomeBinding
import com.elmorshdi.newestask.view.fragments.ShardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class HomeFragment : Fragment(), PostPagingAdapter.OnItemClick {
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: ShardViewModel by viewModels()

    private val postPagingAdapter = PostPagingAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
        binding.filterIcon.setOnClickListener {

        }

    }

    private fun initRecycleView() {

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postPagingAdapter
        }
        lifecycleScope.launchWhenCreated {
            viewModel.gg().collectLatest { pagingData ->
                postPagingAdapter.submitData(pagingData)
                Log.d("tag", "2$pagingData")

            }
        }
        postPagingAdapter.addLoadStateListener { loadState ->

            binding.progressBar.isVisible =
                loadState.mediator?.refresh is LoadState.Loading
            binding.progressBar2.isVisible =
                loadState.mediator?.append is LoadState.Loading


        }


    }


    override fun itemClicked(item: Post?) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item?.id!!))
    }


}
