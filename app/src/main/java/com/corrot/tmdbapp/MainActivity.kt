package com.corrot.tmdbapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.corrot.tmdb_app.R
import com.corrot.tmdbapp.ui.MovieListFragment
import com.corrot.tmdbapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var moviesSearchBar: AutoCompleteTextView
    private lateinit var moviesFragmentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesSearchBar = actv_main
        moviesFragmentContainer = fl_main_fragment

        val mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val searchAdapter =
            ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item)

        moviesSearchBar.setAdapter(searchAdapter)
        moviesSearchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(name: Editable?) {
                name?.let { if (name.length > 1) mViewModel.setQueryInput(name.toString()) }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        mViewModel.userQueryOutput.observe(this, Observer {
            searchAdapter.clear()
            searchAdapter.addAll(it)
            // to force the data to show
            // https://stackoverflow.com/a/19667522/10559761
            searchAdapter.filter.filter(moviesSearchBar.text, moviesSearchBar)
        })

        supportFragmentManager.beginTransaction()
            .add(R.id.fl_main_fragment, MovieListFragment(), "popular")
            .commit()
    }
}


