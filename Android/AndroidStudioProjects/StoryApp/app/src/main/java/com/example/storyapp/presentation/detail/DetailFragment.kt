package com.example.storyapp.presentation.detail

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.data.models.Story
import com.example.storyapp.databinding.FragmentDetailBinding
import com.example.storyapp.domain.factory.DetailViewModelFactory
import com.example.storyapp.domain.sealed_class.ViewModelState
import com.example.storyapp.domain.utils.viewBindings
import com.example.storyapp.presentation.custom_view.CustomSupportMapFragment
import com.example.storyapp.presentation.custom_view.ShadowSpan
import com.example.storyapp.presentation.custom_view.ToolbarProfileIcon
import com.example.storyapp.presentation.dialogs.DialogIconProfile
import com.example.storyapp.presentation.dialogs.DialogSimpleWarning
import com.example.storyapp.presentation.main.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch

class DetailFragment : Fragment(R.layout.fragment_detail), MenuProvider, DialogIconProfile.DialogIconProfileListener {

    private val binding by viewBindings(FragmentDetailBinding::bind)
    private val args: DetailFragmentArgs by navArgs()
    private val storyId by lazy { args.storyId }
    private val mainViewModel: MainViewModel by activityViewModels()
    private val detailViewModel: DetailViewModel by viewModels { DetailViewModelFactory(storyId, requireActivity().application) }
    private var googleMap: GoogleMap? = null
    private lateinit var customSupportMapFragment: CustomSupportMapFragment
    private val dialogIconProfile by lazy { DialogIconProfile().apply { setOnLogoutClicked(this@DetailFragment) } }
    private val simpleDialog by lazy { DialogSimpleWarning() }
    private val toolbarProfileIcon by lazy { ToolbarProfileIcon(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.detailStory.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewModelState.OnLoading -> showLoading()
                is ViewModelState.OnData -> showData(state.data)
                is ViewModelState.OnError -> showError(state.displayErrorMessage)
            }
        }
        customSupportMapFragment = childFragmentManager.findFragmentById(R.id.fcvMapsDetailPage) as CustomSupportMapFragment

        binding.mtbDetailPage.setNavigationOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToHomeFragment()
            findNavController().navigate(action)
        }

        binding.mtbDetailPage.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        mainViewModel.username.observe(viewLifecycleOwner) { username ->
            username?.let { toolbarProfileIcon.setUsername(it) }
        }
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

    private fun showLoading() {
        binding.ablDetailPage.visibility = View.GONE
        binding.nsvDetailPage.visibility = View.GONE
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun closeLoading() {
        binding.ablDetailPage.visibility = View.VISIBLE
        binding.nsvDetailPage.visibility = View.VISIBLE
        binding.pbLoading.visibility = View.GONE
    }

    private fun showData(story: Story) {
        closeLoading()
        binding.apply {
            Glide.with(this@DetailFragment)
                .load(story.photoUrl)
                .into(ivStoryImage)

            val toolbarTitle = "Story by ${story.name}"
            val toolbarSpanTitle = SpannableString(toolbarTitle)
            toolbarSpanTitle.setSpan(
                ShadowSpan(1.6f, 1.5f,1.3f, Color.BLACK, Color.WHITE),
                0, toolbarTitle.length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            mtbDetailPage.title = toolbarSpanTitle
            etCreatedAt.setText(story.createdAt)
            etUserStory.setText(story.description)
        }


        if (story.storyPosition != null) {
            if (googleMap == null) {
                setupMaps(position = story.storyPosition)
            } else {
                showMap(position = story.storyPosition)
            }
        }
    }

    private fun setupMaps(position: LatLng) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                googleMap = customSupportMapFragment.awaitMap()
                showMap(position)
            }
        }
    }

    private fun showMap(position: LatLng) {
        binding.fcvMapsDetailPage.visibility = View.VISIBLE
        googleMap?.addMarker { position(position) }
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16f))
    }

    private fun showError(displayErrorMessage: String) {
        closeLoading()
        simpleDialog.setMessage(displayErrorMessage)
        simpleDialog.show(parentFragmentManager, null)
    }
}