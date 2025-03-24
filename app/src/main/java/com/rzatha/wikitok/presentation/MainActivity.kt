package com.rzatha.wikitok.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearSnapHelper
import com.rzatha.wikitok.R
import com.rzatha.wikitok.databinding.ActivityMainBinding
import com.rzatha.wikitok.domain.ArticlePreviewItem
import com.rzatha.wikitok.domain.Image

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val articleAdapter = ArticleAdapter()
    private val snapHelper = LinearSnapHelper()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        snapHelper.attachToRecyclerView(binding.rvArticles)

        val tempArtList = mutableListOf<ArticlePreviewItem>()
        for (i in 0 until 100) {
            tempArtList.add(ArticlePreviewItem(
                i,
                "Title $i",
                "Long long text $i",
                source= "",
                image = Image(
                    "",
                    ""
                )
            ))
        }
        articleAdapter.articleList = tempArtList
    }

    private fun setupRecyclerView() {


        with(binding.rvArticles){
            adapter = articleAdapter
        }
    }

}