package com.example.githubuser.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapters.UserListAdapter
import com.example.githubuser.data.FollowType
import com.example.githubuser.data.GitHubUserViewModel
import com.example.githubuser.databinding.FragmentFollowersAndFollowingListBinding
import com.example.githubuser.models.User

class FollowersAndFollowingListFragment(private var followType: FollowType? = null) :
    Fragment(),
    Observer<List<User>>,
    UserListAdapter.OnUserCardClickListener {

    private lateinit var binding: FragmentFollowersAndFollowingListBinding
    private lateinit var userListAdapter: UserListAdapter
    private val gitHubUserViewModel: GitHubUserViewModel by viewModels({ requireParentFragment() })
    private val fragmentViewModel: GitHubUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersAndFollowingListBinding
            .bind(
                inflater.inflate(
                    R.layout.fragment_followers_and_following_list,
                    container,
                    false
                )
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (followType == null) followType =
            fragmentViewModel.followType else fragmentViewModel.followType = followType

        userListAdapter = UserListAdapter(this)
        setupRecyclerView()
        setupListOfUserAndObserve()

    }

    override fun onUserCardClickListener(view: View?, user: User) {
        val action = DetailFragmentDirections.actionDetailFragmentToDetailFragment(user)
        requireParentFragment().findNavController().navigate(action)
    }

    override fun onChanged(listOfUser: List<User>?) {
        if (listOfUser == null || listOfUser.isEmpty()) {
            binding.flUserNotFound.visibility = View.VISIBLE
            binding.includeNotFound.tvUserNotFound.text = when (followType) {
                FollowType.Follower -> resources.getString(R.string.label_user_empty_follower)
                FollowType.Following -> resources.getString(R.string.label_user_empty_following)
                else -> ""
            }
            userListAdapter.listOfUser = emptyList()
        } else {
            binding.flUserNotFound.visibility = View.GONE
            userListAdapter.listOfUser = listOfUser
        }
    }

    private fun setupRecyclerView() {
        binding.rvFollowersAndFollowingList.apply {
            adapter = userListAdapter
            layoutManager =
                LinearLayoutManager(this@FollowersAndFollowingListFragment.requireContext())
        }
    }

    private fun setupListOfUserAndObserve() {
        when (followType) {
            FollowType.Follower -> gitHubUserViewModel.listOfFollower.observe(
                viewLifecycleOwner,
                this
            )
            FollowType.Following -> gitHubUserViewModel.listOfFollowing.observe(
                viewLifecycleOwner,
                this
            )
            else -> return
        }
    }
}