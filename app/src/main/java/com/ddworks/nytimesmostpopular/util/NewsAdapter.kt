package com.ddworks.nytimesmostpopular.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ddworks.nytimesmostpopular.R
import com.ddworks.nytimesmostpopular.domain.DomainNews
import com.ddworks.nytimesmostpopular.util.NewsAdapter.NewsViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_item.view.*

class NewsAdapter : ListAdapter<DomainNews, NewsViewHolder>(NewsComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = getItem(position)

        holder.tv_by.text = newsItem.byline
        holder.tv_date.text = newsItem.published_date
        holder.tv_title.text = newsItem.title
        Picasso.get().load(newsItem.picture).into(holder.smallpic.iv_imageofarticle)
        holder.iv_opendetailactivity.setOnClickListener{

        }
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_by = itemView.tv_by
        var tv_date = itemView.tv_date
        var tv_title = itemView.tv_title
        var smallpic = itemView.iv_imageofarticle
        var iv_opendetailactivity = itemView.iv_opendetailactivity
    }

    object NewsComparator : DiffUtil.ItemCallback<DomainNews>() {
        override fun areItemsTheSame(oldItem: DomainNews, newItem: DomainNews): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DomainNews, newItem: DomainNews): Boolean {
            return oldItem == newItem
        }
    }
}