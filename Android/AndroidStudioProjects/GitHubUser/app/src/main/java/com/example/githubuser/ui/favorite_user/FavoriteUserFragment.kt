package com.example.githubuser.ui.favorite_user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.adapters.UserListAdapter
import com.example.githubuser.data.models.User
import com.example.githubuser.databinding.FragmentFavoriteUserBinding
import com.example.githubuser.interfaces.IUserCardClickEventHandler
import com.example.githubuser.ui.viewmodel.GitHubUserViewModel
import com.google.android.material.imageview.ShapeableImageView

class FavoriteUserFragment : Fragment(),
    Observer<List<User>>,
    IUserCardClickEventHandler {

    private lateinit var binding: FragmentFavoriteUserBinding
    private lateinit var favoriteUserAdapter: UserListAdapter
    private val gitHubUserViewModel: GitHubUserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteUserBinding.bind(inflater.inflate(R.layout.fragment_favorite_user, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteUserAdapter = UserListAdapter(this)

        binding.includeUserNotFound.tvUserNotFound.text = resources.getString(R.string.label_user_empty_favorite)

        setupRecyclerView()

        gitHubUserViewModel.getAllUserFavorite().observe(viewLifecycleOwner, this)
    }

    override fun onChanged(value: List<User>) {
        gitHubUserViewModel.notifyFavoriteUserChange(value)
        toggleDisplayErrorPage(value.isEmpty())
        favoriteUserAdapter.listOfUser = value
    }

    override fun onCardClickListener(card: CardView, user: User) {
        val action = FavoriteUserFragmentDirections.actionFavoriteUserFragmentToDetailFragment(user)
        findNavController().navigate(action)
    }

    override fun onFavoriteIconCheckedListener(favButton: CheckBox, user: User) {
        gitHubUserViewModel.addUserToFavorite(user)
    }

    override fun onFavoriteIconUncheckedListener(favButton: CheckBox, user: User) {
        gitHubUserViewModel.removeUserFromFavorite(user)
    }

    private fun setupRecyclerView() {
        binding.rvFavoriteUser.apply {
            adapter = favoriteUserAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun toggleDisplayErrorPage(shouldShow: Boolean) {
        if (shouldShow) {
            binding.flUserNotFound.visibility = View.VISIBLE
            binding.rvFavoriteUser.visibility = View.INVISIBLE
        } else {
            binding.flUserNotFound.visibility = View.GONE
            binding.rvFavoriteUser.visibility = View.VISIBLE
        }
    }

}