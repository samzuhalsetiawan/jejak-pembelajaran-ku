package com.example.storyapp.presentation.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuItemCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.paging.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.data.models.Story
import com.example.storyapp.data.source.UserStory
import com.example.storyapp.databinding.FragmentHomeBinding
import com.example.storyapp.domain.adapter.UserStoryAdapter
import com.example.storyapp.domain.adapter.UserStoryPagerAdapter
import com.example.storyapp.domain.application.UserStoryApp
import com.example.storyapp.domain.factory.HomeViewModelFactory
import com.example.storyapp.domain.sealed_class.MainActivityEvent
import com.example.storyapp.domain.sealed_class.ResponseStatus
import com.example.storyapp.domain.utils.viewBindings
import com.example.storyapp.presentation.custom_view.ToolbarProfileIcon
import com.example.storyapp.presentation.dialogs.DialogIconProfile
import com.example.storyapp.presentation.dialogs.DialogSimpleWarning
import com.example.storyapp.presentation.main.MainViewModel
import com.google.android.material.card.MaterialCardView

class HomeFragment : Fragment(R.layout.fragment_home),
    UserStoryAdapter.OnStoryCardClicked,
    DialogIconProfile.DialogIconProfileListener,
    MenuProvider
{

    private val binding by viewBindings(FragmentHomeBinding::bind)
    private val userStoryAdapter by lazy { UserStoryPagerAdapter(this) }
    private val application by lazy { requireActivity().application as UserStoryApp }
    private val homeViewModel: HomeViewModel by viewModels { HomeViewModelFactory(application.storiesRepository) }
    private val mainViewModel: MainViewModel by activityViewModels()
    private val dialogIconProfile by lazy { DialogIconProfile().apply { setOnLogoutClicked(this@HomeFragment) } }
    private val simpleDialog by lazy { DialogSimpleWarning() }
    private val toolbarProfileIcon by lazy { ToolbarProfileIcon(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()
        binding.rvStoryList.apply {
            adapter = userStoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        homeViewModel.getPagerStory().observe(viewLifecycleOwner) {
            when (it) {
                is ResponseStatus.Error -> showError(it.throwable.message.toString())
                is ResponseStatus.Success -> {
                    onPagerStory(it.data[0])
                }
            }
        }

        binding.fabCreateStory.setOnClickListener(onCreateStoryButtonClicked)

        binding.mtbHomePage.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        mainViewModel.username.observe(viewLifecycleOwner) { username ->
            username?.let { toolbarProfileIcon.setUsername(it) }
        }
        mainViewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                is MainActivityEvent.OnPostingSuccess -> refreshPager()
                else -> Unit
            }
        }
    }

    private val onPagingDataChanged = Observer<PagingData<Story>> {
        val shouldScrollToTop = mainViewModel.event.value is MainActivityEvent.OnPostingSuccess
        showPagerData(it, shouldScrollToTop)
    }

    override fun onStoryCardClicked(story: Story, card: MaterialCardView) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(story.id)
        findNavController().navigate(action)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.home_menu, menu)
        val menuItem = menu.findItem(R.id.menuIconProfile)
        val profileIconActionProvider = toolbarProfileIcon.apply {
            setOnProfileIconClicked { dialogIconProfile.show(parentFragmentManager, null) }
        }
        MenuItemCompat.setActionProvider(menuItem, profileIconActionProvider)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menuAbout -> {
                findNavController().navigate(R.id.action_homeFragment_to_aboutFragment)
                true
            }
            else -> false
        }
    }

    override fun onLogoutClicked() {
        mainViewModel.logout()
    }

    private val onCreateStoryButtonClicked = View.OnClickListener {
        val action = HomeFragmentDirections.actionHomeFragmentToCreateStoryFragment()
        findNavController().navigate(action)
    }

    private fun showLoading() {
        binding.rvStoryList.visibility = View.GONE
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun closeLoading() {
        binding.rvStoryList.visibility = View.VISIBLE
        binding.pbLoading.visibility = View.GONE
    }

    private fun onPagerStory(userStory: UserStory) {
        userStory.pager.liveData.observe(viewLifecycleOwner, onPagingDataChanged)
    }

    private fun showPagerData(data: PagingData<Story>?, shouldScrollToTop: Boolean) {
        data ?: return
        userStoryAdapter.submitData(lifecycle, data)
        if (shouldScrollToTop) {
            binding.rvStoryList.smoothScrollToPosition(0)
        }
        closeLoading()
    }

    private fun showError(displayErrorMessage: String) {
        closeLoading()
        simpleDialog.setMessage(displayErrorMessage)
        simpleDialog.show(parentFragmentManager, null)
    }

    private fun refreshPager() {
        userStoryAdapter.refresh()
    }

}