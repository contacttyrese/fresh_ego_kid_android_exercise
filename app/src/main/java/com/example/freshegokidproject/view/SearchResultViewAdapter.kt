package com.example.freshegokidproject.view

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.freshegokidproject.databinding.ItemviewSearchResultBinding
import com.example.freshegokidproject.model.SearchResult
import javax.inject.Inject

class SearchResultViewAdapter @Inject constructor(private val searchResults: List<SearchResult>): RecyclerView.Adapter<SearchResultViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemviewSearchResultBinding.inflate(
            LayoutInflater.from(parent.context)
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchResult = searchResults[position]
        holder.bind(searchResult)
        holder.itemView.setOnClickListener { view ->
            val intent = Intent(view.context, ProductDetailActivity::class.java)
            intent.putExtra("key", searchResult.key)
            intent.putExtra("price", searchResult.price)
            intent.putExtra("imageUrl", searchResult.imageUrl)
            intent.putExtra("detailsUrl", searchResult.detailsUrl)
            Log.i("itemview_start_detail", "attempting to launch new detail activity")
            view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return searchResults.size
    }

    class ViewHolder(private val binding: ItemviewSearchResultBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(searchResult: SearchResult) = with(binding) {
            Glide.with(root).load(searchResult.imageUrl).into(itemviewProductimage)
            itemviewProductprice.text = searchResult.price
            itemviewProductdescription.text = searchResult.title
        }
    }
}