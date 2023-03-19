package com.example.githubuser.ui.detail_user

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapters.FollowersAndFollowingAdapter
import com.example.githubuser.data.models.User
import com.example.githubuser.databinding.FragmentDetailBinding
import com.example.githubuser.enums.FollowType
import com.example.githubuser.ui.viewmodel.GitHubUserViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment(),
    AppBarLayout.OnOffsetChangedListener,
    MenuProvider {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var followersAndFollowingAdapter: FollowersAndFollowingAdapter
    private val gitHubUserViewModel: GitHubUserViewModel by activityViewModels()
    private val args: DetailFragmentArgs by navArgs()
    private val user: User by lazy { args.user }
    private val initializeProgress = mutableListOf(false, false, false, false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        binding.setupAppBarTabLayoutAndViewPager()
        gitHubUserViewModel.getUserDetailData()
        gitHubUserViewModel.setupObserver()
        binding.updateUiDataWith(user)

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.detail_user_menu, menu)
        val favIconMenu = menu.findItem(R.id.menuAddFavorite)
        switchFavIconToFavorite(favIconMenu, user.isFavorite)
        gitHubUserViewModel.getAllUserFavorite().observe(viewLifecycleOwner) { value: List<User> ->
            updateInitializeProgress(3)
            gitHubUserViewModel.notifyFavoriteUserChange(value)
            switchFavIconToFavorite(favIconMenu, value.contains(user))
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        binding.mlMotionLayoutDetailUser.progress =
            -verticalOffset / appBarLayout.totalScrollRange.toFloat()
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menuAddFavorite -> true.also {
                user.isFavorite = !user.isFavorite
                if (user.isFavorite) gitHubUserViewModel.addUserToFavorite(user)
                else gitHubUserViewModel.removeUserFromFavorite(user)
            }
            else -> false
        }
    }

    private fun GitHubUserViewModel.getUserDetailData() {
        getDetailUser(user.login, user).also { binding.showShimmer() }
        getAllFollowerOf(user.login).also { binding.showShimmer() }
        getUsersFollowedBy(user.login).also { binding.showShimmer() }
    }

    private fun GitHubUserViewModel.setupObserver() {
        detailUser.observe(viewLifecycleOwner) {
            updateInitializeProgress(0)
            binding.updateUiDataWith(it)
        }
        listOfFollower.observe(viewLifecycleOwner) { updateInitializeProgress(1) }
        listOfFollowing.observe(viewLifecycleOwner) { updateInitializeProgress(2) }
    }

    private fun switchFavIconToFavorite(menu: MenuItem, isFavorite: Boolean) {
        menu.icon = if (isFavorite) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite)
                .also { menu.icon?.setTint(ContextCompat.getColor(requireContext(), R.color.red1)) }
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_border)
                .also { menu.icon?.setTint(Color.WHITE) }
        }
    }

    private fun FragmentDetailBinding.setupAppBarTabLayoutAndViewPager() {
        followersAndFollowingAdapter = FollowersAndFollowingAdapter(this@DetailFragment)
        vp2FollowersAndFollowing.adapter = followersAndFollowingAdapter
        TabLayoutMediator(
            tlFollowsTabLayout,
            vp2FollowersAndFollowing
        ) { tab: TabLayout.Tab, index: Int ->
            tab.text = FollowType.values()[index].tabName
        }.attach()
        ablAppBarLayoutDetailUser.addOnOffsetChangedListener(this@DetailFragment)
    }

    private fun FragmentDetailBinding.updateUiDataWith(user: User) {
        Glide.with(this@DetailFragment)
            .load(user.avatarUrl)
            .into(ivUserProfilePicture)
        tvUsername.text = user.name
        tvAccountName.text = user.login
        tvUserFollowerCount.text =
            resources.getString(R.string.followers_dynamic_label, user.followers)
        tvUserFollowingCount.text =
            resources.getString(R.string.following_dynamic_label, user.following)
    }

    private fun updateInitializeProgress(index: Int) {
        initializeProgress[index] = true
        if (initializeProgress.all { it }) binding.closeShimmer()
    }

    private fun FragmentDetailBinding.showShimmer() {
        ablAppBarLayoutDetailUser.visibility = View.INVISIBLE
        shimmerDetailUser.visibility = View.VISIBLE
    }

    private fun FragmentDetailBinding.closeShimmer() {
        ablAppBarLayoutDetailUser.visibility = View.VISIBLE
        shimmerDetailUser.visibility = View.GONE
    }

}