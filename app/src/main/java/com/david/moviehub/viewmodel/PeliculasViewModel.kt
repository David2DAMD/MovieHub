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

class PeliculasViewModel(val peliculasRepositorio: PeliculasRepository) : ViewModel() {

    val lstPeliculas: LiveData<List<Pelicula>> = peliculasRepositorio.listaPeliculas
    val lstFavoritos: LiveData<List<Pelicula>> = peliculasRepositorio.listaFavoritos

    //Variable que almacena el botón pulsado para compartirlo en todos los fragments
    var botonPulsado: String = ""

    init{
        //Rellenar la lista de peliculas favoritas de Room al inicializar el ViewModel
        rellenarFavoritos()
    }

    //Obtener peliculas de cartelera vía Retrofit y añadirlas a la lista
    fun obtenerCartelera(){
        viewModelScope.launch(Dispatchers.IO) {
            peliculasRepositorio.obtenerCartelera()
        }
    }

    //Obtener peliculas favoritas vía Retrofit y añadirlas a la lista
    fun obtenerPopulares(){
        viewModelScope.launch(Dispatchers.IO) {
            peliculasRepositorio.obtenerPopulares()
        }
    }

    //Obtener peliculas favoritas almacenadas en Room y añadirlas a la lista
    fun obtenerFavoritos() {
        viewModelScope.launch(Dispatchers.IO) {
            peliculasRepositorio.obtenerFavoritos()
        }
    }

    //Obtener peliculas favoritas almacenadas en Room y añadirlas a la lista de peliculas favoritas
    fun rellenarFavoritos() {
        viewModelScope.launch(Dispatchers.IO) {
            peliculasRepositorio.rellenarFavoritos()
        }
    }


    //Insertar película en base de datos Room
    fun insertarPelicula(pelicula: Pelicula){
        viewModelScope.launch(Dispatchers.IO) {
            peliculasRepositorio.insertarPelicula(pelicula)
        }
    }

    //Eliminar película de base de datos Room
    fun eliminarPelicula(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            peliculasRepositorio.eliminarPelicula(id)
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
