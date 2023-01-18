package com.me.baseproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.me.baseproject.R
import com.me.baseproject.activities.MainActivity
import com.me.baseproject.databinding.FragmentAnimeListBinding

class AnimeListFragment : Fragment() {

    private var binding: FragmentAnimeListBinding? = null
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimeListBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        mainActivity.updateAppBarName(mainActivity.resources.getString(R.string.anime_list))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}