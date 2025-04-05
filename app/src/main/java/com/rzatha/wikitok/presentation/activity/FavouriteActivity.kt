package com.rzatha.wikitok.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
            object : OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(title: String?): Boolean {
                    viewModel.filter(title.orEmpty())
                    return true
                }
            }
        )
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]

        viewModel.getArticleListFromDb.observe(this) {
            viewModel.setOriginalList(it)
        }

        viewModel.filteredList.observe(this){
            favouriteAdapter.submitList(it)
        }
    }

    private fun setupAdapter(){
        binding.rvFavourites.adapter = favouriteAdapter

        favouriteAdapter.onItemClickListener =
            object : FavouriteArticleAdapter.OnItemClickListener {
                override fun onItemClick(article: Article) {
                    startActivity(
                        ArticleDetailActivity.newIntent(this@FavouriteActivity, article.id)
                    )
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

}