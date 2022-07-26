package eu.hanna.movies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.hanna.movies.models.MoviesResponse
import eu.hanna.movies.util.NetworkResource

import eu.hanna.movies.repository.MovieRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MoviesViewModel(val moviesRepository: MovieRepository) : ViewModel() {

    val popularMovies: MutableLiveData<NetworkResource<MoviesResponse>> = MutableLiveData()
    var popularMoviesPage = 1
    var popularMoviesResponse: MoviesResponse? = null

    val upComingMovies: MutableLiveData<NetworkResource<MoviesResponse>> = MutableLiveData()
    var upComingMoviesPage = 1
    var upComingMoviesResponse: MoviesResponse? = null

    val latestMovies: MutableLiveData<NetworkResource<MoviesResponse>> = MutableLiveData()
    var latestMoviesPage = 1
    var latestMoviesResponse: MoviesResponse? = null

    /*init {
        getPopularMovies("en-US")
        getUpcomingMovies("en-US")
    }*/

    fun getPopularMovies(language: String) = viewModelScope.launch {
        popularMovies.postValue(NetworkResource.Loading())

        val response = moviesRepository.getPopularMovies(language, popularMoviesPage)
        popularMovies.postValue(handlePopularMoviesResponse(response))
    }

    fun getUpcomingMovies(language: String) = viewModelScope.launch {
        upComingMovies.postValue(NetworkResource.Loading())

        val response = moviesRepository.getUpcomingMovies(language, upComingMoviesPage)
        upComingMovies.postValue(handleUpcomingMoviesResponse(response))
    }

    fun getLatestMovies(language: String) = viewModelScope.launch {
        latestMovies.postValue(NetworkResource.Loading())

        val response = moviesRepository.getLatestMovies(language, latestMoviesPage)
        latestMovies.postValue(handleLatestMoviesResponse(response))
    }

    private fun handlePopularMoviesResponse(response: Response<MoviesResponse>): NetworkResource<MoviesResponse> {
        if (response.isSuccessful) {
            // check if the response is not null
            response.body()?.let { resultResponse ->

                // if the response is not null increase the page number
                popularMoviesPage++

                // check if the response is null load the first page
                if (popularMoviesResponse == null) {
                    popularMoviesResponse = resultResponse
                } else {
                    val oldArticles = popularMoviesResponse?.results
                    val newArticles = resultResponse.results
                    oldArticles?.addAll(newArticles)
                }
                return NetworkResource.Success(popularMoviesResponse ?: resultResponse)
            }
        }
        return NetworkResource.Error(response.message())
    }

    private fun handleLatestMoviesResponse(response: Response<MoviesResponse>): NetworkResource<MoviesResponse> {
        if (response.isSuccessful) {
            // check if the response is not null
            response.body()?.let { resultResponse ->

                // if the response is not null increase the page number
               latestMoviesPage++

                // check if the response is null load the first page
                if (latestMoviesResponse == null) {
                    latestMoviesResponse  = resultResponse
                } else {
                    val oldArticles = latestMoviesResponse ?.results
                    val newArticles = resultResponse.results
                    oldArticles?.addAll(newArticles)
                }
                return NetworkResource.Success(latestMoviesResponse ?: resultResponse)
            }
        }
        return NetworkResource.Error(response.message())
    }

    private fun handleUpcomingMoviesResponse(response: Response<MoviesResponse>): NetworkResource<MoviesResponse> {
        if (response.isSuccessful) {
            // check if the response is not null
            response.body()?.let { resultResponse ->

                // if the response is not null increase the page number
                upComingMoviesPage++

                // check if the response is null load the first page
                if (upComingMoviesResponse == null) {
                    upComingMoviesResponse = resultResponse
                } else {
                    val oldArticles = upComingMoviesResponse?.results
                    val newArticles = resultResponse.results
                    oldArticles?.addAll(newArticles)
                }
                return NetworkResource.Success(upComingMoviesResponse ?: resultResponse)
            }
        }
        return NetworkResource.Error(response.message())
    }
}