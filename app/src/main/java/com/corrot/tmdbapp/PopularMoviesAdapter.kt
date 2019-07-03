package com.corrot.tmdbapp

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corrot.tmdb_app.R
import kotlinx.android.synthetic.main.movie_item.view.*

class PopularMoviesAdapter(private var movies: List<TmdbMovie>) :
    RecyclerView.Adapter<PopularMoviesAdapter.MovieHolder>() {

    class MovieHolder(v: View) : RecyclerView.ViewHolder(v) {
        val view: View = v
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieHolder {
        val inflatedView = parent.inflate(R.layout.movie_item, false)
        return MovieHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.view.tv_movie_title.text = movies[position].title

        val poster = movies[position].poster_path
        Glide
            .with(holder.view)
            .load("http://image.tmdb.org/t/p/w342/$poster")
            .placeholder(R.drawable.ic_block_black_24dp)
            .into(holder.view.iv_movie)
    }

    override fun getItemCount(): Int = movies.size

    // todo use coroutines?
    fun setMovies(newMovies: List<TmdbMovie>) {
        val diff = notifyChanges(newMovies, movies)
        movies = newMovies
        diff.dispatchUpdatesTo(this)
    }

    private fun notifyChanges(
        newMovies: List<TmdbMovie>, oldMovies: List<TmdbMovie>
    ): DiffUtil.DiffResult {
        return DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldMovies[oldItemPosition].id == newMovies[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldMovies[oldItemPosition] == newMovies[newItemPosition]

            override fun getOldListSize() = oldMovies.size
            override fun getNewListSize() = newMovies.size
        })
    }
}