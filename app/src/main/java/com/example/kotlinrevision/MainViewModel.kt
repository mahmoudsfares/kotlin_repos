package com.example.kotlinrevision

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinrevision.networking.RetrofitInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: RetrofitInterface): ViewModel() {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    suspend fun getTitle() {
        try{ _title.postValue(repo.getTitle().title) }
        catch (e: Exception){
            _title.postValue("Error occurred: ${e.localizedMessage}")
        }
    }
}