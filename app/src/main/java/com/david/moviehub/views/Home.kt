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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.david.moviehub.R
import com.david.moviehub.databinding.FragmentHomeBinding
import com.david.moviehub.databinding.FragmentPeliculaInfoBinding
import com.david.moviehub.viewmodel.PeliculasViewModel
import com.david.moviehub.viewmodel.PeliculasViewModelFactory


class Home : Fragment() {
    private val viewModel: PeliculasViewModel by activityViewModels { PeliculasViewModelFactory() }
    private lateinit var peliculasAdapter: PeliculasAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Llama a la función que configura el RecyclerView
        configuraRecyclerView()

        //Llama a la función que cambia el color de los botones según lo almacenado en el ViewModel
        cambiaColorBoton(viewModel.botonPulsado)

        //Observador para el listado de películas que notifica al Adapter si hay cambios
        viewModel.lstPeliculas.observe(viewLifecycleOwner) {
            peliculasAdapter.lstPeliculas = it
            peliculasAdapter.notifyDataSetChanged()
        }

        //Se definen las acciones a realizar al pulsar cada uno de los botones
        //Se guarda el valor del botón pulsado en el ViewModel, se obtiene la lista correspondiente y se cambia el color del botón

        binding.cvCartelera.setOnClickListener {
            viewModel.obtenerCartelera()
            cambiaColorBoton("car")
            viewModel.botonPulsado = "car"
        }
        binding.cvPopulares.setOnClickListener {
            viewModel.obtenerPopulares()
            cambiaColorBoton("pop")
            viewModel.botonPulsado = "pop"
        }
        binding.cvFavoritos.setOnClickListener {
            viewModel.obtenerFavoritos()
            cambiaColorBoton("fav")
            viewModel.botonPulsado = "fav"
        }


    }

    //Función que cambia el color de los botones según el que esté activo en ese momento pasando un String
    fun cambiaColorBoton(boton: String) {
        when (boton) {
            "car" -> {
                binding.cvCartelera.setCardBackgroundColor(resources.getColor(R.color.verde_200))
                binding.cvPopulares.setCardBackgroundColor(resources.getColor(R.color.azul_200))
                binding.cvFavoritos.setCardBackgroundColor(resources.getColor(R.color.azul_200))
            }

            "pop" -> {
                binding.cvCartelera.setCardBackgroundColor(resources.getColor(R.color.azul_200))
                binding.cvPopulares.setCardBackgroundColor(resources.getColor(R.color.verde_200))
                binding.cvFavoritos.setCardBackgroundColor(resources.getColor(R.color.azul_200))
            }

            "fav" -> {
                binding.cvCartelera.setCardBackgroundColor(resources.getColor(R.color.azul_200))
                binding.cvPopulares.setCardBackgroundColor(resources.getColor(R.color.azul_200))
                binding.cvFavoritos.setCardBackgroundColor(resources.getColor(R.color.verde_200))
            }
        }
    }

    //Función que configura el RecyclerView
    private fun configuraRecyclerView() {
        val layoutManager = GridLayoutManager(context, 3)
        binding.rvPeliculas.layoutManager = layoutManager
        peliculasAdapter =
            PeliculasAdapter(requireContext(), arrayListOf(), viewModel, findNavController())
        binding.rvPeliculas.adapter = peliculasAdapter
    }
}