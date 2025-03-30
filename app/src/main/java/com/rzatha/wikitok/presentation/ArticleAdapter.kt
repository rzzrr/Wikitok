package com.rzatha.wikitok.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rzatha.wikitok.R
import com.rzatha.wikitok.databinding.ArticlePreviewItemBinding
import com.rzatha.wikitok.domain.ArticlePreviewItem

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    var onReachEndListener: OnReachEndListener? = null
    var onItemClickListener: OnItemClickListener? = null

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

        with(holder.binding) {
            with(article) {

                if (imageUrl != null) {
                    Log.d("MainActivity", "Image url: ${article.imageUrl}\nArticle title: ${article.title}\n" +
                            "Article id: ${article.id}")
                    Glide.with(holder.itemView)
                        .load(article.imageUrl)
                        .placeholder(R.drawable.ic_heart_filled)
                        .into(ivArticle)
                } else {
                    ivArticle.setImageResource(R.drawable.ggb)
                }

                tvTitle.text = title
                tvArticleText.text = extractText

                if (position >= articleList.size - 2) {
                    onReachEndListener?.onReachEnd()
                }

                root.setOnClickListener {
                    onItemClickListener?.onItemClick(id)
                }
            }
        }
    }

    override fun onViewRecycled(holder: ArticleViewHolder) {
        super.onViewRecycled(holder)
        with(holder.binding) {
            tvTitle.text = ""
            tvArticleText.text = ""
            ivArticle.setImageDrawable(null)
        }
    }

    override fun getItemCount() = articleList.size

    class ArticleViewHolder(val binding: ArticlePreviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnReachEndListener {
        fun onReachEnd()
    }

    interface OnItemClickListener {
        fun onItemClick(articleId: Int)
    }
}