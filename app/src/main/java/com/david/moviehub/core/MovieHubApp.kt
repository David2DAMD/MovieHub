package com.david.moviehub.core

import android.app.Application
import com.david.movietor.data.AppDatabase
import com.david.movietor.data.PeliculasRepository
import com.david.movietor.network.WebService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieHubApp: Application() {

    //Se inicializa la base de datos
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this)}

    //Se crea una instancia Retrofit y se configura la URL para solicitudes y el convertidor Gson
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/movie/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    //Se crea una instancia de la interfaz 'WebService' utilizando Retrofit
    val service: WebService = retrofit.create(WebService::class.java)

    //Se inicializa el repositorio
    val peliculasRepository: PeliculasRepository by lazy {PeliculasRepository(service, database)}

}