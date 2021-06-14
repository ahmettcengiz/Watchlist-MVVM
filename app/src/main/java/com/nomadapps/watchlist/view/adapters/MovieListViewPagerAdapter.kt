package com.nomadapps.watchlist.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nomadapps.watchlist.model.Result
import com.nomadapps.watchlist.view.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_viewpager_design.view.*

class MovieListViewPagerAdapter(
    private var items: ArrayList<Result>,
    val onClickListener: OnClickListener
) : RecyclerView.Adapter<MovieListViewPagerAdapter.MyViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: Result)
    }

    var movieList = ArrayList<Result>()

    init {
        movieList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_viewpager_design, parent, false)
        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = movieList[position]
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + currentItem.backdrop_path)
            .into(holder.itemView.imageView)
        holder.itemView.movieBannerTextView.text = currentItem.title


        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(currentItem)
        }

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

}