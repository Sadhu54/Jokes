package com.jokesapp.presentation.jokes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jokesapp.databinding.ItemJokeBinding
import com.jokesapp.domain.model.Jokes


class JokeAdapter : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    private var items = ArrayList<Jokes>()
    private var isFirstItemAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder(
            ItemJokeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, isFirstItemAdded)
        isFirstItemAdded = false
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // Update the adapter's data with a new joke added
    fun addJoke(joke: Jokes) {
        if (items.size >= 10) {
            // Remove the last item to keep the list size at 10
            items.removeAt(items.size - 1)
        }
        items.add(0, joke)
        isFirstItemAdded = true
        notifyDataSetChanged()
    }

    fun submitJokeList(list:List<Jokes>)
    {
        items= ArrayList(list)
        notifyDataSetChanged()
    }

    inner class JokeViewHolder(private val binding: ItemJokeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Bind data to the JokeViewHolder
        fun bind(item: Jokes, isFirstItemAdded: Boolean) {
            binding.run {
                tvJoke.text = item.joke
                if (isFirstItemAdded) {
                    // Animate the first item from small to slightly bigger
                    itemView.scaleX = 0.7f
                    itemView.scaleY = 0.7f
                    itemView.alpha = 0.5f
                    itemView.animate()
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .alpha(1.0f)
                        .setDuration(500)
                        .setListener(null) // Clear any previous listeners
                        .start()
                } else {
                    // Set the scale and alpha to their original values for other items
                    itemView.scaleX = 1.0f
                    itemView.scaleY = 1.0f
                    itemView.alpha = 1.0f
                }
            }
        }
    }
}
