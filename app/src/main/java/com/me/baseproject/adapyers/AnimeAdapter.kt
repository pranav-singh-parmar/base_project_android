package com.me.baseproject.adapyers

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.me.baseproject.apiServices.models.AnimeModel
import com.me.baseproject.databinding.AdapterAnimeBinding
import com.me.baseproject.databinding.AdapterLoaderBinding
import com.me.baseproject.utils.AnimeAdapterInterface
import com.me.baseproject.utils.RecyclerViewsEnum
import com.me.baseproject.utils.Singleton

class AnimeAdapter(
    private val context: Activity,
    private val animeAdapterInterface: AnimeAdapterInterface
) : RecyclerView.Adapter<AnimeAdapter.ViewHolder>() {

    private val animeList = mutableListOf<AnimeModel?>()
    private var showLoaderAtBottom = true

    inner class ViewHolder(private val binding: ViewDataBinding?) :
        RecyclerView.ViewHolder(binding?.root ?: View(context)) {
        fun bindData(animeModel: AnimeModel?) {
            if (binding is AdapterAnimeBinding) {
                Singleton.generalFunctions.glide(
                    context,
                    animeModel?.image ?: "",
                    binding.ivAnime
                )
                binding.tvAnimeTitle.text = animeModel?.title ?: ""
                binding.tvAnimeType.text = animeModel?.type ?: ""
                binding.cdOuterView.setOnClickListener {
                    animeAdapterInterface.animeAdapterClicked(animeModel)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == animeList.size) {
            RecyclerViewsEnum.Footer.value
        } else {
            RecyclerViewsEnum.ContentView.value
        }
    }

    override fun getItemCount(): Int {
        return if (showLoaderAtBottom) {
            // have to show loader at bottom of recycler view
            animeList.size + 1
        } else {
            animeList.size
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = when (viewType) {
            RecyclerViewsEnum.ContentView.value -> {
                AdapterAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            }
            RecyclerViewsEnum.Footer.value -> {
                AdapterLoaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            }
            else -> {null}
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < animeList.size) {
            holder.bindData(animeList[position])
            animeAdapterInterface.visibleItemPosition(position)
        }
    }

    fun loadedAllData(value: Boolean) {
        showLoaderAtBottom = !value
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