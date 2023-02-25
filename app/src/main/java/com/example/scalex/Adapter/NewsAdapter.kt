package com.example.scalex.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scalex.Articles
import com.example.scalex.NewsJSONModel
import com.example.scalex.R
import retrofit2.Callback

class NewsAdapter :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    val context: Context
    var newsList: ArrayList<Articles>

    constructor(context: Context , newsList: ArrayList<Articles>) : super() {
        this.context = context
        this.newsList = newsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.single_card_layout, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.publishedAtTxtView.text = newsList[position].publishedAt
        holder.sourceNameTxtView.text = newsList[position].source?.name
        holder.titleTxtView.text = newsList[position].title
        holder.descriptionTxtView.text = newsList[position].description

        Glide.with(context).load(newsList[position].urlToImage).into(holder.newsImageView)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val publishedAtTxtView: TextView = itemView.findViewById(R.id.published_At_txt_view)
        val sourceNameTxtView: TextView = itemView.findViewById(R.id.source_name_txt_view)
        val titleTxtView: TextView = itemView.findViewById(R.id.title_txt_view)
        val descriptionTxtView: TextView = itemView.findViewById(R.id.description_txt_view)
        val newsImageView: ImageView = itemView.findViewById(R.id.news_image_view)
    }

    fun filteredList(filteredList : ArrayList<Articles>)
    {
        newsList = filteredList
        notifyDataSetChanged()
    }

}