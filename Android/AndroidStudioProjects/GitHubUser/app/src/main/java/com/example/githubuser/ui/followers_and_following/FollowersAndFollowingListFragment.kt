package com.example.githubuser.ui.followers_and_following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapters.UserListAdapter
import com.example.githubuser.data.models.User
import com.example.githubuser.enums.FollowType
import com.example.githubuser.ui.viewmodel.GitHubUserViewModel
import com.example.githubuser.databinding.FragmentFollowersAndFollowingListBinding
import com.example.githubuser.interfaces.IUserCardClickEventHandler
import com.example.githubuser.ui.detail_user.DetailFragmentDirections
import com.example.githubuser.utils.DebugHelper
import com.google.android.material.imageview.ShapeableImageView

class FollowersAndFollowingListFragment() :
    Fragment(),
    IUserCardClickEventHandler {

    companion object {
        private const val ARGS_KEY = "ARGS_KEY"

        fun newInstance(followType: FollowType): FollowersAndFollowingListFragment {
            return FollowersAndFollowingListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGS_KEY, followType.name)
                }
            }
        }
    }

    private lateinit var binding: FragmentFollowersAndFollowingListBinding
    private lateinit var userListAdapter: UserListAdapter
    private var followType: FollowType? = null
    private val gitHubUserViewModel: GitHubUserViewModel by activityViewModels()
    private val initializeProgress = mutableListOf(false, false)
    private var shouldShowErrorPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(ARGS_KEY)?.also { followType = FollowType.valueOf(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersAndFollowingListBinding.bind(
            inflater.inflate(R.layout.fragment_followers_and_following_list, container, false)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userListAdapter = UserListAdapter(this)
        setupRecyclerView()
        setupListOfUserAndObserve()

    }

    override fun onCardClickListener(card: CardView, user: User) {
        gitHubUserViewModel.clearListOfFollowerAndFollowing()
        val action = DetailFragmentDirections.actionDetailFragmentToDetailFragment(user)
        requireParentFragment().findNavController().navigate(action)
    }

    override fun onFavoriteIconCheckedListener(favButton: CheckBox, user: User) {
        gitHubUserViewModel.addUserToFavorite(user)
    }

    override fun onFavoriteIconUncheckedListener(favButton: CheckBox, user: User) {
        gitHubUserViewModel.removeUserFromFavorite(user)
    }

    private val onListOfUserFollowsChange = Observer { value: List<User> ->
        shouldShowErrorPage = value.isEmpty()
        updateInitializeProgress(0)
        userListAdapter.listOfUser = value
    }

    private val onListOfFavoriteUserChange = Observer { value: List<User> ->
        updateInitializeProgress(1)
        gitHubUserViewModel.notifyFavoriteUserChange(value)
    }

    private fun setupRecyclerView() {
        binding.rvFollowersAndFollowingList.apply {
            adapter = userListAdapter
            layoutManager =
                LinearLayoutManager(this@FollowersAndFollowingListFragment.requireContext())
        }
        binding.includeNotFound.tvUserNotFound.text = when (followType) {
            FollowType.Follower -> resources.getString(R.string.label_user_empty_follower)
            FollowType.Following -> resources.getString(R.string.label_user_empty_following)
            else -> return
        }
    }

    private fun setupListOfUserAndObserve() {
        val followType = followType ?: return
        gitHubUserViewModel.getListOfUserBasedFollowType(followType).observe(viewLifecycleOwner, onListOfUserFollowsChange)
        gitHubUserViewModel.getAllUserFavorite().observe(viewLifecycleOwner, onListOfFavoriteUserChange)
    }

    private fun updateInitializeProgress(index: Int) {
        initializeProgress[index] = true
        if (initializeProgress.all { it }) closeShimmer(shouldShowErrorPage)
    }

    private fun closeShimmer(shouldShowErrorPage: Boolean = false) {
        binding.shimmerFollowsList.visibility = View.GONE
        if (shouldShowErrorPage) {
            binding.flUserNotFound.visibility = View.VISIBLE
        } else {
            binding.flUserNotFound.visibility = View.GONE
        }
        binding.rvFollowersAndFollowingList.visibility = View.VISIBLE
    }
}