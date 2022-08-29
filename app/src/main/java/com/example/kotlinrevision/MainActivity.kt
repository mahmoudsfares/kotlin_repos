package com.example.kotlinrevision

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.kotlinrevision.data.Resource
import com.example.kotlinrevision.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val activityViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        lifecycleScope.launchWhenStarted {
            activityViewModel.getTitle()
            activityViewModel.title.observe(this@MainActivity) {
               if (it is Resource.Loading){
                   binding.progressBar.isVisible = true
                   binding.text.isVisible = false
               } else {
                   binding.progressBar.isVisible = false
                   binding.text.isVisible = true
                   if(it is Resource.Error){
                       binding.text.setTextColor(Color.parseColor("#FF0000"))
                       binding.text.text = it.error
                   } else {
                       binding.text.setTextColor(Color.parseColor("#000000"))
                       binding.text.text = it.data
                   }
               }
            }
        }
    }
}