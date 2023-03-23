package com.example.githubuser.presentation.ui.detail_user

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapters.FollowersAndFollowingAdapter
import com.example.githubuser.data.models.User
import com.example.githubuser.databinding.FragmentDetailBinding
import com.example.githubuser.enums.FollowType
import com.example.githubuser.factories.DetailUserViewModelFactory
import com.example.githubuser.presentation.viewmodel.DetailUserViewModel
import com.example.githubuser.sealed_class.ViewModelState
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment(R.layout.fragment_detail),
    AppBarLayout.OnOffsetChangedListener,
    MenuProvider {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var followersAndFollowingAdapter: FollowersAndFollowingAdapter
    private val detailUserViewModel: DetailUserViewModel by viewModels { DetailUserViewModelFactory(user.login, requireActivity().application) }
    private val args: DetailFragmentArgs by navArgs()
    private val user: User by lazy { args.user }

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
        detailUserViewModel.detailUser.observe(viewLifecycleOwner) {
            when (it) {
                is ViewModelState.Loading -> showLoading()
                is ViewModelState.Success -> showData(it.data)
                is ViewModelState.Error -> showError(it.displayMessage)
            }
        }

    }

    private fun showLoading() = binding.showShimmer()

    private fun showData(data: User) {
        binding.closeShimmer(null)
        binding.updateUiWithDataFrom(data)
    }

    private fun showError(errorMessage: String) = binding.closeShimmer(errorMessage)

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.detail_user_menu, menu)
        val favIconMenu = menu.findItem(R.id.menuAddFavorite)
        switchFavIconToFavorite(favIconMenu, detailUserViewModel.isFavorite.value ?: false)
        detailUserViewModel.isFavorite.observe(viewLifecycleOwner) { switchFavIconToFavorite(favIconMenu, it) }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        binding.mlMotionLayoutDetailUser.progress =
            -verticalOffset / appBarLayout.totalScrollRange.toFloat()
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menuAddFavorite -> true.also {
                user.isFavorite = detailUserViewModel.isFavorite.value ?: false
                if (!user.isFavorite) {
                    detailUserViewModel.changeFavoriteStatusOfUser(user, true)
                    Toast.makeText(requireContext(), "${user.login} added to favorite ❤️", Toast.LENGTH_SHORT).show()
                } else {
                    detailUserViewModel.changeFavoriteStatusOfUser(user, false)
                    Toast.makeText(requireContext(), "${user.login} removed from favorite️", Toast.LENGTH_SHORT).show()
                }
            }
            else -> false
        }
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
        followersAndFollowingAdapter = FollowersAndFollowingAdapter(this@DetailFragment, user.login)
        vp2FollowersAndFollowing.adapter = followersAndFollowingAdapter
        TabLayoutMediator(
            tlFollowsTabLayout,
            vp2FollowersAndFollowing
        ) { tab: TabLayout.Tab, index: Int ->
            tab.text = FollowType.values()[index].tabName
        }.attach()
        ablAppBarLayoutDetailUser.addOnOffsetChangedListener(this@DetailFragment)
    }

    private fun FragmentDetailBinding.updateUiWithDataFrom(user: User) {
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

    private fun FragmentDetailBinding.showShimmer() {
        shimmerDetailUser.visibility = View.VISIBLE
        flErrorPage.visibility = View.GONE
        ablAppBarLayoutDetailUser.visibility = View.INVISIBLE
    }

    private fun FragmentDetailBinding.closeShimmer(errorMessage: String?) {
        if (errorMessage == null) {
            flErrorPage.visibility = View.GONE
            ablAppBarLayoutDetailUser.visibility = View.VISIBLE
            llViewPagerAndTabLayout.visibility = View.VISIBLE
        } else {
            llViewPagerAndTabLayout.visibility = View.GONE
            ablAppBarLayoutDetailUser.visibility = View.INVISIBLE
            includeErrorPage.tvUserNotFound.text = errorMessage
            flErrorPage.visibility = View.VISIBLE
        }
        shimmerDetailUser.visibility = View.GONE
    }

}