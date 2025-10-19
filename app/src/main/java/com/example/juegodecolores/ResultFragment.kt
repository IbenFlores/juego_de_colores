package com.example.juegodecolores

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.juegodecolores.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    // recibimos el argumento del puntaje de forma segura
    private val args: ResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val puntajeActual = args.score
        binding.finalScoreValue.text = puntajeActual.toString()

        // añadimos el puntaje al historial de la sesion
        HistorialPuntajes.puntajes.add(puntajeActual)

        manejarPuntajeMasAlto(puntajeActual)
        configurarRecyclerView()

        binding.playAgainButton.setOnClickListener {
            // usamos la accion definida en nav_graph para volver al inicio
            findNavController().navigate(R.id.action_resultFragment_to_welcomeFragment)
        }
    }

    private fun manejarPuntajeMasAlto(puntajeActual: Int) {
        val prefs = requireActivity().getSharedPreferences("JuegoDeColoresPrefs", Context.MODE_PRIVATE)
        val puntajeMasAlto = prefs.getInt("PUNTAJE_MAS_ALTO", 0)

        if (puntajeActual > puntajeMasAlto) {
            binding.highScoreValue.text = puntajeActual.toString()
            // guardamos el nuevo record
            prefs.edit().putInt("PUNTAJE_MAS_ALTO", puntajeActual).apply()
        } else {
            binding.highScoreValue.text = puntajeMasAlto.toString()
        }
    }

    private fun configurarRecyclerView() {
        binding.scoresRecyclerView.adapter = ScoreAdapter(HistorialPuntajes.puntajes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}