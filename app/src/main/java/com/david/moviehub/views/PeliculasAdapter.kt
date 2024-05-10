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
        val ivFavorito = view.findViewById(R.id.ivFavorito) as ImageView
        val pcIndicator =
            view.findViewById(R.id.cpCircularProgress) as antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
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

        //Se utiliza Glide para mostrar en el ImageView la imagen de la URL
        Glide
            .with(context)
            .load("${urlImagen}${pelicula.poster}")
            .apply(RequestOptions().override(330, 330))
            .into(holder.ivPoster)

        //Se configura el Circular Progress Indicator
        holder.pcIndicator.maxProgress = 10.0
        holder.pcIndicator.setCurrentProgress(pelicula.mediaVotos.toDouble())

        //Se comprueba si la película está en la lista de favoritos para asignarle la imagen que corresponda
        val favoritos = lstFavoritos ?: emptyList()
        val isFavorito = favoritos.any { it.id == pelicula.id } ?: false

        if (isFavorito) {
            holder.ivFavorito.setImageResource(R.drawable.ic_favorite)
        } else {
            holder.ivFavorito.setImageResource(R.drawable.ic_favorite_border)
        }

        //Se define la acción a realizar al pulsar el ImageView ivFavorito según esté dentro de la lista de favoritos o no
        holder.ivFavorito.setOnClickListener {
            if (favoritos.any { it.id == pelicula.id } ?: false) {
                holder.ivFavorito.setImageResource(R.drawable.ic_favorite_border)
                viewModel.eliminarPelicula(pelicula.id)
                viewModel.rellenarFavoritos()

            } else {
                holder.ivFavorito.setImageResource(R.drawable.ic_favorite)
                viewModel.insertarPelicula(pelicula)
                viewModel.rellenarFavoritos()

            }
            notifyDataSetChanged()
        }

        //Se define la acción a realizar al pulsar el CardView cvPelicula mediante Navgraph
        holder.cvPelicula.setOnClickListener {
            val action = HomeDirections.actionHome2ToPeliculaInfo(
                pelicula.nombrePelicula,
                pelicula.descripcion,
                pelicula.poster,
                pelicula.mediaVotos,
                pelicula.fechaLanzamiento,
                pelicula.totalVotos
            )
            navGraph.navigate(action)
        }
    }
}