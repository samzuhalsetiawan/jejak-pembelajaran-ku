package com.example.githubuser.ui.search_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
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
import com.example.githubuser.utils.DebugHelper
import com.google.android.material.imageview.ShapeableImageView

class SearchFragment : Fragment(),
    IUserCardClickEventHandler,
    SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var userListAdapter: UserListAdapter
    private val gitHubUserViewModel: GitHubUserViewModel by activityViewModels()
    private val initializeProgress = mutableListOf(false, false)
    private var shouldShowErrorPage = false

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
        setupRecyclerView()
        binding.svSearchUser.setOnQueryTextListener(this)
        doInitialUserSearch()
        setupLiveDataObserver()

    }

    private fun doInitialUserSearch() {
        if (gitHubUserViewModel.listOfUser.value == null) gitHubUserViewModel.searchUserByName(resources.getString(R.string.initial_search_name)).also { showShimmer() }
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvUserList.adapter = userListAdapter
            rvUserList.layoutManager = LinearLayoutManager(this@SearchFragment.requireContext())
        }
    }

    private fun setupLiveDataObserver() {
        gitHubUserViewModel.listOfUser.observe(viewLifecycleOwner, onListOfUserChange)
        gitHubUserViewModel.getAllUserFavorite().observe(viewLifecycleOwner, onListOfFavoriteUserChange)
    }

    private val onListOfUserChange = Observer { value: List<User> ->
        shouldShowErrorPage = value.isEmpty()
        updateInitializeProgress(0)
        userListAdapter.listOfUser = value
    }

    private val onListOfFavoriteUserChange = Observer { value: List<User> ->
        updateInitializeProgress(1)
        gitHubUserViewModel.notifyFavoriteUserChange(value)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query == null) return false
        gitHubUserViewModel.searchUserByName(query).also { showShimmer() }
        binding.svSearchUser.apply { setQuery("", false).also { clearFocus() } }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    override fun onCardClickListener(card: CardView, user: User) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(user)
        findNavController().navigate(action)
    }

    override fun onFavoriteIconCheckedListener(favButton: CheckBox, user: User) {
        gitHubUserViewModel.addUserToFavorite(user)
    }

    override fun onFavoriteIconUncheckedListener(favButton: CheckBox, user: User) {
        gitHubUserViewModel.removeUserFromFavorite(user)
    }

    private fun updateInitializeProgress(index: Int) {
        initializeProgress[index] = true
        if (initializeProgress.all { it }) closeShimmer(shouldShowErrorPage)
    }

    private fun showShimmer() {
        binding.flUserNotFound.visibility = View.GONE
        binding.rvUserList.visibility = View.INVISIBLE
        binding.shimmerSearchUser.visibility = View.VISIBLE
    }

    private fun closeShimmer(shouldShowErrorPage: Boolean = false) {
        binding.shimmerSearchUser.visibility = View.GONE
        if (shouldShowErrorPage) {
            binding.flUserNotFound.visibility = View.VISIBLE
        } else {
            binding.flUserNotFound.visibility = View.GONE
        }
        binding.rvUserList.visibility = View.VISIBLE
    }
}