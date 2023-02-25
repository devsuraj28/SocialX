package com.example.scalex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scalex.Adapter.NewsAdapter
import com.example.scalex.Interface.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : AppCompatActivity() {

    lateinit var newsRecView: RecyclerView
    lateinit var newsAdapter: NewsAdapter
    lateinit var newsList: ArrayList<Articles>
    lateinit var searchTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)


        newsRecView = findViewById(R.id.newsRecView)
        searchTxt = findViewById(R.id.search_feed)
        newsList = ArrayList()
        getNews()

        searchTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filteredNewsList(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // TODO("Not yet implemented")
            }
        })

    }

    private fun filteredNewsList(string: String) {
        var filteredList: ArrayList<Articles> = ArrayList()
        for (news in newsList) {
            if (news.title!!.lowercase()
                    .contains(string.lowercase()) || news.source!!.name!!.contains(string.lowercase()) || news.description!!.lowercase()
                    .contains(string.lowercase())
            ) {
                filteredList.add(news)
                newsAdapter.filteredList(filteredList)
                newsRecView.adapter = newsAdapter
                newsRecView.layoutManager = LinearLayoutManager(this@Home)
            }
        }
    }

    private fun getNews() {
        val news: Call<NewsJSONModel> = NewsService.newsInstance.getHeadLines(country = "in", 1)
        news.enqueue(object : Callback<NewsJSONModel> {
            override fun onResponse(call: Call<NewsJSONModel>, response: Response<NewsJSONModel>) {
                val news: NewsJSONModel? = response.body()
                if (news != null) {
                    newsList.addAll(news.articles)
                    Log.d("News", news.articles.toString())
                    newsAdapter = NewsAdapter(this@Home, newsList)
                    newsRecView.adapter = newsAdapter
                    newsRecView.layoutManager = LinearLayoutManager(this@Home)
                }
            }

            override fun onFailure(call: Call<NewsJSONModel>, t: Throwable) {
                Log.d("News", "Error in Fetching", t)
            }

        })
    }
}