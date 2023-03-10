package com.example.githubuser.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.data.FollowType
import com.example.githubuser.ui.fragments.FollowersAndFollowingListFragment


class FollowersAndFollowingAdapter(parentFragment: Fragment) :
    FragmentStateAdapter(parentFragment) {
    override fun getItemCount(): Int = FollowType.values().size
    override fun createFragment(position: Int): Fragment =
        FollowersAndFollowingListFragment(FollowType.values()[position])
}