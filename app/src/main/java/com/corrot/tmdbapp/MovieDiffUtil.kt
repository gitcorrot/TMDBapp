package com.corrot.tmdbapp

import androidx.recyclerview.widget.DiffUtil

class MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.title == newItem.title
                && oldItem.release_date == newItem.release_date
                && oldItem.overview == newItem.overview
    }
}