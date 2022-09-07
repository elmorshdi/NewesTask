package com.elmorshdi.newestask.view.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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


        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postPagingAdapter
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.getPosts().collectLatest { pagingData ->
                postPagingAdapter.submitData(pagingData)
            }
        }
        binding.filterIcon.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPostFragment())

        }
    }



    override fun itemClicked(item: Post?) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item?.id!!))
    }


}
