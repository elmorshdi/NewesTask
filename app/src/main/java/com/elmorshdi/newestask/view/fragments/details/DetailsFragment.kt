package com.elmorshdi.newestask.view.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.elmorshdi.newestask.databinding.FragmentDetailsBinding
import com.elmorshdi.newestask.view.fragments.ShardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint

class DetailsFragment : Fragment() {


    private val viewModel: ShardViewModel by viewModels()

    private val binding: FragmentDetailsBinding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id: Int = navArgs<DetailsFragmentArgs>().value.id

        lifecycleScope.launchWhenCreated {

                viewModel.getPostById(id)



                viewModel.post.collectLatest {
                    binding.post = it

                }



        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

}