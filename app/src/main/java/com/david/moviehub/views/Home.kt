package com.david.moviehub.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.david.moviehub.R
import com.david.moviehub.databinding.FragmentHomeBinding
import com.david.moviehub.databinding.FragmentPeliculaInfoBinding
import com.david.moviehub.viewmodel.PeliculasViewModel
import com.david.moviehub.viewmodel.PeliculasViewModelFactory


class Home : Fragment() {
    private val viewModel: PeliculasViewModel by viewModels { PeliculasViewModelFactory() }
    private lateinit var peliculasAdapter: PeliculasAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        configuraRecyclerView()

        viewModel.obtenerCartelera()

        viewModel.lstPeliculas.observe(viewLifecycleOwner) {
            peliculasAdapter.lstPeliculas = it
            peliculasAdapter.notifyDataSetChanged()
        }

        binding.cvCartelera.setOnClickListener {
            viewModel.obtenerCartelera()
            cambiaColorBoton("car")
        }
        binding.cvPopulares.setOnClickListener {
            viewModel.obtenerPopulares()
            cambiaColorBoton("pop")
        }
        binding.cvFavoritos.setOnClickListener {
            viewModel.obtenerFavoritos()
            cambiaColorBoton("fav")
        }
    }

    private fun cambiaColorBoton(boton: String){
        when(boton){
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

    private fun configuraRecyclerView() {
        val layoutManager = GridLayoutManager(requireContext(),3)
        binding.rvPeliculas.layoutManager = layoutManager
        peliculasAdapter = PeliculasAdapter(requireContext(), arrayListOf(), viewModel, navController)
        binding.rvPeliculas.adapter = peliculasAdapter
    }
}