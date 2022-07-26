package eu.hanna.movies.repository

import eu.hanna.movies.api.RetrofitInstance
import eu.hanna.movies.database.MovieDatabase
import eu.hanna.movies.models.Result

class MovieRepository(val db: MovieDatabase) {

    suspend fun getPopularMovies(language: String,pageNumber: Int) =
        RetrofitInstance.api.getPopularMovies(language,pageNumber)

    suspend fun getUpcomingMovies(language: String,pageNumber: Int) =
        RetrofitInstance.api.getUpcomingMovies(language,pageNumber)

    suspend fun getLatestMovies(language: String,pageNumber: Int) =
        RetrofitInstance.api.getLatestMovies(language, pageNumber)

    suspend fun searchMovies(searchQuery: String,pageNumber: Int) =
        RetrofitInstance.api.searchMovies(searchQuery, pageNumber)

    suspend fun upsert(movie: Result) = db.getMovieDao().upsert(movie)

    suspend fun delete(movie: Result) =db.getMovieDao().delete(movie)

    fun getAllMovies() = db.getMovieDao().getAllMovies()
}