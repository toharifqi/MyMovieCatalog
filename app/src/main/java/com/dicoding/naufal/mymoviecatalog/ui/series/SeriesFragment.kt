package com.dicoding.naufal.mymoviecatalog.ui.series

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.naufal.mymoviecatalog.R
import com.dicoding.naufal.mymoviecatalog.data.source.local.entity.ShowEntity
import com.dicoding.naufal.mymoviecatalog.databinding.FragmentSeriesBinding
import com.dicoding.naufal.mymoviecatalog.ui.detail.DetailActivity
import com.dicoding.naufal.mymoviecatalog.ui.show.ShowAdapter
import com.dicoding.naufal.mymoviecatalog.ui.show.ShowFragmentCallback
import com.dicoding.naufal.mymoviecatalog.ui.show.ShowViewModel
import com.dicoding.naufal.mymoviecatalog.viewmodel.ViewModelFactory
import com.dicoding.naufal.mymoviecatalog.vo.Status

class SeriesFragment : Fragment(), ShowFragmentCallback {

    private lateinit var binding: FragmentSeriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSeriesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[ShowViewModel::class.java]
            val showAdapter = ShowAdapter(this)

            viewModel.getSeries().observe(viewLifecycleOwner, {shows ->
                if (shows != null){
                    when(shows.status){
                        Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            showAdapter.submitList(shows.data)
                            showAdapter.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            })

            with(binding.rvShow){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = showAdapter
            }

            showAdapter.setOnItemClickCallback(object : ShowAdapter.OnItemClickCallback{
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
}