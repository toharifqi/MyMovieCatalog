package com.dicoding.naufal.mymoviecatalog.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.naufal.mymoviecatalog.R
import com.dicoding.naufal.mymoviecatalog.data.source.local.entity.ShowEntity
import com.dicoding.naufal.mymoviecatalog.databinding.FragmentShowBinding
import com.dicoding.naufal.mymoviecatalog.ui.detail.DetailActivity
import com.dicoding.naufal.mymoviecatalog.ui.show.ShowAdapter
import com.dicoding.naufal.mymoviecatalog.ui.show.ShowFragmentCallback
import com.dicoding.naufal.mymoviecatalog.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavoriteShowFragment : Fragment(), ShowFragmentCallback {

    private lateinit var binding: FragmentShowBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var showAdapter: ShowAdapter

    companion object{
        private const val SHOW_KEY = "show_key"
        const val MOVIES_TYPE = "movie_type"
        const val SERIES_TYPE = "series_type"

        fun newInstance(showType: String?): FavoriteShowFragment {
            val fragment = FavoriteShowFragment()
            val bundle = Bundle()
            bundle.putString(SHOW_KEY, showType)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvShow)
        if (activity != null){
            val showType = arguments?.getString(SHOW_KEY)
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
            showAdapter = ShowAdapter(this)
            binding.progressBar.visibility = View.VISIBLE
            if (showType == MOVIES_TYPE){
                viewModel.getFavoriteMovies().observe(viewLifecycleOwner, {shows ->
                    if (!shows.isEmpty()){
                        binding.progressBar.visibility = View.GONE
                        binding.imageEmpty.visibility = View.GONE
                        binding.rvShow.visibility = View.VISIBLE
                        showAdapter.submitList(shows)
                        showAdapter.notifyDataSetChanged()
                    }else{
                        binding.progressBar.visibility = View.GONE
                        binding.imageEmpty.visibility = View.VISIBLE
                        binding.rvShow.visibility = View.GONE
                    }
                })
            }else{
                viewModel.getFavoriteSeries().observe(viewLifecycleOwner, {shows ->
                    if (!shows.isEmpty()){
                        binding.progressBar.visibility = View.GONE
                        binding.imageEmpty.visibility = View.GONE
                        binding.rvShow.visibility = View.VISIBLE
                        showAdapter.submitList(shows)
                        showAdapter.notifyDataSetChanged()
                    }else{
                        binding.progressBar.visibility = View.GONE
                        binding.imageEmpty.visibility = View.VISIBLE
                        binding.rvShow.visibility = View.GONE
                    }
                })
            }

            with(binding.rvShow){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = showAdapter
            }

            showAdapter.setOnItemClickCallback(object : ShowAdapter.OnItemClickCallback {
                override fun onItemClicked(showId: String) {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_SHOW_ID, showId)
                    context?.startActivity(intent)
                }
            })

        }
    }

    override fun onShareClick(show: ShowEntity) {
        if (activity != null){
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                    .from(requireActivity())
                    .setType(mimeType)
                    .setChooserTitle(getString(R.string.shareText))
                    .setText(resources.getString(R.string.share_text, show.showTitle))
                    .startChooser()
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val courseEntity = showAdapter.getSwipedData(swipedPosition)
                courseEntity?.let { viewModel.setFavorite(it) }
                val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_undo_ok) { v ->
                    courseEntity?.let { viewModel.setFavorite(it) }
                }
                snackbar.show()
            }
        }
    })
}