package com.example.githubuser.presentation.ui.favorite_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapters.UserListAdapter
import com.example.githubuser.data.models.User
import com.example.githubuser.databinding.FragmentFavoriteUserBinding
import com.example.githubuser.interfaces.IUserCardClickEventHandler
import com.example.githubuser.presentation.viewmodel.FavoriteUserViewModel
import com.example.githubuser.presentation.viewmodel.MainViewModel
import com.example.githubuser.sealed_class.ViewModelState

class FavoriteUserFragment : Fragment(R.layout.fragment_favorite_user),
    IUserCardClickEventHandler {

    private lateinit var binding: FragmentFavoriteUserBinding
    private lateinit var favoriteUserAdapter: UserListAdapter
    private val mainViewModel: MainViewModel by activityViewModels()
    private val favoriteUserViewModel: FavoriteUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteUserAdapter = UserListAdapter(this)
        binding.setupRecyclerView()
        favoriteUserViewModel.listOfUser.observe(viewLifecycleOwner) {
            when (it) {
                is ViewModelState.Loading -> showLoading()
                is ViewModelState.Success -> showData(it.data)
                is ViewModelState.Error -> showError(it.displayMessage)
            }
        }

    }

    private fun showLoading() = binding.showShimmer()

    private fun showData(data: List<User>) {
        favoriteUserAdapter.listOfUser = data
        binding.closeShimmer(null)
    }

    private fun showError(errorMessage: String) = binding.closeShimmer(errorMessage)

    override fun onCardClickListener(card: CardView, user: User) {
        val action = FavoriteUserFragmentDirections.actionFavoriteUserFragmentToDetailFragment(user)
        findNavController().navigate(action)
    }

    override fun onFavoriteIconCheckedListener(favButton: CheckBox, user: User) {
        mainViewModel.changeFavoriteStatusOfUser(user, true)
    }

    override fun onFavoriteIconUncheckedListener(favButton: CheckBox, user: User) {
        mainViewModel.changeFavoriteStatusOfUser(user, false)
    }

    private fun FragmentFavoriteUserBinding.setupRecyclerView() {
        rvFavoriteUser.adapter = favoriteUserAdapter
        rvFavoriteUser.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun FragmentFavoriteUserBinding.showShimmer() {
        shimmerFavUser.visibility = View.VISIBLE
        flErrorPage.visibility = View.GONE
        rvFavoriteUser.visibility = View.GONE
    }

    private fun FragmentFavoriteUserBinding.closeShimmer(errorMessage: String?) {
        if (errorMessage == null) {
            flErrorPage.visibility = View.GONE
            rvFavoriteUser.visibility = View.VISIBLE
        } else {
            rvFavoriteUser.visibility = View.GONE
            includeErrorPage.tvUserNotFound.text = errorMessage
            flErrorPage.visibility = View.VISIBLE
        }
        shimmerFavUser.visibility = View.GONE
    }

}