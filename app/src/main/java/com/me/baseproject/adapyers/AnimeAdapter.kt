package com.me.baseproject.adapyers

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.me.baseproject.apiServices.models.AnimeModel
import com.me.baseproject.databinding.AdapterAnimeBinding
import com.me.baseproject.utils.AnimeAdapterClickInterface

class AnimeAdapter(
    private val context: Activity,
    private val animeAdapterInterface: AnimeAdapterClickInterface
) : RecyclerView.Adapter<AnimeAdapter.ViewHolder>() {

    private val animeList = mutableListOf<AnimeModel?>()

    inner class ViewHolder(private val binding: AdapterAnimeBinding?) :
        RecyclerView.ViewHolder(binding?.root ?: View(context)) {
        fun bindData(animeModel: AnimeModel?) {
            if (binding != null) {
//                    binding.cdOuterView.setOnClickListener {
//                        animeAdapterInterface.animeAdapterClicked(animeModel)
//                    }
            }
        }
    }

    override fun getItemCount(): Int {
        return animeList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = AdapterAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(animeList[position])
    }

    fun updateList(newMatches: List<AnimeModel?>) {
        animeList.clear()
        animeList.addAll(newMatches)
        notifyDataSetChanged()
    }

    fun addAll(newMatches: List<AnimeModel?>, position: Int) {
        animeList.addAll(newMatches.slice(position until newMatches.count()))
        notifyItemChanged(position)
    }
}