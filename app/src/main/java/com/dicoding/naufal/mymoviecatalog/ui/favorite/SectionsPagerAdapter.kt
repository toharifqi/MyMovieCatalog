package com.dicoding.naufal.mymoviecatalog.ui.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.naufal.mymoviecatalog.R
import com.dicoding.naufal.mymoviecatalog.ui.favorite.FavoriteShowFragment.Companion.MOVIES_TYPE
import com.dicoding.naufal.mymoviecatalog.ui.favorite.FavoriteShowFragment.Companion.SERIES_TYPE

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        @StringRes
        private val tabTitles = intArrayOf(R.string.movies, R.string.series)
    }

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> FavoriteShowFragment.newInstance(MOVIES_TYPE)
            1 -> FavoriteShowFragment.newInstance(SERIES_TYPE)
            else -> Fragment()
        }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence = mContext.resources.getString(tabTitles[position])

}