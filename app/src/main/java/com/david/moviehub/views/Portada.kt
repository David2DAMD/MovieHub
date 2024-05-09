package com.david.moviehub.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.david.moviehub.R
import com.david.moviehub.databinding.FragmentHomeBinding
import com.david.moviehub.databinding.FragmentPortadaBinding
import com.david.moviehub.viewmodel.PeliculasViewModel
import com.david.moviehub.viewmodel.PeliculasViewModelFactory

class Portada : Fragment() {

    private var _binding: FragmentPortadaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PeliculasViewModel by activityViewModels { PeliculasViewModelFactory() }
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPortadaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        //Se definen las acciones a realizar al pulsar cada uno de los botones de la portada
        //Se guarda el valor del botón pulsado en el ViewModel y se obtiene la lista correspondiente

        binding.btnCartelera.setOnClickListener {
            val action = PortadaDirections.actionPortadaToMain()
            navController.navigate(action)
            viewModel.botonPulsado = "car"
            viewModel.obtenerCartelera()
        }
        binding.btnPopulares.setOnClickListener {
            val action = PortadaDirections.actionPortadaToMain()
            navController.navigate(action)
            viewModel.botonPulsado = "pop"
            viewModel.obtenerPopulares()
        }
        binding.btnFavoritos.setOnClickListener {
            val action = PortadaDirections.actionPortadaToMain()
            navController.navigate(action)
            viewModel.botonPulsado = "fav"
            viewModel.obtenerFavoritos()
        }

    }
}