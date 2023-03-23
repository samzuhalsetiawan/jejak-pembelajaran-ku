package com.example.githubuser.presentation.ui.search_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapters.UserListAdapter
import com.example.githubuser.data.models.User
import com.example.githubuser.databinding.FragmentSearchBinding
import com.example.githubuser.interfaces.IUserCardClickEventHandler
import com.example.githubuser.presentation.viewmodel.MainViewModel
import com.example.githubuser.presentation.viewmodel.SearchUserViewModel
import com.example.githubuser.sealed_class.ViewModelState

class SearchFragment : Fragment(R.layout.fragment_search),
    IUserCardClickEventHandler,
    SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var userListAdapter: UserListAdapter
    private val mainViewModel: MainViewModel by activityViewModels()
    private val searchUserViewModel: SearchUserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userListAdapter = UserListAdapter(this)
        binding.setupRecyclerView()
        binding.svSearchUser.setOnQueryTextListener(this)
        searchUserViewModel.listOfUser.observe(viewLifecycleOwner) {
            when (it) {
                is ViewModelState.Loading -> showLoading()
                is ViewModelState.Success -> showData(it.data)
                is ViewModelState.Error -> showError(it.displayMessage)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query == null) return false
        searchUserViewModel.searchForUser(query)
        binding.svSearchUser.apply { setQuery("", false).also { clearFocus() } }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    override fun onCardClickListener(card: CardView, user: User) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(user)
        findNavController().navigate(action)
    }

    override fun onFavoriteIconCheckedListener(favButton: CheckBox, user: User) {
        mainViewModel.changeFavoriteStatusOfUser(user, true)
        Toast.makeText(requireContext(), "${user.login} added to favorite ❤️", Toast.LENGTH_SHORT).show()
    }

    override fun onFavoriteIconUncheckedListener(favButton: CheckBox, user: User) {
        mainViewModel.changeFavoriteStatusOfUser(user, false)
        Toast.makeText(requireContext(), "${user.login} removed from favorite️", Toast.LENGTH_SHORT).show()
    }

    private fun FragmentSearchBinding.setupRecyclerView() {
        rvUserList.apply {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(this@SearchFragment.requireContext())
            setHasFixedSize(true)
        }
    }

    private fun showLoading() {
        binding.showShimmer()
    }

    private fun showData(data: List<User>) {
        userListAdapter.listOfUser = data
        binding.closeShimmer(null)
    }

    private fun showError(errorMessage: String) = binding.closeShimmer(errorMessage)

    private fun FragmentSearchBinding.showShimmer() {
        shimmerSearchUser.visibility = View.VISIBLE
        flErrorPage.visibility = View.GONE
    }

    private fun FragmentSearchBinding.closeShimmer(errorMessage: String?) {
        if (errorMessage == null) {
            flErrorPage.visibility = View.GONE
            rvUserList.visibility = View.VISIBLE
        } else {
            rvUserList.visibility = View.GONE
            includeErrorPage.tvUserNotFound.text = errorMessage
            flErrorPage.visibility = View.VISIBLE
        }
        shimmerSearchUser.visibility = View.GONE
    }
}