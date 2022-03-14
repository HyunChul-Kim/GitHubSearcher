package com.chul.githubsearcher

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.chul.githubsearcher.databinding.FragmentSearchUserBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchUserFragment: Fragment() {

    private var _binding: FragmentSearchUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SearchUserViewModel
    private lateinit var searchHeaderViewModel: SearchHeaderViewModel
    private val adapter = SearchUserAdapter { url ->
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchUserBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SearchUserViewModel::class.java]
        searchHeaderViewModel = ViewModelProvider(this)[SearchHeaderViewModel::class.java]
        binding.headerViewModel = searchHeaderViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListView()
        setupViewModel()
    }

    private fun setupListView() {
        binding.searchUserListView.adapter = adapter
        adapter.addLoadStateListener { loadState ->
            binding.searchUserListView.isVisible = loadState.refresh is LoadState.NotLoading
        }
    }

    private fun setupViewModel() {
        searchHeaderViewModel.userFlow.observe(viewLifecycleOwner) { data ->
            adapter.submitData(lifecycle, data)
        }
        searchHeaderViewModel.searchKeyword.observe(viewLifecycleOwner) {
            binding.searchUserListView.scrollToPosition(0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}