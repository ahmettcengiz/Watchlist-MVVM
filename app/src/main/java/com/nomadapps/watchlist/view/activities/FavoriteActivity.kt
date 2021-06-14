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
import com.nomadapps.watchlist.local.FavoriteViewModel
import com.nomadapps.watchlist.model.FavoriteMovieEntity
import com.nomadapps.watchlist.model.SeriesResult
import com.nomadapps.watchlist.view.R
import com.nomadapps.watchlist.view.adapters.FavoriteMovieListRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {
    private var adapter: FavoriteMovieListRecyclerViewAdapter? = null
    private lateinit var favoriteMovieViewModel: FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        favoriteMovieViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        bottomNavBar()
        fetchFavorites()
    }

    private fun fetchFavorites() {
        favoriteMovieViewModel.allFavoriteMovieDetail.observe(this, Observer { gamelist ->
            adapter = FavoriteMovieListRecyclerViewAdapter(
                gamelist,
                object : FavoriteMovieListRecyclerViewAdapter.OnClickListener {

                    override fun onItemClick(position: FavoriteMovieEntity) {
                        if (position.type == "movie") {
                            val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                            intent.putExtra("come", "Favorite")
                            intent.putExtra("key", position.id.toString())
                            startActivity(intent)
                        } else {
                            val intent = Intent(this@FavoriteActivity, SerieDetail::class.java)
                            val temp: SeriesResult = SeriesResult(
                                position.id,
                                position.title,
                                position.release_date,
                                position.poster_path,
                                position.vote_average,
                                position.backdrop_path,
                                position.overview
                            )
                            intent.putExtra("come", "Favorite")
                            intent.putExtra("key", temp)
                            startActivity(intent)
                        }

                    }
                })
            showEmptyError()
            searchFilter()
        })
    }

    private fun showEmptyError() {
        adapter!!.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
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
                empty.visibility = (if (adapter!!.itemCount == 0) View.VISIBLE else View.GONE)
            }
        })

        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
    }


    private fun searchFilter() {
        game_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.length > 2) {
                    adapter!!.filter.filter(newText)

                } else if (newText.isEmpty()) {
                    adapter!!.filter.filter(newText)
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
                    val intent = Intent(this@FavoriteActivity, MovieActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_series -> {
                    val intent = Intent(this@FavoriteActivity, SerieActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_favorites -> {
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
        val intent = Intent(this@FavoriteActivity, MovieActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }
}