package com.rzatha.wikitok.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rzatha.wikitok.R
import com.rzatha.wikitok.databinding.ArticlePreviewItemBinding
import com.rzatha.wikitok.domain.Article
import com.rzatha.wikitok.presentation.activity.MainActivity

class ArticleAdapter : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(
    ArticleItemDiffCallback()
) {

    var onReachEndListener: OnReachEndListener? = null
    var onItemClickListener: OnItemClickListener? = null
    var onFavouriteClickAdapter: OnFavouriteClickAdapter? = null

    private var _favouriteArticlesId: List<Int> = listOf()
    var favouriteArticlesId: List<Int>
        get() = _favouriteArticlesId
        set(newList) {
            val oldList = _favouriteArticlesId
            _favouriteArticlesId = newList.toList()

            if (itemCount > 0) {
                val changedIds = findChangedFavoriteIds(oldList, newList)

                if (changedIds.isNotEmpty()) {

                    currentList.forEachIndexed { index, article ->

                        if (changedIds.contains(article.id)) {

                            notifyItemChanged(index, PAYLOAD_FAVORITE_STATUS_CHANGED)
                        }
                    }
                }
            }
        }
    private fun findChangedFavoriteIds(oldList: List<Int>, newList: List<Int>): Set<Int> {
        val oldSet = oldList.toSet()
        val newSet = newList.toSet()

        return (oldSet union newSet) - (oldSet intersect newSet)
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
        val article = getItem(position)
        bindArticleData(holder, article)
        bindFavoriteIcon(holder, article)
        setupListeners(holder, article)
        checkReachEnd(position)
    }

    private fun bindArticleData(holder: ArticleViewHolder, article: Article) {
        with(holder.binding) {
            if (article.imageUrl != null) {
                Glide.with(holder.itemView)
                    .load(article.imageUrl)
                    .placeholder(R.drawable.image_placeholder)
                    .into(ivArticle)
            } else {
                ivArticle.setImageResource(R.drawable.ggb)
            }
            tvTitle.text = article.title
            tvArticleText.text = article.extractText
        }
    }

    private fun bindFavoriteIcon(holder: ArticleViewHolder, article: Article) {
        val resId = if (_favouriteArticlesId.contains(article.id)) {
            R.drawable.ic_bookmarked
        } else {
            R.drawable.ic_bookmark
        }
        holder.binding.ivFavourites.setImageResource(resId)
    }

    private fun setupListeners(holder: ArticleViewHolder, article: Article) {
        with(holder.binding) {
            root.setOnClickListener {
                onItemClickListener?.onItemClick(article.id)
            }
            ivFavourites.setOnClickListener {
                val artContainsInDb = _favouriteArticlesId.contains(article.id)
                onFavouriteClickAdapter?.onFavouriteClick(artContainsInDb, article)
            }
        }
    }

    private fun checkReachEnd(position: Int) {
        if (REM_PAGES_TO_START_LOAD >= itemCount - position) {
            onReachEndListener?.onReachEnd()
        }
    }

    override fun onBindViewHolder(
        holder: ArticleViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {

            super.onBindViewHolder(holder, position, payloads)
        } else {

            if (payloads.contains(PAYLOAD_FAVORITE_STATUS_CHANGED)) {

                val article = getItem(position)
                bindFavoriteIcon(holder, article)
            }

        }
    }

    override fun onViewRecycled(holder: ArticleViewHolder) {
        super.onViewRecycled(holder)
        with(holder.binding) {
            tvTitle.text = ""
            tvArticleText.text = ""
            ivArticle.setImageDrawable(null)
            ivFavourites.setImageDrawable(null)
        }
    }

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
        private const val PAYLOAD_FAVORITE_STATUS_CHANGED = "PAYLOAD_FAVORITE_STATUS_CHANGED"
    }
}