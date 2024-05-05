package com.david.movietor.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.david.moviehub.core.Pelicula

//En esta clase Dao se crean métodos que acceden a la base de datos Room mediante las consultas indicadas

@Dao
interface PeliculaDao {

    @Query("SELECT * FROM Pelicula ORDER BY nombrePelicula ASC")
    fun getAll(): List<Pelicula>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPelicula(model: Pelicula)

    @Query("DELETE FROM Pelicula WHERE id = :id")
    fun deletePelicula(id: String)

}