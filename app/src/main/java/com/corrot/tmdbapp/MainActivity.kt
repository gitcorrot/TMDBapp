package com.corrot.tmdbapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corrot.tmdb_app.R
import com.corrot.tmdbapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var popularMoviesRecyclerView: RecyclerView
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        popularMoviesRecyclerView = rv_popular
        layoutManager = GridLayoutManager(this, 2)

        val adapter = PopularMoviesAdapter(ArrayList())
        popularMoviesRecyclerView.layoutManager = layoutManager
        popularMoviesRecyclerView.adapter = adapter

        val mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(MainViewModel::class.java)

        mViewModel.popularMoviesLiveData.observe(this, Observer {
            adapter.setMovies(it)
        })

        mViewModel.fetchMovies()
    }
}
