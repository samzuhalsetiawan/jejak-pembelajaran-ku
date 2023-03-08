package com.example.githubuser.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapters.UserListAdapter
import com.example.githubuser.data.FollowType
import com.example.githubuser.data.GitHubUserViewModel
import com.example.githubuser.databinding.FragmentFollowersAndFollowingListBinding
import com.example.githubuser.models.User

class FollowersAndFollowingListFragment(private val followType: FollowType) :
    Fragment(),
    Observer<List<User>>,
    UserListAdapter.OnUserCardClickListener {

    private lateinit var binding: FragmentFollowersAndFollowingListBinding
    private lateinit var userListAdapter: UserListAdapter
    private val gitHubUserViewModel: GitHubUserViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersAndFollowingListBinding
            .bind(inflater.inflate(R.layout.fragment_followers_and_following_list, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userListAdapter = UserListAdapter(this)
        setupRecyclerView()
        setupListOfUserAndObserve()

    }

    private fun setupRecyclerView() {
        binding.rvFollowersAndFollowingList.apply {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(this@FollowersAndFollowingListFragment.requireContext())
        }
    }

    private fun setupListOfUserAndObserve() {
        when (followType) {
            FollowType.Follower -> gitHubUserViewModel.listOfFollower.observe(viewLifecycleOwner, this)
            FollowType.Following -> gitHubUserViewModel.listOfFollowing.observe(viewLifecycleOwner, this)
        }
    }

    override fun onUserCardClickListener(view: View?, user: User) {
        val action = DetailFragmentDirections.actionDetailFragmentToDetailFragment(user)
        requireParentFragment().findNavController().navigate(action)
    }

    override fun onChanged(listOfUser: List<User>?) {
        listOfUser?.let { userListAdapter.listOfUser = it }
    }
}