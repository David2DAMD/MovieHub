package com.david.moviehub.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.david.moviehub.R
import com.david.moviehub.core.Pelicula
import com.david.moviehub.viewmodel.PeliculasViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PeliculasAdapter(
    val context: Context,
    var lstPeliculas: List<Pelicula>,
    val viewModel: PeliculasViewModel,
    val navGraph: NavController
) :
    RecyclerView.Adapter<PeliculasAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvPelicula = view.findViewById(R.id.cvPelicula) as CardView
        val ivPoster = view.findViewById(R.id.ivPoster) as ImageView
        val ivFavorite = view.findViewById(R.id.iv_favorite) as ImageView
        val pcIndicator =
            view.findViewById(R.id.circularProgress) as antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_pelicula, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lstPeliculas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pelicula = lstPeliculas[position]
        val urlImagen = "https://image.tmdb.org/t/p/w500"
        val lstFavoritos = viewModel.lstFavoritos.value

        Glide
            .with(context)
            .load("${urlImagen}${pelicula.poster}")
            .apply(RequestOptions().override(330, 330))
            .into(holder.ivPoster)

        holder.pcIndicator.maxProgress = 10.0
        holder.pcIndicator.setCurrentProgress(pelicula.mediaVotos.toDouble())

        val favoritos = lstFavoritos ?: emptyList()
        val isFavorito = favoritos.any { it.id == pelicula.id } ?: false

        if (isFavorito) {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite_border)
        }

        holder.ivFavorite.setOnClickListener {
            if (isFavorito) {
                holder.ivFavorite.setImageResource(R.drawable.ic_favorite_border)
                viewModel.deletePelicula(pelicula.id)
                viewModel.rellenarFavoritos()
            } else {
                holder.ivFavorite.setImageResource(R.drawable.ic_favorite)
                viewModel.insertPelicula(pelicula)
                viewModel.rellenarFavoritos()
            }
        }

        holder.cvPelicula.setOnClickListener {
            val action = HomeDirections.actionHome2ToPeliculaInfo(
                pelicula.nombrePelicula,
                pelicula.descripcion,
                pelicula.poster
            )
            navGraph.navigate(action)

        }
    }
}