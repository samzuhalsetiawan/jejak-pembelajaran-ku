package com.example.storyapp.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.data.source.UserStory
import com.example.storyapp.domain.interfaces.StoriesRepository
import com.example.storyapp.domain.sealed_class.ResponseStatus
import com.example.storyapp.presentation.home.util.DummyDataTest
import com.example.storyapp.presentation.home.util.DummyDataTest.toStoryPager
import com.example.storyapp.presentation.home.util.LiveDataTestUtil.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Rule
import org.mockito.Mockito

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storiesRepository: StoriesRepository
    private lateinit var homeViewModel: HomeViewModel
    private val dummyStory = DummyDataTest.dummyStory(30)

    @Before
    fun setUp() {
        /**
         * [HomeViewModel] adalah viewmodel yang digunakan oleh [HomeFragment]
         * didalamnya terdapat method [HomeViewModel::getAllStories()] untuk menyediakan list of story dari pager
         * method tersebut dipanggil dari [HomeFragment] untuk menampilkan list of story
         */
        homeViewModel = HomeViewModel(storiesRepository)
    }

    @Test
    fun `when getAllStories Should Not Null and Return ResponseStatus Success`() {
//        Ekspektasi method getAllStories() mengembalikan list of story
        val expectedStory = MutableLiveData<ResponseStatus<List<UserStory>>>()
        expectedStory.value = ResponseStatus.Success(listOf(dummyStory.toStoryPager()))
        `when`(storiesRepository.getAllStories()).thenReturn(expectedStory)
        val actualStory = homeViewModel.getPagerStory().getOrAwaitValue()

//        Ekspektasi method getAllStories(9999999) mengembalikan empty list
        val expectedZeroStory = MutableLiveData<ResponseStatus<List<UserStory>>>()
        expectedZeroStory.value = ResponseStatus.Success(emptyList())
        `when`(storiesRepository.getAllStories(9999999)).thenReturn(expectedZeroStory)
        val actualStory2 = homeViewModel.getPagerStory(9999999).getOrAwaitValue()

//        Memastikan method getAllStories telah dipanggil
        Mockito.verify(storiesRepository).getAllStories()
//        actual story tidak boleh null
        assertNotNull(actualStory)
//        memastikan response success
        assertTrue(actualStory is ResponseStatus.Success)
//        list size harus sesuai dengan yg di inginkan
        assertEquals(dummyStory.size, (actualStory as ResponseStatus.Success).data.first().size)
//        memastikan data pertama yang dikembalikan sesuai
        assertEquals((expectedStory.value as ResponseStatus.Success).data[0], actualStory.data[0])
//        memastikan data nol ketika tidak ada data cerita
        assertTrue((actualStory2 as ResponseStatus.Success).data.isEmpty())
    }

    @Test
    fun `when Network Error Should Return Error`() {

//        Ekspektasi method getAllStories() mengembalikan response status error
        val errorResult = MutableLiveData<ResponseStatus<List<UserStory>>>()
        errorResult.value = ResponseStatus.Error(Throwable("Error"))
        `when`(storiesRepository.getAllStories()).thenReturn(errorResult)
        val actualError = homeViewModel.getPagerStory().getOrAwaitValue()

//        Memastikan method getAllStories telah dipanggil
        Mockito.verify(storiesRepository).getAllStories()
//        actual story tidak boleh null
        assertNotNull(actualError)
//        Response statusnya harus success
        assertTrue(actualError is ResponseStatus.Error)
    }
}