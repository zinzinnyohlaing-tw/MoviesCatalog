package eu.hanna.movies.api

import eu.hanna.movies.models.MoviesResponse
import eu.hanna.movies.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language")
        language: String,
        @Query("page")
        pageNumber: Int,
        @Query("api_key")
        api_key: String = API_KEY
    ) : Response<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language")
        language: String,
        @Query("page")
        pageNumber: Int,
        @Query("api_key")
        api_key: String = API_KEY
    ) : Response<MoviesResponse>

    @GET("movie/now_playing")
    suspend fun getLatestMovies(
        @Query("language")
        language: String,
        @Query("page")
        pageNumber: Int,
        @Query("api_key")
        api_key: String = API_KEY
    ) : Response<MoviesResponse>

    @GET("/search/movie")
    suspend fun searchMovies(
        @Query("query")
        searchQuery: String,
        @Query("page")
        pageNumber: Int,
        @Query("api_key")
        api_key: String = API_KEY
    ) : Response<MoviesResponse>
}