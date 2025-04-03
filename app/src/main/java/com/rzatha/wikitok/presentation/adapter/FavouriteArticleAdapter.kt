package com.rzatha.wikitok.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rzatha.wikitok.R
import com.rzatha.wikitok.databinding.FavouriteArticleItemBinding
import com.rzatha.wikitok.domain.Article

class FavouriteArticleAdapter : ListAdapter<Article, FavouriteArticleAdapter.ArticleViewHolder>(
    ArticleItemDiffCallback()
) {

    var onRemoveClickListener: OnRemoveClickListener? = null
    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = FavouriteArticleItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)

        with(holder.binding) {
            with(article) {

                if (imageUrl != null) {

                    Glide.with(holder.itemView)
                        .load(article.imageUrl)
                        .placeholder(R.drawable.image_placeholder)
                        .into(ivArticleImage)

                } else {
                    ivArticleImage.setImageResource(R.drawable.golden_gate_bridge)
                }

                tvTitle.text = title

                root.setOnClickListener {
                    onItemClickListener?.onItemClick(article)
                }

                ivRemove.setOnClickListener {
                    onRemoveClickListener?.onRemoveClick(article)
                }

            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(article: Article)
    }

    interface OnRemoveClickListener {
        fun onRemoveClick(article: Article)
    }

    class ArticleViewHolder(val binding: FavouriteArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root)


}