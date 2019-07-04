package com.corrot.tmdbapp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.corrot.tmdb_app.R
import com.corrot.tmdbapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var popularMoviesRecyclerView: RecyclerView
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var popularMoviesPageTextView: TextView
    private lateinit var popularMoviesProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefreshLayout = srl_main
        popularMoviesRecyclerView = rv_popular
        popularMoviesPageTextView = tv_page
        popularMoviesProgressBar = pb_main
        layoutManager = GridLayoutManager(this, 2)

        val adapter = PopularMoviesAdapter(ArrayList())
        popularMoviesRecyclerView.layoutManager = layoutManager
        popularMoviesRecyclerView.adapter = adapter

        val mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(MainViewModel::class.java)

        mViewModel.popularMoviesLiveData.observe(this, Observer {
            adapter.setMovies(it)
            popularMoviesProgressBar.visibility = View.GONE
            swipeRefreshLayout.isRefreshing = false
        })

        mViewModel.popularMoviesPageLiveData.observe(this, Observer {
            popularMoviesPageTextView.text = "Page: $it"
        })

        swipeRefreshLayout.setOnRefreshListener {
            mViewModel.fetchMovies()
        }

        // init
        mViewModel.fetchMovies()
        popularMoviesProgressBar.visibility = View.VISIBLE
    }
}
