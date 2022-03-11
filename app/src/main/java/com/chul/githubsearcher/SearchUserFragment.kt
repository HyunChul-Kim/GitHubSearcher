package com.chul.githubsearcher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.chul.githubsearcher.databinding.FragmentSearchUserBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchUserFragment: Fragment() {

    private var _binding: FragmentSearchUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SearchUserViewModel
    private lateinit var searchHeaderViewModel: SearchHeaderViewModel
    private val adapter = SearchUserAdapter()

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
    }

    private fun setupViewModel() {
        searchHeaderViewModel.userFlow.observe(viewLifecycleOwner) { data ->
            adapter.submitData(lifecycle, data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}