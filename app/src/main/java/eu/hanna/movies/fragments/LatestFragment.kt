package eu.hanna.movies.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.recyclerview.widget.GridLayoutManager
import eu.hanna.movies.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import eu.hanna.movies.activities.DetailMoviesActivity
import eu.hanna.movies.activities.MainActivity
import eu.hanna.movies.adapters.MoviesAdapter
import eu.hanna.movies.databinding.FragmentLatestBinding
import eu.hanna.movies.util.Constants
import eu.hanna.movies.util.Constants.Companion.DATE
import eu.hanna.movies.util.Constants.Companion.IMAGE
import eu.hanna.movies.util.Constants.Companion.LANGUAGE
import eu.hanna.movies.util.Constants.Companion.OVERVIEW
import eu.hanna.movies.util.Constants.Companion.TITLE
import eu.hanna.movies.util.NetworkResource
import eu.hanna.movies.viewmodel.MoviesViewModel

class LatestFragment : Fragment() {

    private lateinit var binding: FragmentLatestBinding

    lateinit var movieAdapter: MoviesAdapter

    lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLatestBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()
        viewModel.getLatestMovies("en-US")
        observeLatestMovieFromViewModel()
        onMovieItemClick()
    }

    // set up RecyclerView
    private fun setupRecyclerView() {
        movieAdapter = MoviesAdapter()
        binding.rvLatest.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)

            // handle the pagnitation
            addOnScrollListener(this@LatestFragment.scrollListener)
        }
    }

    //  observe the livedata from viewmodel
    private fun observeLatestMovieFromViewModel(){
        viewModel.latestMovies.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is NetworkResource.Success -> {
                    hideProgressBar()

                    // checking if the response data is not null
                    response.data?.let { moviesResponse ->
                        movieAdapter.differ.submitList(moviesResponse.results.toList())

                        // handle the pagination
                        val totalPages = moviesResponse.total_pages / Constants.QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.latestMoviesPage == totalPages
                        if(isLastPage) {
                            binding.rvLatest.setPadding(0, 0, 0, 0)
                        }
                    }
                }
                is NetworkResource.Error -> {
                    hideProgressBar()

                    response.message?.let { message ->
                        Log.e("latestMovies", "An error occured: $message")
                    }

                }
                is NetworkResource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    // A function to onItemClickListener in recyclerview
    private fun onMovieItemClick() {
        movieAdapter.onItemClick = { movies ->
            var intent = Intent(context, DetailMoviesActivity::class.java)
            intent.putExtra(IMAGE,movies.backdrop_path)
            intent.putExtra(TITLE,movies.original_title)
            intent.putExtra(LANGUAGE,movies.original_language)
            intent.putExtra(DATE,movies.release_date)
            intent.putExtra(OVERVIEW,movies.overview)
            startActivity(intent)

        }
    }


    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    // handle pagination
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate =  isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                viewModel.getLatestMovies("en-US")
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

}