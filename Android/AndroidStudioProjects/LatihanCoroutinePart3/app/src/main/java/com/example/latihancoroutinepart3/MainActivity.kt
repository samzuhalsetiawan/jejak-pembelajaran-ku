package com.example.latihancoroutinepart3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihancoroutinepart3.adapters.MovieCardRvAdapter
import com.example.latihancoroutinepart3.databinding.ActivityMainBinding
import com.example.latihancoroutinepart3.models.Movie
import com.example.latihancoroutinepart3.singleton.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieCardRvAdapter: MovieCardRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieCardRvAdapter = MovieCardRvAdapter()
        binding.rvListFilm.apply {
            adapter = movieCardRvAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        binding.btnCariFilm.setOnClickListener { btnCariFilmHandler() }
    }

    private fun btnCariFilmHandler() {
        val query = binding.etSearchbar.text.toString()

        lifecycleScope.launch(Dispatchers.IO) {
            val listMovies = searchMovies(query)
            withContext(Dispatchers.Main) {
                movieCardRvAdapter.listMovies = listMovies
            }
        }
    }

    private suspend fun searchMovies(query: String): List<Movie> {
        val response = Api.imdbApi.searchMovie(Api.IMDB_API_KEY, query)
        if (response.isSuccessful && response.body() != null) {
            val imdbResponse = response.body()
            imdbResponse?.let { return it.results }
        }
        return emptyList()
    }
}