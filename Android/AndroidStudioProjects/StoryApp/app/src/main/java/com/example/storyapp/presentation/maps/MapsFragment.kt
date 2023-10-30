package com.example.storyapp.presentation.maps

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.storyapp.R
import com.example.storyapp.data.models.Story
import com.example.storyapp.databinding.FragmentMapsBinding
import com.example.storyapp.domain.sealed_class.MapsPageEvent
import com.example.storyapp.domain.utils.viewBindings
import com.example.storyapp.presentation.create_story.CreateStoryFragment
import com.example.storyapp.presentation.dialogs.DialogLocationPermissionRationale
import com.example.storyapp.presentation.dialogs.DialogSimpleWarning
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.ktx.addMarker

class MapsFragment : Fragment(R.layout.fragment_maps), OnMapReadyCallback {
    private val binding by viewBindings(FragmentMapsBinding::bind)
    private val mapsViewModel: MapsViewModel by viewModels()
    private lateinit var requestLocationPermissionsLauncher: ActivityResultLauncher<Array<String>>
    private val dialogLocationPermissionNeeded by lazy { DialogLocationPermissionRationale() }
    private val dialogLocationPermissionRationale by lazy { DialogLocationPermissionRationale().apply { dialogLocationPermissionRationaleListener = this@MapsFragment.dialogLocationPermissionRationaleListener } }
    private val dialogSimpleWarning by lazy { DialogSimpleWarning() }
    private var withoutLocationPermission = false
    private var googleMap: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestLocationPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), requestedLocationPermissionsCallback)

        initMaps()

        mapsViewModel.mapsPageEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                is MapsPageEvent.OnCloseLoading -> closeLoading()
                is MapsPageEvent.OnError -> showError(event.throwable).also { closeLoading() }
                is MapsPageEvent.OnLoading -> showLoading()
            }
        }

        mapsViewModel.listOfStories.observe(viewLifecycleOwner) { stories ->
            updateMaps(stories)
        }

    }

    @SuppressLint("MissingPermission", "PotentialBehaviorOverride")
    private fun updateMaps(stories: List<Story>) {
        val gMap = googleMap ?: return
        for (story in stories) {
            val storyLocation = story.storyPosition ?: continue
            gMap.addMarker { position(storyLocation) }
        }
        gMap.setOnMarkerClickListener { marker ->
            val story = stories.find { it.storyPosition == marker.position } ?: return@setOnMarkerClickListener false
            val action = MapsFragmentDirections.actionMapsFragmentToDetailFragment(story.id)
            findNavController().navigate(action)
            true
        }
        if (!withoutLocationPermission) gMap.isMyLocationEnabled = true
        val indonesiaLocation = LatLng(-2.308724, 118.487034)
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesiaLocation, 5f))
    }

    private fun showError(throwable: Throwable) {
        dialogSimpleWarning.setMessage(throwable.message.toString())
        dialogSimpleWarning.show(parentFragmentManager, null)
    }

    private fun showLoading() {
        binding.pbLoading.visibility = View.VISIBLE
        binding.map.visibility = View.GONE
    }

    private fun closeLoading() {
        binding.pbLoading.visibility = View.GONE
        binding.map.visibility = View.VISIBLE
    }

    private fun initMaps() {
        val permissionsNeedToRequest = getLocationPermissionNeedToRequest()
        if (permissionsNeedToRequest.isEmpty()) {
            showMaps()
        } else {
            requestLocationPermissionsLauncher.launch(permissionsNeedToRequest)
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        p0.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.custom_map_style))
        googleMap = p0
        mapsViewModel.getAllStoriesWithLocation()
    }

    private fun getLocationPermissionNeedToRequest(): Array<String> {
        return CreateStoryFragment.REQUIRED_LOCATION_LOCATION.filter { permissionName ->
            ContextCompat.checkSelfPermission(requireContext(), permissionName) == PackageManager.PERMISSION_DENIED
        }.toTypedArray()
    }

    private val requestedLocationPermissionsCallback = ActivityResultCallback<Map<String, Boolean>> { result ->
        when {
            result.all { !shouldShowRequestPermissionRationale(it.key) && it.value } -> showMaps()
            result.none { shouldShowRequestPermissionRationale(it.key) } -> {
                dialogLocationPermissionNeeded.show(parentFragmentManager, null)
                showMaps(true)
            }
            else -> dialogLocationPermissionRationale.show(parentFragmentManager, null)
        }
    }

    private fun showMaps(withoutLocationPermission: Boolean = false) {
        this.withoutLocationPermission = withoutLocationPermission
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
    }

    private val dialogLocationPermissionRationaleListener = object : DialogLocationPermissionRationale.DialogLocationPermissionRationaleListener {
        override fun onPositiveButtonClicked() {
            requestLocationPermissionsLauncher.launch(getLocationPermissionNeedToRequest())
        }
    }
}