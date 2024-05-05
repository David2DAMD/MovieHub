package com.david.movietor.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.david.moviehub.core.Pelicula

//En esta clase se define la base de datos Room

@Database(entities = arrayOf(Pelicula::class), version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun PeliculaDao(): PeliculaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database")
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }

}