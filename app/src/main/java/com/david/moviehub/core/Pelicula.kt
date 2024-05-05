package com.david.moviehub.core

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//Se definen los atributos del objeto Pelicula y se anotan tanto para utlizar Retrofit como Room
//Con la anotación @SerializedName se indica a que elemento del response de la api queremos acceder

@Entity
data class Pelicula(
    @SerializedName("id")
    @PrimaryKey
    var id: String,
    @SerializedName("original_title")
    var nombrePelicula: String,
    @SerializedName("overview")
    var descripcion: String,
    @SerializedName("poster_path")
    var poster: String,
    @SerializedName("release_date")
    var fechaLanzamiento: String,
    @SerializedName("vote_average")
    var mediaVotos: String,
    @SerializedName("vote_count")
    var totalVotos: String
)
