package com.example.storyapp.presentation.create_story

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.databinding.FragmentCreateStoryBinding
import com.example.storyapp.domain.utils.uriToFile
import com.example.storyapp.domain.utils.viewBindings
import com.example.storyapp.presentation.camera_app.CameraActivity
import com.example.storyapp.presentation.custom_view.CustomSupportMapFragment
import com.example.storyapp.presentation.dialogs.DialogCameraPermissionNeeded
import com.example.storyapp.presentation.dialogs.DialogCameraPermissionRationale
import com.example.storyapp.presentation.dialogs.DialogLocationPermissionRationale
import com.example.storyapp.presentation.dialogs.DialogSelectPhotoSource
import com.example.storyapp.presentation.dialogs.DialogSimpleWarning
import com.example.storyapp.presentation.main.MainViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.maps.android.ktx.addMarker


class CreateStoryFragment : Fragment(R.layout.fragment_create_story),
        DialogSelectPhotoSource.DialogSelectPhotoSourceListener,
        OnMapReadyCallback,
        OnMenuItemClickListener
{

    private val binding by viewBindings(FragmentCreateStoryBinding::bind)
    private val createStoryViewModel: CreateStoryViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var requestCameraPermissionsLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var requestLocationPermissionsLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var cameraActivityLauncher: ActivityResultLauncher<Intent>
    private lateinit var photoPickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var mapFragment: CustomSupportMapFragment
    private val dialogPhotoSource by lazy { DialogSelectPhotoSource().apply { optionSelectListener = this@CreateStoryFragment } }
    private val dialogCameraPermissionRationale by lazy { DialogCameraPermissionRationale().apply { dialogCameraPermissionRationaleListener = this@CreateStoryFragment.dialogCameraPermissionRationaleListener } }
    private val dialogLocationPermissionRationale by lazy { DialogLocationPermissionRationale().apply { dialogLocationPermissionRationaleListener = this@CreateStoryFragment.dialogLocationPermissionRationaleListener } }
    private val dialogCameraPermissionNeeded by lazy { DialogCameraPermissionNeeded().apply { dialogCameraPermissionNeededListener = openSettings } }
    private val dialogLocationPermissionNeeded by lazy { DialogLocationPermissionRationale() }
    private val fusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(requireContext()) }
    private val dialogSimpleWarning by lazy { DialogSimpleWarning() }
    private var googleMaps: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestCameraPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), requestedCameraPermissionsCallback)
        requestLocationPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), requestedLocationPermissionsCallback)
        cameraActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), cameraActivityResultCallback)
        photoPickerLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia(), visualMediaPickerResultCallback)
        mapFragment = childFragmentManager.findFragmentById(R.id.frMaps) as CustomSupportMapFragment

        binding.ppPhotoPicker.setOnClickListener {
            val permissionsNeedToRequest = getCameraPermissionsNeedToRequest()
            if (permissionsNeedToRequest.isEmpty()) {
                dialogPhotoSource.show(parentFragmentManager, "dialog_photo_picker")
            } else {
                requestCameraPermissionsLauncher.launch(permissionsNeedToRequest)
            }
        }

        binding.toolbarCreateStory.setNavigationOnClickListener {
            val action = CreateStoryFragmentDirections.actionCreateStoryFragmentToHomeFragment()
            findNavController().navigate(action)
        }

        binding.msToggleLocation.setOnCheckedChangeListener(onLocationSwitchCheckedChangeListener)
        mapFragment.touchListener = CustomSupportMapFragment.CustomSupportMapFragmentOnTouchListener {
            binding.nsvScrollView.requestDisallowInterceptTouchEvent(true)
        }

        binding.toolbarCreateStory.setOnMenuItemClickListener(this)

        createStoryViewModel.currentSelectedPhoto.observe(viewLifecycleOwner) {
            it?.let { uri -> Glide.with(this).load(uri).into(binding.ppPhotoPicker) }
        }
        createStoryViewModel.currentSelectedLocation.observe(viewLifecycleOwner) { location ->
            location?.let { updateMapsLocation(it) }
        }
    }

    override fun onCameraOptionSelected() { launchCameraActivity() }

    override fun onGalleryOptionSelected() { photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }

    private fun launchCameraActivity() { cameraActivityLauncher.launch(Intent(requireContext(), CameraActivity::class.java)) }

    private val dialogCameraPermissionRationaleListener = object : DialogCameraPermissionRationale.DialogCameraPermissionRationaleListener {
        override fun onPositiveButtonClicked() {
            requestCameraPermissionsLauncher.launch(getCameraPermissionsNeedToRequest())
        }
    }

    private val dialogLocationPermissionRationaleListener = object : DialogLocationPermissionRationale.DialogLocationPermissionRationaleListener {
        override fun onPositiveButtonClicked() {
            requestLocationPermissionsLauncher.launch(getLocationPermissionNeedToRequest())
        }
    }

    private val requestedCameraPermissionsCallback = ActivityResultCallback<Map<String, Boolean>> { result ->
        when {
            result.all { !shouldShowRequestPermissionRationale(it.key) && it.value } -> dialogPhotoSource.show(parentFragmentManager, "dialog_photo_picker")
            result.none { shouldShowRequestPermissionRationale(it.key) } -> dialogCameraPermissionNeeded.show(parentFragmentManager, "dialog_permission_needed")
            else -> dialogCameraPermissionRationale.show(parentFragmentManager, "dialog_permission_rationale")
        }
    }

    private val requestedLocationPermissionsCallback = ActivityResultCallback<Map<String, Boolean>> { result ->
        when {
            result.all { !shouldShowRequestPermissionRationale(it.key) && it.value } -> showMaps()
            result.none { shouldShowRequestPermissionRationale(it.key) } -> {
                dialogLocationPermissionNeeded.show(parentFragmentManager, "dialog_permission_needed")
                binding.msToggleLocation.isChecked = false
            }
            else -> dialogLocationPermissionRationale.show(parentFragmentManager, "dialog_permission_rationale")
        }
    }

    private val cameraActivityResultCallback = ActivityResultCallback<ActivityResult> { activityResult ->
        if (activityResult.data == null || activityResult.resultCode != Activity.RESULT_OK) return@ActivityResultCallback
        activityResult.data?.let { data ->
            val uri = data.getStringExtra(CameraActivity.EXTRA_PHOTO_URI)
            uri?.let { createStoryViewModel.setCurrentSelectedPhoto(Uri.parse(it)) }
        }
    }

    private val visualMediaPickerResultCallback = ActivityResultCallback<Uri?> {
        it?.let { uri -> createStoryViewModel.setCurrentSelectedPhoto(uri) }
    }

    private fun getCameraPermissionsNeedToRequest(): Array<String> {
        return CameraActivity.REQUIRED_PERMISSIONS.filter { permissionName ->
            ContextCompat.checkSelfPermission(requireContext(), permissionName) == PackageManager.PERMISSION_DENIED
        }.toTypedArray()
    }

    private fun getLocationPermissionNeedToRequest(): Array<String> {
        return REQUIRED_LOCATION_LOCATION.filter { permissionName ->
            ContextCompat.checkSelfPermission(requireContext(), permissionName) == PackageManager.PERMISSION_DENIED
        }.toTypedArray()
    }

    private val onLocationSwitchCheckedChangeListener = OnCheckedChangeListener { _, isChecked ->
        if (isChecked) {
            val permissionsNeedToRequest = getLocationPermissionNeedToRequest()
            if (permissionsNeedToRequest.isEmpty()) {
                showMaps()
            } else {
                requestLocationPermissionsLauncher.launch(permissionsNeedToRequest)
            }
        } else hideMaps()
    }

    private val openSettings = object : DialogCameraPermissionNeeded.DialogCameraPermissionNeededListener {
        override fun onPositiveButtonClicked() {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", requireContext().packageName, null)
            intent.apply {
                data = uri
                addCategory(Intent.CATEGORY_DEFAULT)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            }
            startActivity(intent)
        }
    }

    private fun hideMaps() {
        binding.frMaps.visibility = View.GONE
    }

    private fun showMaps() {
        binding.msToggleLocation.isEnabled = false
        binding.frMaps.visibility = View.VISIBLE
        if (googleMaps == null) {
            setupGoogleMaps()
        } else {
            binding.msToggleLocation.isEnabled = true
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(gm: GoogleMap) {
        googleMaps = gm.apply { isMyLocationEnabled = true }
        createStoryViewModel.getCurrentLocation(fusedLocationProviderClient)
        gm.setOnMapLongClickListener { latLng ->
            gm.addMarker { position(latLng) }.also { marker ->
                createStoryViewModel.currentSelectedLocation.value?.marker?.remove()
                marker?.let { createStoryViewModel.setCurrentSelectedLocation(it) } ?: createStoryViewModel.setCurrentSelectedLocation(latLng)
            }
        }
    }

    private fun updateMapsLocation(ps: CreateStoryViewModel.Position) {
        val latLng = ps.latLng ?: return
        googleMaps?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
        binding.msToggleLocation.isEnabled = true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menuPosting -> {
                val uri = createStoryViewModel.currentSelectedPhoto.value
                val story = binding.etUserStory.text.toString()
                val position = createStoryViewModel.currentSelectedLocation.value?.latLng
                if (uri == null || story.isBlank()) {
                    dialogSimpleWarning.setMessage("Foto dan story tidak boleh kosong")
                    dialogSimpleWarning.show(parentFragmentManager, null)
                    return false
                }
                val file = uriToFile(uri, requireContext())
                mainViewModel.postingStory(file, story, position?.latitude?.toFloat(), position?.longitude?.toFloat())
                true
            }
            else -> false
        }
    }

    @SuppressLint("MissingPermission")
    private fun setupGoogleMaps() {
        mapFragment.getMapAsync(this)
    }

    companion object {
        private val REQUIRED_LOCATION_LOCATION = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
}