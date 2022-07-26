package eu.hanna.movies.models

data class MoviesResponse(
    val page: Int,
    val results: MutableList<Result>,
    val total_pages: Int,
    val total_results: Int
)