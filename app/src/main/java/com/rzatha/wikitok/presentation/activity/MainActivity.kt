package com.rzatha.wikitok.presentation.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SimpleItemAnimator
import com.rzatha.wikitok.R
import com.rzatha.wikitok.databinding.ActivityMainBinding
import com.rzatha.wikitok.databinding.DialogMenuBinding
import com.rzatha.wikitok.domain.Article
import com.rzatha.wikitok.presentation.adapter.ArticleAdapter
import com.rzatha.wikitok.presentation.viewmodel.MainViewModel

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

        binding.ivMenu.setOnClickListener {
            showMenu()
        }
    }

    private fun observeViewModel() {
        viewModel.articleItemList.observe(this) {
            Log.d(TAG, "observeViewModel")
            articleAdapter.submitList(it)
        }
        viewModel.isLoading.observe(this) { loading ->
            if (loading) {
                Toast.makeText(this, "New data loading", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "New data loaded", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.favouriteArticleIdList.observe(this) {
            articleAdapter.favouriteArticlesId = it
        }
    }

    private fun setupRecyclerView() {
        snapHelper.attachToRecyclerView(binding.rvArticles)

        with(binding.rvArticles) {
            adapter = articleAdapter
            itemAnimator.apply {
                if (this is SimpleItemAnimator) {
                    this.supportsChangeAnimations = false
                }
            }
        }

        articleAdapter.onReachEndListener = object : ArticleAdapter.OnReachEndListener {
            override fun onReachEnd() {
                Log.d(TAG, "onReachEnd")
                viewModel.loadRandomResponse()
            }
        }
        articleAdapter.onItemClickListener = object : ArticleAdapter.OnItemClickListener {
            override fun onItemClick(articleId: Int) {
                startActivity(ArticleDetailActivity.newIntent(this@MainActivity, articleId))
            }
        }
        articleAdapter.onShareListener = object : ArticleAdapter.OnShareListener{

            override fun onShare(article: Article) {
                article.fullUrl?.let {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, article.title)
                        putExtra(Intent.EXTRA_TEXT, it)
                    }
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.share_using)))
                }

            }
        }
        articleAdapter.onFavouriteClickAdapter = object : ArticleAdapter.OnFavouriteClickAdapter {
            override fun onFavouriteClick(containsInDb: Boolean, article: Article) {
                if (containsInDb) {
                    viewModel.removeFromDb(article)
                } else {
                    viewModel.addToDb(article)
                }
            }
        }
    }

    private fun showMenu() {
        val bindingDialog = DialogMenuBinding.inflate(layoutInflater)

        val dialog = Dialog(this, R.style.RoundedDialogTheme)

        with(dialog) {
            setContentView(bindingDialog.root)
            setCancelable(true)
        }

        bindingDialog.buttonFavourites.setOnClickListener {
            startActivity(FavouriteActivity.newIntent(this))
            dialog.dismiss()
        }

        bindingDialog.buttonBack.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}