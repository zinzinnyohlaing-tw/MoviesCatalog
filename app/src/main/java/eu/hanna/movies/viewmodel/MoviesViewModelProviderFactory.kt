package eu.hanna.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.hanna.movies.repository.MovieRepository

class MoviesViewModelProviderFactory(val moviesRepository: MovieRepository) : ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesViewModel(moviesRepository) as T
    }
}