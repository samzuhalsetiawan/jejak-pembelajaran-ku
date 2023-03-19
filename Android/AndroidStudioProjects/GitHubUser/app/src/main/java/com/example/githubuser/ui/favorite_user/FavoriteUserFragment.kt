package com.example.githubuser.ui.favorite_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapters.UserListAdapter
import com.example.githubuser.data.models.User
import com.example.githubuser.databinding.FragmentFavoriteUserBinding
import com.example.githubuser.interfaces.IUserCardClickEventHandler
import com.example.githubuser.ui.viewmodel.GitHubUserViewModel

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
        binding = FragmentFavoriteUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteUserAdapter = UserListAdapter(this)
        binding.includeUserNotFound.tvUserNotFound.text =
            resources.getString(R.string.label_user_empty_favorite)
        binding.setupRecyclerView()
        gitHubUserViewModel.getAllUserFavorite().observe(viewLifecycleOwner, this)

    }

    override fun onChanged(value: List<User>) {
        gitHubUserViewModel.notifyFavoriteUserChange(value)
        binding.toggleDisplayErrorPage(value.isEmpty())
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

    private fun FragmentFavoriteUserBinding.setupRecyclerView() {
        rvFavoriteUser.adapter = favoriteUserAdapter
        rvFavoriteUser.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun FragmentFavoriteUserBinding.toggleDisplayErrorPage(shouldShow: Boolean) {
        if (shouldShow) {
            flUserNotFound.visibility = View.VISIBLE
            rvFavoriteUser.visibility = View.INVISIBLE
        } else {
            flUserNotFound.visibility = View.GONE
            rvFavoriteUser.visibility = View.VISIBLE
        }
    }

}