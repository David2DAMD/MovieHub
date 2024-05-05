package com.david.moviehub.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.david.moviehub.R
import com.david.moviehub.databinding.FragmentPeliculaInfoBinding
import java.net.URL

class PeliculaInfo : Fragment() {

    private var _binding: FragmentPeliculaInfoBinding? = null
    private val binding get() = _binding!!
    private val args: PeliculaInfoArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPeliculaInfoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fpTitle.text = args.nombre
        binding.fpDescription.text = args.descripcion
        val urlImagen = "https://image.tmdb.org/t/p/w500${args.imagen}"
        Glide.with(requireContext())
            .load(urlImagen)
            .into(binding.fpImage)

        binding.fpVolver.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

}