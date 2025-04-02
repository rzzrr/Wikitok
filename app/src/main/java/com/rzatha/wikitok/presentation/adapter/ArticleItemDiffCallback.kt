package com.rzatha.wikitok.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.rzatha.wikitok.domain.Article

class ArticleItemDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article)= oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem
}