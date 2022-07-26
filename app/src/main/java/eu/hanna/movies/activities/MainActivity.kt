package eu.hanna.movies.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import eu.hanna.movies.R
import eu.hanna.movies.database.MovieDatabase
import eu.hanna.movies.fragments.LatestFragment
import eu.hanna.movies.fragments.PopularFragment
import eu.hanna.movies.fragments.SearchFragment
import eu.hanna.movies.fragments.UpcomingFragment
import eu.hanna.movies.repository.MovieRepository
import eu.hanna.movies.viewmodel.MoviesViewModel
import eu.hanna.movies.viewmodel.MoviesViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieRepository = MovieRepository(MovieDatabase(this))
        val viewModelProviderFactory = MoviesViewModelProviderFactory(movieRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(MoviesViewModel::class.java)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = Navigation.findNavController(this, R.id.moviesNavHostFragment)
        NavigationUI.setupWithNavController(bottomNavigation,navController)

    }
    //  val transaction = supportFragmentManager.beginTransaction()
    //  transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    //  transaction.add(R.id.fragmentHost,PopularFragment(),"Popular").commit()

    /*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.fragment_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.popularFragment -> {
                val popularFragment = PopularFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.moviesNavHostFragment,popularFragment)
                fragmentTransaction.commit()
              //  val fragment: Fragment?= supportFragmentManager.findFragmentById(LatestFragment::class.java.simpleName)
            }
            R.id.latestFragment -> {
                val latestFragment = LatestFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.moviesNavHostFragment,latestFragment)
                fragmentTransaction.commit()
            }
            R.id.upcomingFragment -> {
                val upcomingFragment = UpcomingFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.moviesNavHostFragment,upcomingFragment)
                fragmentTransaction.commit()
            }
            R.id.searchFragment-> {
                val searchFragment = SearchFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.moviesNavHostFragment,searchFragment)
                fragmentTransaction.commit()
            }

        }
        return super.onOptionsItemSelected(item)

    }*/

}