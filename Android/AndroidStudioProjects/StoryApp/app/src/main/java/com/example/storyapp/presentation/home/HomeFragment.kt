package com.example.storyapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuItemCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.data.models.Story
import com.example.storyapp.databinding.FragmentHomeBinding
import com.example.storyapp.domain.adapter.UserStoryAdapter
import com.example.storyapp.domain.sealed_class.ViewModelState
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
    private val userStoryAdapter by lazy { UserStoryAdapter(this) }
    private val homeViewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val dialogIconProfile by lazy { DialogIconProfile().apply { setOnLogoutClicked(this@HomeFragment) } }
    private val simpleDialog by lazy { DialogSimpleWarning() }
    private val toolbarProfileIcon by lazy { ToolbarProfileIcon(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvStoryList.apply {
            adapter = userStoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        homeViewModel.listOfStories.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewModelState.OnLoading -> showLoading()
                is ViewModelState.OnData -> showData(state.data)
                is ViewModelState.OnError -> showError(state.displayErrorMessage)
            }
        }

        binding.fabCreateStory.setOnClickListener(onCreateStoryButtonClicked)

        binding.mtbHomePage.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        mainViewModel.username.observe(viewLifecycleOwner) { username ->
            username?.let { toolbarProfileIcon.setUsername(it) }
        }
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

    private fun showData(data: List<Story>) {
        closeLoading()
        userStoryAdapter.listOfStories = data
    }

    private fun showError(displayErrorMessage: String) {
        closeLoading()
        simpleDialog.setMessage(displayErrorMessage)
        simpleDialog.show(parentFragmentManager, null)
    }

}