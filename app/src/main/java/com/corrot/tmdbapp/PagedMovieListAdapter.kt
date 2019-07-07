package com.corrot.tmdbapp

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corrot.tmdb_app.R
import com.corrot.tmdbapp.utils.AppConstants.Companion.POSTER_154_BASE_URL
import com.corrot.tmdbapp.utils.inflate
import kotlinx.android.synthetic.main.movie_item.view.*

class PagedMovieListAdapter :
    PagedListAdapter<Movie, PagedMovieListAdapter.MovieHolder>(MovieDiffUtil()) {

    var lastItemPosition = -1

    class MovieHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val view: View = v

        fun bind(m: Movie) {
            with(m) {
                view.tv_movie_title.text = title
                Glide
                    .with(view)
                    .load("$POSTER_154_BASE_URL$poster_path")
                    .into(view.iv_movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflatedView = parent.inflate(R.layout.movie_item, false)
        return MovieHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        lastItemPosition = position
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}
