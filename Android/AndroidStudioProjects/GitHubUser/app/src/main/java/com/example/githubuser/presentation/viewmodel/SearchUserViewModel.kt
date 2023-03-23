package com.example.githubuser.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.githubuser.application.GitHubUserApplication
import com.example.githubuser.data.models.User
import com.example.githubuser.exceptions.SearchQueryNullException
import com.example.githubuser.sealed_class.ViewModelState
import com.example.githubuser.utils.ExtensionUtils.coroutineExceptionHandler
import com.example.githubuser.utils.ExtensionUtils.mapBasedOnFavoriteWith
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class SearchUserViewModel(application: Application): AndroidViewModel(application) {

    private val repository = (application as GitHubUserApplication).repository

    private val currentQueryName: MutableLiveData<String> = MutableLiveData("Arif")

    private val mediatorLiveData = MediatorLiveData<ViewModelState<List<User>>>(ViewModelState.Loading())

    val listOfUser: LiveData<ViewModelState<List<User>>> = mediatorLiveData

    init {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val listOfFavoriteUserSource = repository.getAllFavoriteUser().asLiveData()
            withContext(Dispatchers.Main + coroutineExceptionHandler) {
                mediatorLiveData.addSource(listOfFavoriteUserSource) { listOfFavoriteUser ->
                    val currentListOfUser = listOfUser.value ?: return@addSource
                    if (currentListOfUser !is ViewModelState.Success) return@addSource
                    val updatedList = currentListOfUser.data.mapBasedOnFavoriteWith(listOfFavoriteUser)
                    mediatorLiveData.value = ViewModelState.Success(updatedList)
                }
            }
        }

        mediatorLiveData.addSource(currentQueryName) { newQueryName ->
            viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                try {
                    mediatorLiveData.postValue(ViewModelState.Loading())
                    val query = newQueryName.ifBlank { throw SearchQueryNullException() }
                    val listOfUser = repository.getAllUserByName(query)
                    if (listOfUser.isEmpty()) {
                        mediatorLiveData.postValue(ViewModelState.Error("User tidak ditemukan"))
                    } else {
                        val favUser = repository.getAllFavoriteUserAsList()
                        val updatedList = listOfUser.mapBasedOnFavoriteWith(favUser)
                        mediatorLiveData.postValue(ViewModelState.Success(updatedList))
                    }
                } catch (throwable: SearchQueryNullException) {
                    mediatorLiveData.postValue(ViewModelState.Error("Nama Tidak Boleh Kosong"))
                } catch (throwable: SocketTimeoutException) {
                    mediatorLiveData.postValue(ViewModelState.Error("Connection Error, Coba Lagi"))
                } catch (throwable: Throwable) {
                    mediatorLiveData.postValue(ViewModelState.Error("Unknown Error"))
                    throw throwable
                }
            }
        }
    }

    fun searchForUser(name: String) { currentQueryName.value = name }

}