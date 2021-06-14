package com.nomadapps.watchlist.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nomadapps.watchlist.model.SeriesResult
import com.nomadapps.watchlist.view.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_viewpager_design.view.*

class SerieListViewPagerAdapter(
    private var items: ArrayList<SeriesResult>,
    val onClickListener: OnClickListener
) : RecyclerView.Adapter<SerieListViewPagerAdapter.MyViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: SeriesResult)
    }

    var serieList = ArrayList<SeriesResult>()

    init {
        serieList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_viewpager_design, parent, false)
        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return serieList.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = serieList[position]
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+currentItem.backdrop_path).into(holder.itemView.imageView)
        holder.itemView.movieBannerTextView.text = currentItem.name


        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(currentItem)
        }

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

}