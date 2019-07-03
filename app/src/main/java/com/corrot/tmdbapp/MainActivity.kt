package com.corrot.tmdbapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.corrot.tmdb_app.R
import com.corrot.tmdbapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var t: TextView
    lateinit var b: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        t = textView
        b = button

        val mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(MainViewModel::class.java)

        mViewModel.popularMoviesLiveData.observe(this, Observer {
            for (o in it) {
                t.text = "\n" + o.title + t.text + "\n"
            }
        })

        b.setOnClickListener {
            t.text = "_"
            mViewModel.fetchMovies()
        }
    }
}
