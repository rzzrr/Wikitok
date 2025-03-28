package com.rzatha.wikitok.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.DrawableTransformation
import com.rzatha.wikitok.R
import com.rzatha.wikitok.databinding.ArticlePreviewItemBinding
import com.rzatha.wikitok.domain.ArticlePreviewItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    var onReachEndListener: OnReachEndListener? = null

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
        Log.d("MainActivity", "onBindViewHolder")

        val article = articleList[position]

        with(holder.binding) {
            with(article) {

                if (imageUrl != null) {
                    Log.d("MainActivity", "Glide: ${article.imageUrl}")
                    Glide.with(holder.itemView)
                        .load(article.imageUrl)
                        .placeholder(R.drawable.ic_heart_filled)
                        .into(holder.binding.ivArticle)
                } else {
                    ivArticle.setImageResource(R.drawable.ggb)
                }

                tvTitle.text = title
                tvArticleText.text = extractText
                Log.d("MainActivity", "title text ${tvArticleText.text}")

                if (position >= articleList.size - 2) {
                    onReachEndListener?.onReachEnd()
                    Log.d("MainActivity", "$position >= ${articleList.size} - 2")
                }
            }
        }
    }

    override fun onViewRecycled(holder: ArticleViewHolder) {
        super.onViewRecycled(holder)
        with(holder.binding){
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
}