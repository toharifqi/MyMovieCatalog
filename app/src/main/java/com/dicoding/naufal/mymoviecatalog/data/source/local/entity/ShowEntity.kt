package com.dicoding.naufal.mymoviecatalog.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "showentities")
data class ShowEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "showId")
    var showId: String,

    @ColumnInfo(name = "title")
    var showTitle: String,

    @ColumnInfo(name = "releaseDate")
    var showReleaseDate: String,

    @ColumnInfo(name = "writer")
    var showWriter: String,

    @ColumnInfo(name = "genre")
    var showGenre: String,

    @ColumnInfo(name = "sinopsys")
    var showSinopsys: String,

    @ColumnInfo(name = "posterPath")
    var showPosterPath: String,

    @ColumnInfo(name = "scene1Path")
    var showScene1Path: String,

    @ColumnInfo(name = "scene2Path")
    var showScene2Path: String,

    @ColumnInfo(name = "scene3Path")
    var showScene3Path: String,

    @ColumnInfo(name = "stars")
    var showStars: String,

    @ColumnInfo(name = "isMovie")
    var isMovie: Boolean,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)