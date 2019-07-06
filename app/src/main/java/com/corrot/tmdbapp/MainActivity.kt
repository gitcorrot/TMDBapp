package com.corrot.tmdbapp

import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.corrot.tmdb_app.R
import com.corrot.tmdbapp.api.LoadState
import com.corrot.tmdbapp.viewmodel.MoviesViewModel
import com.corrot.tmdbapp.viewmodel.MoviesViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var moviesSearchBar: AutoCompleteTextView
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var moviesPageTextView: TextView
    private lateinit var moviesProgressBar: ProgressBar
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefreshLayout = srl_main
        moviesSearchBar = actv_main
        moviesRecyclerView = rv_movies
        moviesPageTextView = tv_page
        moviesProgressBar = pb_main
        layoutManager = GridLayoutManager(this, 2)

        val mViewModel by lazy {
            ViewModelProviders.of(
                this,
                MoviesViewModelFactory(MoviesDataFactory.MoviesDataType.POPULAR)
            ).get(MoviesViewModel::class.java)
        }

        moviesRecyclerView.layoutManager = layoutManager
        val adapter = PagedMoviesAdapter()
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

//        val adapter = MoviesAdapter(ArrayList())
//        moviesRecyclerView.layoutManager = layoutManager
//        moviesRecyclerView.adapter = adapter
//
//        val mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//            .create(MainViewModel::class.java)
//
//        mViewModel.popularMoviesLiveData.observe(this, Observer {
//            it?.let {
//                adapter.setMovies(it)
//                moviesProgressBar.visibility = View.GONE
//                swipeRefreshLayout.isRefreshing = false
//            }
//        })
//
//        mViewModel.popularMoviesPageLiveData.observe(this, Observer {
//            it?.let {
//                moviesPageTextView.text = "Page: ${it.currentPage}  /  ${it.totalPages}"
//            }
//        })
//
//        swipeRefreshLayout.setOnRefreshListener {
//            mViewModel.fetchMovies()
//        }
//
//        val searchAdapter =
//            ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item)
//
//        moviesSearchBar.setAdapter(searchAdapter)
//        moviesSearchBar.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//                p0?.let { if (p0.length > 1) mViewModel.setQueryInput(p0.toString()) }
//            }
//
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//        })
//
//        mViewModel.userQueryOutput.observe(this, Observer {
//            searchAdapter.clear()
//            searchAdapter.addAll(it)
//            // to force the data to show
//            // https://stackoverflow.com/a/19667522/10559761
//            searchAdapter.filter.filter(moviesSearchBar.text, moviesSearchBar)
//        })
//
//        // init
//        mViewModel.fetchMovies()
//        moviesProgressBar.visibility = View.VISIBLE
    }
}

