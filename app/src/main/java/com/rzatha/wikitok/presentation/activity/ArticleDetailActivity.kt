package com.rzatha.wikitok.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rzatha.wikitok.databinding.ActivityArticleDetailBinding
import com.rzatha.wikitok.presentation.viewmodel.ArticleDetailViewModel
import com.rzatha.wikitok.presentation.viewmodel.ArticleDetailViewModelFactory
import com.rzatha.wikitok.presentation.WikiWebViewClient

class ArticleDetailActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityArticleDetailBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: ArticleDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intent.extras?.getInt(EXTRA_ARTICLE_ID)?.let {
            viewModel = ViewModelProvider(
                this,
                ArticleDetailViewModelFactory(it, application)
            )[ArticleDetailViewModel::class.java]
        } ?: throw RuntimeException("The required argument ArticleId was not passed ")


        viewModel.ldArticle.observe(this){
            binding.webView.loadDataWithBaseURL(
                "https://ru.wikipedia.org/",
                it.extractText ?: "",
                "text/html",
                "UTF-8",
                null
            )
        }

        binding.webView.webViewClient = WikiWebViewClient(this)

    }

    companion object {

        private const val EXTRA_ARTICLE_ID = "article_id"

        fun newIntent(context: Context, articleId: Int) =
            Intent(context, ArticleDetailActivity::class.java).apply {
                putExtra(EXTRA_ARTICLE_ID, articleId)
            }

    }


}