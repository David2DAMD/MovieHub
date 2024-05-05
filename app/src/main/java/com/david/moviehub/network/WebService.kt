package com.david.movietor.network

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

/*
En esta clase se definen los métodos para obtener los datos de la API
Se utiliza la anotación GET para definir que es lo que queremos descargar
Se obtiene una lista de películas
 */


interface WebService {

    @GET("now_playing")
    suspend fun obtenerCartelera(
        @Query("api_key") apiKey: String
    ): Response<PeliculasResponse>

    @GET("popular")
    suspend fun obtenerPopular(
        @Query("api_key") apiKey: String
    ): Response<PeliculasResponse>

}