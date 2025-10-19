package com.example.juegodecolores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScoreAdapter(private val scores: List<Int>) : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    // esta clase interna representa la vista de cada fila
    class ScoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val scoreText: TextView = view.findViewById(R.id.score_item_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        // creamos (inflamos) la vista de la fila a partir de nuestro xml
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.score_list_item, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        // conectamos los datos con la vista
        val score = scores[position]
        holder.scoreText.text = "Partida ${position + 1}: $score puntos"
    }

    // el adaptador necesita saber cuantos items hay en la lista
    override fun getItemCount() = scores.size
}