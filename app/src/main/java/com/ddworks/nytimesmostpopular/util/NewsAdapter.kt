package com.ddworks.nytimesmostpopular.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ddworks.nytimesmostpopular.R
import com.ddworks.nytimesmostpopular.domain.DomainNews
import com.ddworks.nytimesmostpopular.util.NewsAdapter.NewsViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_item.view.*
import kotlinx.android.synthetic.main.row_item.view.tv_by
import kotlinx.android.synthetic.main.row_item.view.tv_date
import kotlinx.android.synthetic.main.row_item.view.tv_title

class NewsAdapter(
    private val listener: NewsItemListener
) : ListAdapter<DomainNews, NewsViewHolder>(NewsComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = getItem(position)

        holder.tvBy.text = newsItem.byline
        holder.tvDate.text = newsItem.published_date
        holder.tvTitle.text = newsItem.title
        Picasso.get().load(newsItem.picture).placeholder(R.drawable.icon_calendar).into(holder.ivImageOfNews)
        holder.ivOpenDetailActivity.setOnClickListener{
            listener.onItemClick(newsItem.id)
        }
    }

    fun getListOfNews(): List<DomainNews> {
        val list = mutableListOf<DomainNews>()
        for (i in 0 until itemCount) {
            list.add(getItem(i))
        }
        return list
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvBy: TextView = itemView.tv_by
        var tvDate: TextView = itemView.tv_date
        var tvTitle: TextView = itemView.tv_title
        var ivImageOfNews: ImageView = itemView.iv_imageofnews
        var ivOpenDetailActivity: ImageView = itemView.iv_opendetailactivity
    }

    object NewsComparator : DiffUtil.ItemCallback<DomainNews>() {
        override fun areItemsTheSame(oldItem: DomainNews, newItem: DomainNews): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DomainNews, newItem: DomainNews): Boolean {
            return oldItem == newItem
        }
    }

    interface NewsItemListener{
        fun onItemClick(newsId: Int)
    }
}