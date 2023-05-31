package com.example.storyapp.presentation.create_story

import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CreateStoryViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _currentSelectedPhoto: MutableLiveData<Uri> = MutableLiveData()
    val currentSelectedPhoto: LiveData<Uri> = _currentSelectedPhoto

    private val position = Position()

    private val _currentSelectedLocation: MutableLiveData<Position> = MutableLiveData()
    val currentSelectedLocation: MutableLiveData<Position> = _currentSelectedLocation

    fun setCurrentSelectedPhoto(uri: Uri) {
        _currentSelectedPhoto.value = uri
    }

    fun setCurrentSelectedLocation(latLng: LatLng) {
        position.latLng = latLng
        _currentSelectedLocation.value = position.copy()
    }

    fun setCurrentSelectedLocation(marker: Marker) {
        position.latLng = marker.position
        position.marker = marker
        _currentSelectedLocation.value = position.copy()
    }


    @SuppressLint("MissingPermission")
    fun getCurrentLocation(client: FusedLocationProviderClient) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = CurrentLocationRequest.Builder().build()
            val result = client.getCurrentLocation(request, null).await()
            result?.let {
                _currentSelectedLocation.postValue(Position(LatLng(it.latitude, it.longitude)))
            }
        }
    }

    data class Position(
        var latLng: LatLng? = null,
        var marker: Marker? = null
    )
}