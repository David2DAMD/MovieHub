package com.david.movietor.network

import com.david.moviehub.core.Pelicula
import com.google.gson.annotations.SerializedName

/*
Esta clase se utiliza para almacenar la respuesta del JSON obtenido del Webservice
Se obtiene una lista de películas
 */

data class PeliculasResponse(

    @SerializedName("results")
    var resultados: List<Pelicula>
)
