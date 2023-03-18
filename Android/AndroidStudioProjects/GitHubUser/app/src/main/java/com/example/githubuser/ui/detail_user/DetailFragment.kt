package com.example.githubuser.ui.detail_user

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
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
import com.example.githubuser.enums.FollowType
import com.example.githubuser.ui.viewmodel.GitHubUserViewModel
import com.example.githubuser.databinding.FragmentDetailBinding
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
        binding =
            FragmentDetailBinding.bind(inflater.inflate(R.layout.fragment_detail, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        followersAndFollowingAdapter = FollowersAndFollowingAdapter(this)
        getUserDetailData()
        updateUiDataWith(user)
        setupAppBarTabLayoutAndViewPager()
        setupObserver()

    }

    private fun getUserDetailData() {
        gitHubUserViewModel.getDetailUser(user.login).also { showShimmer() }
        gitHubUserViewModel.getAllFollowerOf(user.login).also { showShimmer() }
        gitHubUserViewModel.getUsersFollowedBy(user.login).also { showShimmer() }
    }

    private fun setupObserver() {
        gitHubUserViewModel.detailUser.observe(viewLifecycleOwner) {
            updateInitializeProgress(0)
            updateUiDataWith(it)
        }
        gitHubUserViewModel.listOfFollower.observe(viewLifecycleOwner) { updateInitializeProgress(1) }
        gitHubUserViewModel.listOfFollowing.observe(viewLifecycleOwner) { updateInitializeProgress(2) }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        binding.mlMotionLayoutDetailUser.progress =
            -verticalOffset / appBarLayout.totalScrollRange.toFloat()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.detail_user_menu, menu)
        val favIconMenu = menu.findItem(R.id.menuAddFavorite)
        gitHubUserViewModel.getAllUserFavorite().observe(viewLifecycleOwner) { value: List<User> ->
            updateInitializeProgress(3)
            gitHubUserViewModel.notifyFavoriteUserChange(value)
            switchFavIconToFavorite(favIconMenu, value.contains(user))
        }
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

    private fun switchFavIconToFavorite(menu: MenuItem, isFavorite: Boolean) {
        menu.icon = if (isFavorite) ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite)
        else ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_border)
        menu.icon?.setTint(Color.WHITE)
    }

    private fun setupAppBarTabLayoutAndViewPager() {
        binding.apply {
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

    private fun updateUiDataWith(user: User) {
        binding.apply {
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
    }

    private fun updateInitializeProgress(index: Int) {
        initializeProgress[index] = true
        if (initializeProgress.all { it }) closeShimmer()
    }

    private fun showShimmer() {
        binding.ablAppBarLayoutDetailUser.visibility = View.INVISIBLE
        binding.shimmerDetailUser.visibility = View.VISIBLE
    }

    private fun closeShimmer() {
        binding.ablAppBarLayoutDetailUser.visibility = View.VISIBLE
        binding.shimmerDetailUser.visibility = View.GONE
    }

}