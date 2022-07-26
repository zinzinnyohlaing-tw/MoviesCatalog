package eu.hanna.movies.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Result(
    @PrimaryKey(autoGenerate = true)
    var movieId: Int? = null,
    val adult: Boolean,
    val backdrop_path: String,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)