package com.david.movietor.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.david.moviehub.core.Pelicula
import com.david.movietor.network.WebService

/*
Desde esta clase Repositorio se accede a los datos tanto de las consultas a la API como de la base de datos Room
Se crean dos listas, una general que se utiliza para el Recycler view y la otra de películas favoritas, que sirve
para almacenar las películas que están en la base de datos Room
*/

class PeliculasRepository(val webService: WebService, val database: AppDatabase) {

    private var API_KEY = "78a6a7031997841f12176835f2c4d40a"
    private var _listaPeliculas = MutableLiveData<List<Pelicula>>()
    val listaPeliculas: LiveData<List<Pelicula>> = _listaPeliculas
    private var _listaFavoritos = MutableLiveData<List<Pelicula>>()
    val listaFavoritos: LiveData<List<Pelicula>> = _listaFavoritos
    val dao = database.PeliculaDao()

/*
NOTA: Se utiliza postValue para actualizar el valor _listaPeliculas desde un hilo de fondo.
Asegura que la actualización se realice en el hilo principal
*/
    //Método para rellenar lista de películas con las películas obtenidas del método obtenerCartelera del WebService
    suspend fun obtenerCartelera() {
        val response = webService.obtenerCartelera(API_KEY)
        val cartelera = response.body()!!.resultados.sortedByDescending { it.mediaVotos }
        _listaPeliculas.postValue(cartelera)
    }

    //Método para rellenar lista de películas con las películas obtenidas del método obtenerPopular del WebService
    suspend fun obtenerPopulares() {
        val response = webService.obtenerPopular(API_KEY)
        val populares = response.body()!!.resultados.sortedByDescending { it.mediaVotos }
        _listaPeliculas.postValue(populares)
    }

    //Método para rellenar la lista de películas con las películas favoritas de Room
    suspend fun obtenerFavoritos(){
        val peliculasFav = dao.getAll()
        _listaPeliculas.postValue(peliculasFav)
    }

    //Metodo para rellenar la lista de películas favoritas con las películas favoritas de Room
    suspend fun rellenarFavoritos(){
        val peliculasFav = dao.getAll()
        _listaFavoritos.postValue(peliculasFav)
    }

    //Metodo para insertar película en la base de datos Room
    suspend fun insertPelicula(pelicula: Pelicula){
        dao.insertPelicula(pelicula)
    }

    //Método para eliminar una película de la base de datos Room
    suspend fun deletePelicula(id: String){
        dao.deletePelicula(id)
    }

}