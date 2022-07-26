package eu.hanna.movies.database

import androidx.lifecycle.LiveData
import androidx.room.*
import eu.hanna.movies.models.Result

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie: Result):Long

    @Delete
    suspend fun delete(movie: Result)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<Result>>

}