package com.david.moviehub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.david.moviehub.core.MovieHubApp
import com.david.moviehub.core.Pelicula
import com.david.movietor.data.PeliculasRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
Desde esta clase ViewModel se accede a los métodos que acceden a los datos
de la base de datos Room y de los datos obtenidos mediante Retrofit
Es un intermediario entre la capa de presentación y la capa de datos
 */

class PeliculasViewModel(val peliculasRepository: PeliculasRepository) : ViewModel() {

    val lstPeliculas: LiveData<List<Pelicula>> = peliculasRepository.listaPeliculas
    val lstFavoritos: LiveData<List<Pelicula>> = peliculasRepository.listaFavoritos

    init{
        obtenerCartelera()
        rellenarFavoritos()
    }

    fun obtenerCartelera(){
        viewModelScope.launch(Dispatchers.IO) {
            peliculasRepository.obtenerCartelera()
        }
    }

    fun obtenerPopulares(){
        viewModelScope.launch(Dispatchers.IO) {
            peliculasRepository.obtenerPopulares()
        }
    }

    fun obtenerFavoritos() {
        viewModelScope.launch(Dispatchers.IO) {
            peliculasRepository.obtenerFavoritos()
        }
    }

    fun rellenarFavoritos() {
        viewModelScope.launch(Dispatchers.IO) {
            peliculasRepository.rellenarFavoritos()
        }
    }


    fun insertPelicula(pelicula: Pelicula){
        viewModelScope.launch(Dispatchers.IO) {
            peliculasRepository.insertPelicula(pelicula)
        }
    }

    fun deletePelicula(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            peliculasRepository.deletePelicula(id)
        }
    }
}

/*
Esta clase interna crea y configura las instancias del ViewModel de manera uniforme en toda la aplicación
 */
class PeliculasViewModelFactory(): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val app = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as MovieHubApp
        val repo = app.peliculasRepository
        return PeliculasViewModel(repo) as T
    }
}
