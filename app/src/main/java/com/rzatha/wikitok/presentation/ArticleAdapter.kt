package com.rzatha.wikitok.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rzatha.wikitok.R
import com.rzatha.wikitok.databinding.ArticlePreviewItemBinding
import com.rzatha.wikitok.domain.Article

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(
) {

    var onReachEndListener: OnReachEndListener? = null
    var onItemClickListener: OnItemClickListener? = null
    var onFavouriteClickAdapter: OnFavouriteClickAdapter? = null

    var articleList: List<Article> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var favouriteArticlesId : List<Int> = listOf()
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

                val containsInDb = favouriteArticlesId.contains(id)

                if (imageUrl != null) {
                    Log.d(
                        "MainActivity",
                        "Image url: ${article.imageUrl}\nArticle title: ${article.title}\n" +
                                "Article id: ${article.id}"
                    )
                    Glide.with(holder.itemView)
                        .load(article.imageUrl)
                        .placeholder(R.drawable.image_placeholder)
                        .into(ivArticle)
                } else {
                    ivArticle.setImageResource(R.drawable.ggb)
                }

                tvTitle.text = title
                tvArticleText.text = extractText

                var resId = if (containsInDb) {
                    R.drawable.ic_bookmarked
                } else {
                    R.drawable.ic_bookmark
                }
                ivFavourites.setImageResource(resId)

                if (position >= articleList.size - REM_PAGES_TO_START_LOAD) {
                    onReachEndListener?.onReachEnd()
                }

                root.setOnClickListener {
                    onItemClickListener?.onItemClick(id)
                }

                ivFavourites.setOnClickListener {
                    val artContainsInDb = favouriteArticlesId.contains(id)
                    onFavouriteClickAdapter?.onFavouriteClick(artContainsInDb, article)
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

    interface OnFavouriteClickAdapter {
        fun onFavouriteClick(containsInDb: Boolean, article: Article)
    }

    companion object {
        const val REM_PAGES_TO_START_LOAD = 5
    }
}