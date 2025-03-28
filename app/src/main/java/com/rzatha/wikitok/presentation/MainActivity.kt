package com.rzatha.wikitok.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearSnapHelper
import com.rzatha.wikitok.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val articleAdapter = ArticleAdapter()
    private val snapHelper = LinearSnapHelper()
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.articlePreviewItemList.observe(this){
            Log.d(TAG, "observeViewModel")
            articleAdapter.articleList = it
        }
    }

    private fun setupRecyclerView() {
        snapHelper.attachToRecyclerView(binding.rvArticles)
        with(binding.rvArticles){
            adapter = articleAdapter
        }
        articleAdapter.onReachEndListener = object : ArticleAdapter.OnReachEndListener{
            override fun onReachEnd() {
                Log.d(TAG, "onReachEnd")
                viewModel. loadRandomResponse()
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}