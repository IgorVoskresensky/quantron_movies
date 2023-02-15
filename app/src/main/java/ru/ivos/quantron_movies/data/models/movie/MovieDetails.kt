package ru.ivos.quantron_movies.data.models.movie

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetails(
    @SerializedName("id")
    @Expose
    var id: Int = 0,
    @SerializedName("original_title")
    @Expose
    var originalTitle: String = "",
    @SerializedName("overview")
    @Expose
    var overview: String = "",
    @SerializedName("popularity")
    @Expose
    var popularity: Double = 0.0,
    @SerializedName("poster_path")
    @Expose
    var posterPath: String = "",
    @SerializedName("release_date")
    @Expose
    var releaseDate: String = "",
    @SerializedName("title")
    @Expose
    var title: String = "",
    @SerializedName("vote_average")
    @Expose
    var rating: Double = 0.0,

    @SerializedName("original_name")
    @Expose
    var originalName: String = "",
    @SerializedName("number_of_seasons")
    @Expose
    var numberOfSeasons: Int = 0,
    @SerializedName("number_of_episodes")
    @Expose
    var numberOfEpisodes: Int = 0,
    @SerializedName("first_air_date")
    @Expose
    var firstAirDate: String = "",
    @SerializedName("name")
    @Expose
    var name: String = "",
) : Parcelable