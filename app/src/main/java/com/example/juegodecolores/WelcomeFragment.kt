package com.example.juegodecolores

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.juegodecolores.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    // usamos view binding para una referencia segura a las vistas
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startButton.setOnClickListener {
            // mostramos las reglas antes de navegar
            mostrarDialogoReglas()
        }
    }

    private fun mostrarDialogoReglas() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.game_rules_title)
            .setMessage(R.string.game_rules)
            .setPositiveButton(R.string.accept) { dialog, _ ->
                dialog.dismiss()
                // una vez aceptado, navegamos al juego
                findNavController().navigate(R.id.action_welcomeFragment_to_gameFragment)
            }
            .setCancelable(false) // el usuario debe aceptar
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // limpiamos el binding para evitar fugas de memoria
        _binding = null
    }
}