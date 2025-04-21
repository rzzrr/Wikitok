package com.rzatha.wikitok.presentation.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.ViewModelProvider
import com.rzatha.wikitok.databinding.ActivityFavouriteBinding
import com.rzatha.wikitok.domain.Article
import com.rzatha.wikitok.presentation.adapter.FavouriteArticleAdapter
import com.rzatha.wikitok.presentation.viewmodel.FavouriteViewModel

class FavouriteActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFavouriteBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: FavouriteViewModel
    private val favouriteAdapter = FavouriteArticleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViewModel()
        setupAdapter()
        setupFilter()
    }

    private fun setupFilter() {
        binding.svFilter.setOnQueryTextListener(
            object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(title: String?): Boolean {
                    viewModel.filter(title.orEmpty())
                    return true
                }
            }
        )
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]

        viewModel.getArticleListFromDb.observe(this) {
            viewModel.setOriginalList(it)
        }

        viewModel.filteredList.observe(this) {
            favouriteAdapter.submitList(it)
        }
    }

    private fun setupAdapter() {
        binding.rvFavourites.adapter = favouriteAdapter

        favouriteAdapter.onItemClickListener =
            object : FavouriteArticleAdapter.OnItemClickListener {
                override fun onItemClick(article: Article) {
                    if (isNetworkAvailable()) {
                        startActivity(
                            ArticleDetailActivity.newIntent(this@FavouriteActivity, article.id)
                        )
                    } else {
                        Toast.makeText(
                            this@FavouriteActivity,
                            "There is no internet connection",
                            Toast.LENGTH_LONG).show();
                    }
                }
            }

        favouriteAdapter.onRemoveClickListener =
            object : FavouriteArticleAdapter.OnRemoveClickListener {
                override fun onRemoveClick(article: Article) {
                    viewModel.removeArticle(article)
                }
            }

    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, FavouriteActivity::class.java)
        }
    }

    private fun Context.isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(
            connectivityManager.activeNetwork
        )
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

}