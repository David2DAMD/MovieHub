package com.david.moviehub.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.david.moviehub.R
import com.david.moviehub.databinding.FragmentPeliculaInfoBinding
import com.david.moviehub.viewmodel.PeliculasViewModel
import com.david.moviehub.viewmodel.PeliculasViewModelFactory
import java.net.URL

class PeliculaInfo : Fragment() {

    private var _binding: FragmentPeliculaInfoBinding? = null
    private val binding get() = _binding!!
    private val args: PeliculaInfoArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPeliculaInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Se recuperan los datos pasados mediante el Navigation Component y se asignan a los elementos de la vista de este Fragment
        binding.tvTitulo.text = args.nombre
        binding.tvDescripcion.text = args.descripcion

        //Se usa Glide para visualizar las imagenes de la URL en un ImageView
        val urlImagen = "https://image.tmdb.org/t/p/w500${args.imagen}"
        Glide.with(requireContext())
            .load(urlImagen)
            .into(binding.ivPosterInfo)


        binding.btnVolver.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}