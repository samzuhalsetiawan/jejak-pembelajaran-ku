package com.example.githubuser.ui.followers_and_following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class FollowersAndFollowingListFragment() :
    Fragment(),
    Observer<List<User>>,
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

    override fun onCardClickListener(view: View, user: User) {
        val action = DetailFragmentDirections.actionDetailFragmentToDetailFragment(user)
        requireParentFragment().findNavController().navigate(action)
    }

    override fun onFavoriteIconClickListener(view: View, user: User) {
        gitHubUserViewModel.addUserToFavorite(user)
    }

    override fun onChanged(value: List<User>) {
        val followType = followType ?: return
        if (value.isEmpty()) {
            binding.flUserNotFound.visibility = View.VISIBLE
            binding.includeNotFound.tvUserNotFound.text = when (followType) {
                FollowType.Follower -> resources.getString(R.string.label_user_empty_follower)
                FollowType.Following -> resources.getString(R.string.label_user_empty_following)
            }
        } else {
            binding.flUserNotFound.visibility = View.GONE
        }
        userListAdapter.listOfUser = value
    }

    private fun setupRecyclerView() {
        binding.rvFollowersAndFollowingList.apply {
            adapter = userListAdapter
            layoutManager =
                LinearLayoutManager(this@FollowersAndFollowingListFragment.requireContext())
        }
    }

    private fun setupListOfUserAndObserve() {
        followType?.let {
            gitHubUserViewModel.getListOfUserBasedFollowType(it).observe(viewLifecycleOwner, this)
        }
    }
}