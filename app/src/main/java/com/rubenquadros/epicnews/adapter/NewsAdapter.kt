package com.rubenquadros.epicnews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rubenquadros.epicnews.R
import com.rubenquadros.epicnews.callback.ViewCallback
import com.rubenquadros.epicnews.data.local.entity.NewsEntity
import com.rubenquadros.epicnews.data.remote.model.Article
import com.rubenquadros.epicnews.utils.ApplicationConstants
import com.rubenquadros.epicnews.utils.ApplicationUtility
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class NewsAdapter(private val news: List<Article>?, private val listener: ViewCallback): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return news?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(news != null) {
            Picasso.get().load(news[position].urlToImage)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.color.colorPrimaryDark)
                .into(holder.mImageView, object : Callback {
                    override fun onSuccess() {
                        // Successfully loaded image from the server
                    }

                    override fun onError(e: Exception?) {
                        Picasso.get().load(news[position].urlToImage)
                            .networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.color.colorPrimaryDark)
                            .into(holder.mImageView, object : Callback {
                                override fun onSuccess() {
                                    // Successfully loaded image from cache
                                }

                                override fun onError(e: Exception?) {
                                    // No images in local db/no cache data available
                                }

                            })
                    }

                })
            if(news[position].source != null) {
                holder.authorTv?.text = news[position].source!!.name
            }
            holder.dateTv?.text = ApplicationUtility.formatDate(news[position].publishedAt!!)
            holder.titleTv?.text = news[position].title

            holder.mImageView?.setOnClickListener {
                val description: String? = if(news[position].description == null) {
                    news[position].content
                }else{
                    news[position].description
                }
                var source = ""
                if(news[position].source != null) {
                    source = news[position].source!!.name ?: ""
                }
                listener.onActionPerformed(ApplicationConstants.CLICKED,
                    NewsEntity(0,news[position].title,description,news[position].urlToImage,
                        news[position].publishedAt,news[position].author,source))
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mImageView: ImageView? = itemView.findViewById(R.id.background)
        val authorTv: TextView? = itemView.findViewById(R.id.author)
        val dateTv: TextView? = itemView.findViewById(R.id.date)
        val titleTv: TextView? = itemView.findViewById(R.id.title)
    }
}