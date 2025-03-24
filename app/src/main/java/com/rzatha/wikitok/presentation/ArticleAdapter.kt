package com.rzatha.wikitok.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rzatha.wikitok.databinding.ArticlePreviewItemBinding
import com.rzatha.wikitok.domain.ArticlePreviewItem

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    var articleList: List<ArticlePreviewItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticlePreviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articleList[position]
        with(holder.binding){
            with(article){
                tvTitle.text = title
                tvArticleText.text = text
            }
        }
    }

    override fun getItemCount() = articleList.size


    class ArticleViewHolder(val binding: ArticlePreviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}