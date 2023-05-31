package com.example.storyapp.presentation.authentication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.storyapp.domain.application.UserStoryApp
import com.example.storyapp.domain.sealed_class.AuthenticationActivityEvent
import com.example.storyapp.domain.sealed_class.ResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class AuthenticationViewModel(application: Application): AndroidViewModel(application) {

    private val userStoryApp = application as UserStoryApp
    private val storiesRepository = userStoryApp.storiesRepository

    private val _events: MutableLiveData<AuthenticationActivityEvent> = MutableLiveData()
    val events: LiveData<AuthenticationActivityEvent> = _events

    private val onCollectFlowToken = FlowCollector<String?> { token ->
        token?.let {
            storiesRepository.setAuthenticationToken(token)
            _events.postValue(AuthenticationActivityEvent.RedirectUserToMain)
        } ?: _events.postValue(AuthenticationActivityEvent.UserNotLoginYet)
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _events.postValue(AuthenticationActivityEvent.ShowLoading)
            when (val requestTokenResponse = storiesRepository.getSavedUserToken()) {
                is ResponseStatus.Error -> _events.postValue(AuthenticationActivityEvent.OnError(requestTokenResponse.throwable))
                is ResponseStatus.Success -> requestTokenResponse.data.collect(onCollectFlowToken)
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _events.postValue(AuthenticationActivityEvent.ShowLoading)
            when (val responseStatus = storiesRepository.loginUser(email, password)) {
                is ResponseStatus.Error -> _events.postValue(AuthenticationActivityEvent.OnError(responseStatus.throwable))
                is ResponseStatus.Success -> _events.postValue(AuthenticationActivityEvent.RedirectUserToMain)
            }
        }
    }

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _events.postValue(AuthenticationActivityEvent.ShowLoading)
            when (val responseStatus = storiesRepository.registerUser(username, email, password)) {
                is ResponseStatus.Error -> _events.postValue(AuthenticationActivityEvent.OnError(responseStatus.throwable))
                is ResponseStatus.Success -> _events.postValue(AuthenticationActivityEvent.RegisterSuccess(email))
            }
        }
    }
}