package com.nomadapps.watchlist.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.nomadapps.watchlist.model.SeriesResult
import com.nomadapps.watchlist.view.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_row_design.view.*

class SerieListRecyclerViewAdapter(
    private var items: ArrayList<SeriesResult>,
    val onClickListener: OnClickListener
) : RecyclerView.Adapter<SerieListRecyclerViewAdapter.MyViewHolder>(), Filterable {

    interface OnClickListener {
        fun onItemClick(position: SeriesResult)
    }

    var serieList = ArrayList<SeriesResult>()

    init {
        serieList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row_design, parent, false)
        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return serieList.size;
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = serieList[position]
        holder.itemView.nameOfMovie.text = currentItem.name
        //holder.itemView.rating.text = "Rate - " + currentItem.vote_average
        //holder.itemView.date.text = "Released Date - " + currentItem.release_date
        holder.itemView.tvSearchMovieDescription.text = currentItem.overview
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+currentItem.backdrop_path).into(holder.itemView.typeImageView)
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+currentItem.poster_path).into(holder.itemView.ivSearchPoster)
        holder.itemView.rowLayoutList.setOnClickListener {
            onClickListener.onItemClick(currentItem)

        }

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    serieList = items
                } else {
                    val resultList = ArrayList<SeriesResult>()
                    for (row in items) {
                        if (row.name.lowercase().contains(charSearch.lowercase())) {
                            resultList.add(row)
                        }
                    }
                    serieList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = serieList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null) {
                    serieList = results.values as ArrayList<SeriesResult>
                    notifyDataSetChanged()
                }

            }

        }
    }
}