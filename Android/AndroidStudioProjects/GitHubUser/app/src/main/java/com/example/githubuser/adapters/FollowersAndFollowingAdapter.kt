package com.example.githubuser.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.enums.FollowType
import com.example.githubuser.presentation.ui.followers_and_following.FollowersAndFollowingListFragment


class FollowersAndFollowingAdapter(parentFragment: Fragment, private val username: String) :
    FragmentStateAdapter(parentFragment) {
    override fun getItemCount(): Int = FollowType.values().size
    override fun createFragment(position: Int): Fragment =
        FollowersAndFollowingListFragment.newInstance(FollowType.values()[position], username)
}