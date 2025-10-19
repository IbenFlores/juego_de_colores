package com.example.juegodecolores

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import android.media.AudioAttributes
import android.media.SoundPool
import android.view.animation.AnimationUtils
import com.example.juegodecolores.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private var score = 0
    private lateinit var timer: CountDownTimer

    private lateinit var soundPool: SoundPool
    private var idSonidoAcierto: Int = 0
    private var idSonidoError: Int = 0

    // definimos una lista de los colores (desde colors.xml) que usaremos
    private val gameColors = listOf(
        R.color.game_red,
        R.color.game_green,
        R.color.game_blue,
        R.color.game_yellow
    )
    private var currentColorId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializarSonidos()
        configurarBotones()
        iniciarJuego()
    }

    private fun inicializarSonidos() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder().setMaxStreams(2).setAudioAttributes(audioAttributes).build()
        idSonidoAcierto = soundPool.load(requireContext(), R.raw.sonido_acierto, 1)
        idSonidoError = soundPool.load(requireContext(), R.raw.sonido_error, 1)
    }

    private fun configurarBotones() {
        binding.redButton.setOnClickListener { verificarRespuesta(R.color.game_red) }
        binding.greenButton.setOnClickListener { verificarRespuesta(R.color.game_green) }
        binding.blueButton.setOnClickListener { verificarRespuesta(R.color.game_blue) }
        binding.yellowButton.setOnClickListener { verificarRespuesta(R.color.game_yellow) }
    }

    private fun iniciarJuego() {
        score = 0
        binding.scoreValueText.text = score.toString()
        siguienteColor()
        iniciarTimer()
    }

    private fun iniciarTimer() {
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val segundosRestantes = millisUntilFinished / 1000
                binding.timeValueText.text = segundosRestantes.toString()
            }
            override fun onFinish() {
                // el tiempo se acabo, vamos a los resultados
                // pasamos el puntaje como argumento
                val action = GameFragmentDirections.actionGameFragmentToResultFragment(score)
                findNavController().navigate(action)
            }
        }.start()
    }

    private fun siguienteColor() {
        currentColorId = gameColors.random()
        val colorReal = ContextCompat.getColor(requireContext(), currentColorId)
        binding.colorToGuessView.setBackgroundColor(colorReal)

        // aplicamos la animacion al cuadro de color
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.scale_up)
        binding.colorToGuessView.startAnimation(anim)
    }

    private fun verificarRespuesta(colorSeleccionadoId: Int) {
        if (colorSeleccionadoId == currentColorId) {
            score++
            binding.scoreValueText.text = score.toString()
            soundPool.play(idSonidoAcierto, 1f, 1f, 0, 0, 1f)
        } else {
            soundPool.play(idSonidoError, 1f, 1f, 0, 0, 1f)
        }
        siguienteColor()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // es importante cancelar el timer para evitar que siga corriendo
        if (::timer.isInitialized) {
            timer.cancel()
        }
        _binding = null
    }
}