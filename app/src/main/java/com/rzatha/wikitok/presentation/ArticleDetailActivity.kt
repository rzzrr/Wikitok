package com.rzatha.wikitok.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rzatha.wikitok.R
import com.rzatha.wikitok.databinding.ActivityArticleDetailBinding

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