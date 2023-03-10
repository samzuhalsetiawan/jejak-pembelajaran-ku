package com.example.githubuser.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapters.FollowersAndFollowingAdapter
import com.example.githubuser.data.FollowType
import com.example.githubuser.data.GitHubUserViewModel
import com.example.githubuser.databinding.FragmentDetailBinding
import com.example.githubuser.models.User
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {

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
        binding =
            FragmentDetailBinding.bind(inflater.inflate(R.layout.fragment_detail, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gitHubUserViewModel.getAllFollowerOf(user.userName).also { showShimmer() }
        gitHubUserViewModel.getAllUserFollowedBy(user.userName).also { showShimmer() }
        gitHubUserViewModel.getDetailUser(user.userName).also { showShimmer() }

        gitHubUserViewModel.listOfFollower.observe(viewLifecycleOwner) {
            updateFollowsList(
                FollowType.Follower
            )
        }
        gitHubUserViewModel.listOfFollowing.observe(viewLifecycleOwner) {
            updateFollowsList(
                FollowType.Following
            )
        }
        gitHubUserViewModel.detailUser.observe(viewLifecycleOwner) { detailUser ->
            binding.tvUsername.text = detailUser.name
            binding.tvAccountName.text = user.userName
            binding.tvUserFollowerCount.text =
                resources.getString(R.string.followers_dynamic_label, detailUser.followers)
            binding.tvUserFollowingCount.text =
                resources.getString(R.string.following_dynamic_label, detailUser.following)
        }

        followersAndFollowingAdapter = FollowersAndFollowingAdapter(this)

        binding.apply {
            Glide.with(this@DetailFragment)
                .load(user.avatarUrl)
                .into(ivUserProfilePicture)
            vp2FollowersAndFollowing.adapter = followersAndFollowingAdapter
            TabLayoutMediator(
                tlFollowsTabLayout,
                vp2FollowersAndFollowing
            ) { tab: TabLayout.Tab, index: Int ->
                tab.text = FollowType.values()[index].tabName
            }.attach()
            ablAppBarLayoutDetailUser.addOnOffsetChangedListener(this@DetailFragment)
        }

    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        binding.mlMotionLayoutDetailUser.progress =
            -verticalOffset / appBarLayout.totalScrollRange.toFloat()
    }

    private fun updateFollowsList(followType: FollowType) {
        when (followType) {
            FollowType.Follower -> {
                val followers = gitHubUserViewModel.listOfFollower.value ?: emptyList()
            }
            FollowType.Following -> {
                val following = gitHubUserViewModel.listOfFollowing.value ?: emptyList()
            }
        }
        closeShimmer()
    }

    private fun showShimmer() {
        binding.shimmerDetailUser.visibility = View.VISIBLE
    }

    private fun closeShimmer() {
        binding.shimmerDetailUser.visibility = View.GONE
    }

}