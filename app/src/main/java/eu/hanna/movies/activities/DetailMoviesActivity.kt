package eu.hanna.movies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import eu.hanna.movies.R
import eu.hanna.movies.database.MovieDatabase
import eu.hanna.movies.databinding.ActivityDetailMoviesBinding
import eu.hanna.movies.fragments.PopularFragment
import eu.hanna.movies.viewmodel.MoviesViewModel
import eu.hanna.movies.models.Result
import eu.hanna.movies.repository.MovieRepository
import eu.hanna.movies.util.Constants.Companion.DATE
import eu.hanna.movies.util.Constants.Companion.IMAGE
import eu.hanna.movies.util.Constants.Companion.IMAGE_BASE
import eu.hanna.movies.util.Constants.Companion.LANGUAGE
import eu.hanna.movies.util.Constants.Companion.OVERVIEW
import eu.hanna.movies.util.Constants.Companion.TITLE
import eu.hanna.movies.viewmodel.MoviesViewModelProviderFactory

class DetailMoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMoviesBinding

    lateinit var viewModel: MoviesViewModel

    private lateinit var movies: Result

    private lateinit var image: String
    private lateinit var title: String
    private lateinit var language: String
    private lateinit var releasedDate: String
    private lateinit var overview: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val moviesRepository = MovieRepository(MovieDatabase(this))
        val viewModelProviderFactory = MoviesViewModelProviderFactory(moviesRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(MoviesViewModel::class.java)

        getMoviesDetails()

    }

    private fun getMoviesDetails(){
        val intent = intent
        image = intent.getStringExtra(IMAGE)!!
        title = intent.getStringExtra(TITLE)!!
        language = intent.getStringExtra(LANGUAGE)!!
        releasedDate = intent.getStringExtra(DATE)!!
        overview = intent.getStringExtra(OVERVIEW)!!

        Glide.with(applicationContext).load(IMAGE_BASE + image).into(binding.posterImg)
        binding.collapsingToolbar.title = title
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
        binding.tvLanguage.text = language
        binding.tvDate.text = releasedDate
        binding.content.text = overview


    }
}