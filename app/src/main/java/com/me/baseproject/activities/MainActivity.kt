package com.me.baseproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.me.baseproject.R
import com.me.baseproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        navController = findNavController(R.id.navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun updateAppBarName(text: String) {
        binding?.tvAppBarTitle?.text = text
        //val animation: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        //binding?.tvAppBarTitle?.startAnimation(animation)
    }
}