package ru.ivos.quantron_movies.data.models.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorites")
data class Favorite(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "originalTitle")
    var originalTitle: String = "",
    @ColumnInfo(name = "overview")
    var overview: String = "",
    @ColumnInfo(name = "popularity")
    var popularity: Double = 0.0,
    @ColumnInfo(name = "posterPath")
    var posterPath: String = "",
    @ColumnInfo(name = "releaseDate")
    var releaseDate: String = "",
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "rating")
    var rating: Double = 0.0,

    @ColumnInfo(name = "originalName")
    var originalName: String = "",
    @ColumnInfo(name = "numberOfSeasons")
    var numberOfSeasons: Int = 0,
    @ColumnInfo(name = "numberOfEpisodes")
    var numberOfEpisodes: Int = 0,
    @ColumnInfo(name = "firstAirDate")
    var firstAirDate: String = "",
    @ColumnInfo(name = "name")
    var name: String = "",
)