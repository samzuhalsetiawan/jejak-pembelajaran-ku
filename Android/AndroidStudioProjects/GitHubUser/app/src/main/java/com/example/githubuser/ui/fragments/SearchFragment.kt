package com.example.githubuser.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapters.UserListAdapter
import com.example.githubuser.data.GitHubUserViewModel
import com.example.githubuser.databinding.FragmentSearchBinding
import com.example.githubuser.models.User

class SearchFragment : Fragment(),
    Observer<List<User>>,
    SearchView.OnQueryTextListener,
    UserListAdapter.OnUserCardClickListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var userListAdapter: UserListAdapter
    private val gitHubUserViewModel: GitHubUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentSearchBinding.bind(inflater.inflate(R.layout.fragment_search, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userListAdapter = UserListAdapter(this)

        binding.rvUserList.apply {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(this@SearchFragment.requireContext())
        }

        if (gitHubUserViewModel.listOfUser.value == null) {
            gitHubUserViewModel.searchUserByName(resources.getString(R.string.initial_search_name))
                .also { showShimmer() }
        }
        gitHubUserViewModel.listOfUser.observe(viewLifecycleOwner, this)
        binding.svSearchUser.setOnQueryTextListener(this)
    }

    override fun onChanged(listOfUser: List<User>?) {
        if (listOfUser == null || listOfUser.isEmpty()) {
            binding.flUserNotFound.visibility = View.VISIBLE
            userListAdapter.listOfUser = emptyList()
        } else {
            binding.flUserNotFound.visibility = View.GONE
            userListAdapter.listOfUser = listOfUser
        }
        closeShimmer()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return query?.let { name ->
            gitHubUserViewModel.searchUserByName(name).also { showShimmer() }
            binding.svSearchUser.setQuery("", false)
            binding.svSearchUser.clearFocus()
            true
        } ?: false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onUserCardClickListener(view: View?, user: User) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(user)
        findNavController().navigate(action)
    }

    private fun showShimmer() {
        binding.shimmerSearchUser.visibility = View.VISIBLE
    }

    private fun closeShimmer() {
        binding.shimmerSearchUser.visibility = View.GONE
    }
}