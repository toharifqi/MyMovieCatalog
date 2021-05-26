package com.dicoding.naufal.mymoviecatalog.ui.show

import com.dicoding.naufal.mymoviecatalog.data.source.local.entity.ShowEntity

interface ShowFragmentCallback {
    fun onShareClick(show: ShowEntity)
}