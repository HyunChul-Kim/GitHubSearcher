package com.chul.githubsearcher

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.chul.githubsearcher.databinding.FragmentSearchUserBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchUserFragment: Fragment() {

    private var _binding: FragmentSearchUserBinding? = null
    private val binding get() = _binding!!

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
            binding.searchUserProgress.isVisible = loadState.refresh is LoadState.Loading
        }
    }

    private fun setupViewModel() {
        searchHeaderViewModel.userFlow.observe(viewLifecycleOwner) { data ->
            adapter.submitData(lifecycle, data)
        }
        searchHeaderViewModel.searchKeyword.observe(viewLifecycleOwner) {
            binding.searchUserHeader.searchHeaderEditText.run {
                clearFocus()
                hideKeyboard(this.context, this)
            }
            adapter.submitData(lifecycle, PagingData.empty())
        }
    }

    private fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}