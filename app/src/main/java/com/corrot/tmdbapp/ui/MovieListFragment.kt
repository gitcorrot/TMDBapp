package com.corrot.tmdbapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.corrot.tmdb_app.R
import com.corrot.tmdbapp.PagedMovieListAdapter
import com.corrot.tmdbapp.api.LoadState
import com.corrot.tmdbapp.paging.MoviesDataFactory
import com.corrot.tmdbapp.viewmodel.MovieListViewModel
import com.corrot.tmdbapp.viewmodel.MovieListViewModelFactory
import kotlinx.android.synthetic.main.fragment_movies.*

class MovieListFragment : Fragment() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var moviesPageTextView: TextView
    private lateinit var moviesProgressBar: ProgressBar
    private lateinit var layoutManager: GridLayoutManager

    var totalPages: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = srl_main
        moviesRecyclerView = rv_movies
        moviesPageTextView = tv_page
        moviesProgressBar = pb_movies
        layoutManager = GridLayoutManager(activity, 2)

        val mViewModel by lazy {
            ViewModelProviders.of(
                this,
                MovieListViewModelFactory(MoviesDataFactory.MoviesDataType.POPULAR)
            ).get(MovieListViewModel::class.java)
        }

        moviesRecyclerView.layoutManager = layoutManager
        val adapter = PagedMovieListAdapter()
        moviesRecyclerView.adapter = adapter

        mViewModel.popularMoviesLiveData.observe(this, Observer {
            adapter.submitList(it)
        })

        mViewModel.loadingState.observe(this, Observer {
            when (it) {
                LoadState.LOADED -> moviesProgressBar.visibility = View.GONE
                else -> moviesProgressBar.visibility = View.VISIBLE
            }
        })

        mViewModel.totalPages.observe(this, Observer {
            this.totalPages = it
        })

        moviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val page = 1 + adapter.lastItemPosition / 20
                moviesPageTextView.text = "Page: $page / $totalPages"
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            // TODO implement refreshing
        }
    }
}
