package com.rzatha.wikitok.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearSnapHelper
import com.rzatha.wikitok.databinding.ActivityMainBinding
import com.rzatha.wikitok.domain.Article

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val snapHelper = LinearSnapHelper()
    private lateinit var viewModel: MainViewModel
    private val articleAdapter = ArticleAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setContentView(binding.root)
        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.articleItemList.observe(this){
            Log.d(TAG, "observeViewModel")
            articleAdapter.articleList = it
        }
        viewModel.isLoading.observe(this){loading ->
            if(loading) {
                Toast.makeText(this,"New data loading", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"New data loaded", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.favouriteArticleIdList.observe(this){
            articleAdapter.favouriteArticlesId = it
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
                viewModel.loadRandomResponse()
            }
        }
        articleAdapter.onItemClickListener = object : ArticleAdapter.OnItemClickListener{

            override fun onItemClick(articleId: Int) {
                startActivity(ArticleDetailActivity.newIntent(this@MainActivity, articleId))
            }
        }
        articleAdapter.onFavouriteClickAdapter = object : ArticleAdapter.OnFavouriteClickAdapter{
            override fun onFavouriteClick(containsInDb: Boolean, article: Article) {
                if (containsInDb) {
                    viewModel.removeFromDb(article)
                } else {
                    viewModel.addToDb(article)
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}