package com.corrot.tmdbapp

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corrot.tmdb_app.R
import kotlinx.android.synthetic.main.movie_item.view.*

class PagedMoviesAdapter :
    PagedListAdapter<Movie, PagedMoviesAdapter.MovieHolder>(MovieDiffUtil()) {

    class MovieHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val view: View = v

        fun bind(m: Movie) {
            with(m) {
                view.tv_movie_title.text = title
                Glide
                    .with(view)
                    .load("http://image.tmdb.org/t/p/w342/$poster_path")
                    .placeholder(R.drawable.ic_block_black_24dp)
                    .into(view.iv_movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflatedView = parent.inflate(R.layout.movie_item, false)
        return MovieHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}
