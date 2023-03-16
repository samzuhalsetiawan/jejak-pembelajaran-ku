package com.example.githubuser.ui.search_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapters.UserListAdapter
import com.example.githubuser.data.models.User
import com.example.githubuser.ui.viewmodel.GitHubUserViewModel
import com.example.githubuser.databinding.FragmentSearchBinding
import com.example.githubuser.interfaces.IUserCardClickEventHandler

class SearchFragment : Fragment(),
    Observer<List<User>?>,
    IUserCardClickEventHandler,
    SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var userListAdapter: UserListAdapter
    private val gitHubUserViewModel: GitHubUserViewModel by activityViewModels()

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

        binding.apply {
            rvUserList.adapter = userListAdapter
            rvUserList.layoutManager = LinearLayoutManager(this@SearchFragment.requireContext())
            svSearchUser.setOnQueryTextListener(this@SearchFragment)
        }

        if (gitHubUserViewModel.listOfUser.value == null) gitHubUserViewModel.searchUserByName(resources.getString(R.string.initial_search_name)).also { showShimmer() }

        gitHubUserViewModel.listOfUser.observe(viewLifecycleOwner, this)
    }

    override fun onChanged(value: List<User>?) {
        if (value == null || value.isEmpty()) {
            binding.flUserNotFound.visibility = View.VISIBLE
            userListAdapter.listOfUser = emptyList()
        } else {
            binding.flUserNotFound.visibility = View.GONE
            userListAdapter.listOfUser = value
        }
        closeShimmer()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query == null) return false
        gitHubUserViewModel.searchUserByName(query).also { showShimmer() }
        binding.svSearchUser.apply { setQuery("", false).also { clearFocus() } }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    override fun onCardClickListener(view: View, user: User) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(user)
        findNavController().navigate(action)
    }

    override fun onFavoriteIconClickListener(view: View, user: User) {
        gitHubUserViewModel.addUserToFavorite(user)
    }

    private fun showShimmer() {
        binding.shimmerSearchUser.visibility = View.VISIBLE
    }

    private fun closeShimmer() {
        binding.shimmerSearchUser.visibility = View.GONE
    }
}