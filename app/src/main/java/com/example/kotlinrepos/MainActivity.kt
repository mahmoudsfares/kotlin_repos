package com.example.kotlinrepos

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.kotlinrepos.networking.Resource
import com.example.kotlinrepos.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.getTitle()

        // flows are not lifecycle aware
        lifecycleScope.launchWhenStarted {
            viewModel.title.collect {
                if (it is Resource.Loading) {
                    binding.progressBar.isVisible = true
                    binding.text.isVisible = false
                } else {
                    binding.progressBar.isVisible = false
                    binding.text.isVisible = true
                    if (it is Resource.Error) {
                        binding.text.setTextColor(Color.parseColor("#FF0000"))
                        binding.text.text = it.error
                    } else {
                        binding.text.setTextColor(Color.parseColor("#000000"))
                        binding.text.text = it.data
                    }
                }
            }
        }

        //TODO: use the following instead of the previous coroutine when working with LiveData

//        // livedata is lifecycle aware so there's no need for coroutine scope
//        viewModel.title.observe(this@MainActivity) {
//            if (it is Resource.Loading){
//                binding.progressBar.isVisible = true
//                binding.text.isVisible = false
//            } else {
//                binding.progressBar.isVisible = false
//                binding.text.isVisible = true
//                if(it is Resource.Error){
//                    binding.text.setTextColor(Color.parseColor("#FF0000"))
//                    binding.text.text = it.error
//                } else {
//                    binding.text.setTextColor(Color.parseColor("#000000"))
//                    binding.text.text = it.data
//                }
//            }
//        }

        binding.refresher.setOnRefreshListener {
            // added a bit of a delay to show the refresh visuals when the view is swiped down
            Timer().schedule(1000) {
                viewModel.getTitle()
                binding.refresher.isRefreshing = false
            }
        }

        binding.submitBtn.setOnClickListener {
            if(binding.idEt.text.isNotEmpty()) {
                viewModel.updateSelectedTaskId(binding.idEt.text.toString())
            }
        }
    }
}