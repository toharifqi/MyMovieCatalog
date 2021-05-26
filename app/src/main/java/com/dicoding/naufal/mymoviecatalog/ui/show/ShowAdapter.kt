package com.dicoding.naufal.mymoviecatalog.ui.show

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.naufal.mymoviecatalog.R
import com.dicoding.naufal.mymoviecatalog.data.source.local.entity.ShowEntity
import com.dicoding.naufal.mymoviecatalog.databinding.ItemShowBinding

class ShowAdapter(private val callback: ShowFragmentCallback): PagedListAdapter<ShowEntity, ShowAdapter.ShowViewHolder>(DIFF_CALLBACK) {

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ShowEntity>(){
            override fun areItemsTheSame(oldItem: ShowEntity, newItem: ShowEntity): Boolean {
                return oldItem.showId == newItem.showId
            }

            override fun areContentsTheSame(oldItem: ShowEntity, newItem: ShowEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback{
        fun onItemClicked(showId: String)
    }

    fun getSwipedData(swipedPosition: Int): ShowEntity? = getItem(swipedPosition)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val itemsMovieBinding = ItemShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    inner class ShowViewHolder(private val binding: ItemShowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(show: ShowEntity){
            with(binding){
                tvMovieTitle.text = show.showTitle
                tvMovieGenre.text = show.showGenre
                tvMovieSynopsis.text = show.showSinopsys
                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(show.showId)
                }
                imgShare.setOnClickListener { callback.onShareClick(show) }
                Glide.with(itemView.context)
                        .load(show.showPosterPath)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                        .error(R.drawable.ic_error)
                        .into(imgPoster)
            }
        }

    }
}