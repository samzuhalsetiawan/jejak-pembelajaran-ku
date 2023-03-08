package com.example.githubuser.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapters.FollowersAndFollowingAdapter
import com.example.githubuser.data.FollowType
import com.example.githubuser.data.GitHubUserViewModel
import com.example.githubuser.databinding.FragmentDetailBinding
import com.example.githubuser.models.User

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var user: User
    private lateinit var followersAndFollowingAdapter: FollowersAndFollowingAdapter
    private val gitHubUserViewModel: GitHubUserViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        user = args.user
        binding = FragmentDetailBinding.bind(inflater.inflate(R.layout.fragment_detail, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gitHubUserViewModel.listOfFollower.observe(viewLifecycleOwner) { updateFollowsList(FollowType.Follower) }
        gitHubUserViewModel.listOfFollowing.observe(viewLifecycleOwner) { updateFollowsList(FollowType.Following) }

        gitHubUserViewModel.getAllFollowerOf(user.userName)
        gitHubUserViewModel.getAllUserFollowedBy(user.userName)
        followersAndFollowingAdapter = FollowersAndFollowingAdapter(this)

        binding.apply {
            Glide.with(this@DetailFragment)
                .load(user.avatarUrl)
                .into(ivUserProfilePicture)
            tvUsername.text = user.userName
            vp2FollowersAndFollowing.adapter = followersAndFollowingAdapter
        }

    }

    private fun updateFollowsList(followType: FollowType) {
        when (followType) {
            FollowType.Follower -> {
                val followers = gitHubUserViewModel.listOfFollower.value ?: emptyList()
                binding.tvUserFollowerCount.text = "${followers.size} Followers"
            }
            FollowType.Following -> {
                val following = gitHubUserViewModel.listOfFollowing.value ?: emptyList()
                binding.tvUserFollowingCount.text = "${following.size} Following"
            }
        }
    }

}