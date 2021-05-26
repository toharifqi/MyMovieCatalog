package com.dicoding.naufal.mymoviecatalog.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowResponse(
    val showId: String,
    val showTitle: String,
    val showReleaseDate: String,
    val showWriter: String,
    val showGenre: String,
    val showSinopsys: String,
    val showPosterPath: String,
    val showScene1Path: String,
    val showScene2Path: String,
    val showScene3Path: String,
    val showStars: String,
    val isMovie: Boolean
): Parcelable