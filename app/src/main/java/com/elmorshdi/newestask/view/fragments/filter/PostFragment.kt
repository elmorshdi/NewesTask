package com.elmorshdi.newestask.view.fragments.filter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.elmorshdi.newestask.data.localdata.model.Post
import com.elmorshdi.newestask.databinding.FragmentItemListBinding
import com.elmorshdi.newestask.view.fragments.ShardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class PostFragment : Fragment() ,FilterAdapter.Interaction{
    private val binding by lazy {
        FragmentItemListBinding.inflate(layoutInflater)
    }
    private val viewModel: ShardViewModel by viewModels()
    private val filterAdapter = FilterAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filterBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        lifecycleScope.launchWhenCreated {
            viewModel.filterPostsFlow.collect {
                filterAdapter.submitList(it)

            }
        }
        binding.filterEditTxt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
             }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    lifecycleScope.launch (Dispatchers.IO){
                        viewModel.getFilteredList(s.toString().toInt())
                     }

                }else{
                    filterAdapter.submitList(listOf())

                }

            }
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Set the adapter
        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = filterAdapter
        }

        return binding.root
     }




    override fun onItemSelected(post: Post) {
    findNavController().navigate(PostFragmentDirections.actionPostFragmentToDetailsFragment(post.id!!))
        }
}