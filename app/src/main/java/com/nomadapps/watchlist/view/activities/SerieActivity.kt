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
import com.nomadapps.watchlist.model.SeriesModel
import com.nomadapps.watchlist.model.SeriesResult
import com.nomadapps.watchlist.view.R
import com.nomadapps.watchlist.view.adapters.SerieListRecyclerViewAdapter
import com.nomadapps.watchlist.view.adapters.SerieListViewPagerAdapter
import com.nomadapps.watchlist.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.activity_serie.*


class SerieActivity : AppCompatActivity() {
    private var adapterRecyclerView: SerieListRecyclerViewAdapter? = null
    private var adapterViewPager: SerieListViewPagerAdapter? = null
    private var seriesListRecyclerView: ArrayList<SeriesResult> = ArrayList()
    private var seriesListViewPager: ArrayList<SeriesResult> = ArrayList()

    private lateinit var serieListViewModel: MovieListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie)
        serieListViewModel = ViewModelProvider(this)[MovieListViewModel::class.java]
        bottomNavBar()
        fetchGameList()



    }
    private fun fetchGameList() {
        serieListViewModel.getSerieList()!!.observe(this, Observer<SeriesModel> { serielist ->
            if (seriesListRecyclerView.isEmpty()) {
                for (i in serielist.results.indices) {
                    if (i < 3) {
                        seriesListViewPager.add(serielist.results[i])
                    } else {
                        seriesListRecyclerView.add(serielist.results[i])
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
        adapterRecyclerView = SerieListRecyclerViewAdapter(
            seriesListRecyclerView,
            object : SerieListRecyclerViewAdapter.OnClickListener {
                override fun onItemClick(position: SeriesResult) {
                    val intent = Intent(this@SerieActivity, SerieDetail::class.java)
                    intent.putExtra("key", position)
                    startActivity(intent)
                }
            })
        recyclerview.isNestedScrollingEnabled = false
    }

    private fun setViewPagerAdapter() {
        adapterViewPager = SerieListViewPagerAdapter(
            seriesListViewPager,
            object : SerieListViewPagerAdapter.OnClickListener {
                override fun onItemClick(position: SeriesResult) {
                    val intent = Intent(this@SerieActivity, SerieDetail::class.java)
                    intent.putExtra("key", position)
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
                    val intent = Intent(this@SerieActivity, MovieActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_favorites -> {
                    val intent = Intent(this@SerieActivity, FavoriteActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_series -> {
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