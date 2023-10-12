package com.me.baseproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.livevalue.customer.apiServices.viewModels.AnimeListViewModel
import com.me.baseproject.R
import com.me.baseproject.activities.MainActivity
import com.me.baseproject.adapyers.AnimeAdapter
import com.me.baseproject.apiServices.models.AnimeModel
import com.me.baseproject.databinding.FragmentAnimeListBinding
import com.me.baseproject.utils.AnimeAdapterClickInterface
import com.me.baseproject.utils.ApiStatus

class AnimeListFragment : Fragment() {

    private var binding: FragmentAnimeListBinding? = null
    private lateinit var mainActivity: MainActivity

    private lateinit var animeListVM: AnimeListViewModel

    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var linearLMAnimeList: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity

        animeAdapter = AnimeAdapter(mainActivity, object : AnimeAdapterClickInterface {
            override fun animeAdapterClicked(animeModel: AnimeModel?) {

            }
        })

        animeListVM = ViewModelProvider(this)[AnimeListViewModel::class.java]
        animeListVM.getAnimeListsAS.observe(this) { apiStatus ->
            if (apiStatus == ApiStatus.ApiHit) {
                if (animeListVM.animeList.isEmpty()) {
                    binding?.llEmptyList?.visibility = View.VISIBLE
                    binding?.rvAnime?.visibility = View.GONE
                } else {
                    binding?.llEmptyList?.visibility = View.GONE
                    binding?.rvAnime?.visibility = View.VISIBLE
                    if (animeListVM.lastIndex == 0) {
                        animeAdapter.updateList(animeListVM.animeList)
                    } else {
                        animeAdapter.addAll(animeListVM.animeList, animeListVM.lastIndex)
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimeListBinding.inflate(layoutInflater)

        linearLMAnimeList = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        binding?.rvAnime?.layoutManager = linearLMAnimeList
        binding?.rvAnime?.adapter = animeAdapter

        binding?.rvAnime?.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(linearLMAnimeList.findLastVisibleItemPosition() == linearLMAnimeList.itemCount -1)
                    animeListVM.pagination(mainActivity, animeListVM.animeList.size - 1)
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        mainActivity = activity as MainActivity
        mainActivity.updateAppBarName(mainActivity.resources.getString(R.string.anime_list))
        animeListVM.getAnimeList(mainActivity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}