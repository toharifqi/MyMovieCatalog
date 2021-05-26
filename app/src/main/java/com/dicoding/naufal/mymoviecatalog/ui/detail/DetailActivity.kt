package com.dicoding.naufal.mymoviecatalog.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.naufal.mymoviecatalog.R
import com.dicoding.naufal.mymoviecatalog.data.source.local.entity.ShowEntity
import com.dicoding.naufal.mymoviecatalog.databinding.ActivityDetailBinding
import com.dicoding.naufal.mymoviecatalog.viewmodel.ViewModelFactory
import com.dicoding.naufal.mymoviecatalog.vo.Status

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var menu: Menu? = null

    companion object{
        const val EXTRA_SHOW_ID = "extra_show_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val extras = intent.extras
        if (extras != null){
            val showId = extras.getString(EXTRA_SHOW_ID)
            if (showId != null){

                viewModel.setShowId(showId)
                viewModel.show.observe(this, {show ->
                    if (show != null){
                        when(show.status){
                            Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                            Status.SUCCESS -> {
                                binding.progressBar.visibility = View.GONE
                                populateShow(show.data)
                            }
                            Status.ERROR -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                })
            }
        }
    }

    private fun populateShow(detailShow: ShowEntity?) {
        if (detailShow != null){
            Glide.with(this)
                .load(detailShow.showPosterPath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .error(R.drawable.ic_error)
                .into(binding.imgPoster)

            Glide.with(this)
                .load(detailShow.showScene1Path)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .error(R.drawable.ic_error)
                .into(binding.imgScene1)
            Glide.with(this)
                .load(detailShow.showScene2Path)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .error(R.drawable.ic_error)
                .into(binding.imgScene2)
            Glide.with(this)
                .load(detailShow.showScene3Path)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .error(R.drawable.ic_error)
                .into(binding.imgScene3)

            with(binding){
                tvTitleContent.text = detailShow.showTitle
                tvGenreContent.text = detailShow.showGenre
                tvDateContent.text = detailShow.showReleaseDate
                tvStarsContent.text = detailShow.showStars
                tvWriterContent.text = detailShow.showWriter
                tvSinopsysContent.text = detailShow.showSinopsys
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        viewModel.show.observe(this, { show ->
            if (show != null){
                when(show.status){
                    Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> if (show.data != null){
                        binding.progressBar.visibility = View.GONE
                        val state = show.data.isFavorite
                        setFavoriteState(state)
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun setFavoriteState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state){
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_selected_24)
        }else{
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_white_24)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite){
            viewModel.setFavorite()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}