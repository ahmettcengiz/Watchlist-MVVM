package com.nomadapps.watchlist.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nomadapps.watchlist.model.MovieModel
import com.nomadapps.watchlist.model.Result
import com.nomadapps.watchlist.view.R
import com.nomadapps.watchlist.view.adapters.MovieListRecyclerViewAdapter
import com.nomadapps.watchlist.view.adapters.MovieListViewPagerAdapter
import com.nomadapps.watchlist.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MovieActivity : AppCompatActivity() {
    private var adapterRecyclerView: MovieListRecyclerViewAdapter? = null
    private var adapterViewPager: MovieListViewPagerAdapter? = null
    private var movieListRecyclerView: ArrayList<Result> = ArrayList()
    private var movieListViewPager: ArrayList<Result> = ArrayList()

    private lateinit var movieListViewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movieListViewModel = ViewModelProvider(this)[MovieListViewModel::class.java]
        bottomNavBar()
        fetchGameList()
    }


    private fun fetchGameList() {
        movieListViewModel.getMovieList()!!.observe(this, Observer<MovieModel> { gamelist ->
            if (movieListRecyclerView.isEmpty()) {
                for (i in gamelist.results.indices) {
                    if (i < 3) {
                        movieListViewPager.add(gamelist.results[i])
                    } else {
                        movieListRecyclerView.add(gamelist.results[i])
                    }
                }
            }

            setRecylcerViewAdapter()
            showEmptyError()
            setViewPagerAdapter()
            searchFilter()

        })


    }

    private fun showEmptyError() {
        adapterRecyclerView!!.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            fun checkEmpty() {
                empty.visibility =
                    (if (adapterRecyclerView!!.itemCount == 0) View.VISIBLE else View.GONE)
            }
        })
        recyclerview.adapter = adapterRecyclerView
        recyclerview.layoutManager = LinearLayoutManager(this)
    }

    private fun setRecylcerViewAdapter() {
        adapterRecyclerView = MovieListRecyclerViewAdapter(
            movieListRecyclerView,
            object : MovieListRecyclerViewAdapter.OnClickListener {
                override fun onItemClick(position: Result) {
                    val intent = Intent(this@MovieActivity, DetailActivity::class.java)
                    val temp: String = position.id.toString()
                    intent.putExtra("key", temp)
                    startActivity(intent)
                }
            })
        recyclerview.isNestedScrollingEnabled = false
    }

    private fun setViewPagerAdapter() {
        adapterViewPager = MovieListViewPagerAdapter(
            movieListViewPager,
            object : MovieListViewPagerAdapter.OnClickListener {
                override fun onItemClick(position: Result) {
                    val intent = Intent(this@MovieActivity, DetailActivity::class.java)
                    val temp: String = position.id.toString()
                    intent.putExtra("key", temp)
                    startActivity(intent)
                }
            })
        viewPager.adapter = adapterViewPager
        worm_dots_indicator.setViewPager2(viewPager)
    }

    private fun searchFilter() {
        game_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.length > 2) {
                    adapterRecyclerView!!.filter.filter(newText)
                    viewPager.visibility = View.GONE
                    worm_dots_indicator.visibility = View.GONE
                } else if (newText.isEmpty()) {
                    adapterRecyclerView!!.filter.filter(newText)
                    viewPager.visibility = View.VISIBLE
                    worm_dots_indicator.visibility = View.VISIBLE

                }

                return false
            }

        })
    }


    private fun bottomNavBar() {
        bottom_navigation_bar.selectedItemId = R.id.navigation_homePage
        bottom_navigation_bar.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_homePage -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_favorites -> {
                    val intent = Intent(this@MovieActivity, FavoriteActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_series -> {
                    val intent = Intent(this@MovieActivity, SerieActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()
    }

}
