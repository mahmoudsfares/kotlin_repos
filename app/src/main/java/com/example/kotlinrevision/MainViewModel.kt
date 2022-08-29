package com.example.kotlinrevision

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinrevision.data.Resource
import com.example.kotlinrevision.networking.RetrofitInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: RetrofitInterface): ViewModel() {

    private val _title = MutableLiveData<Resource<String>>()
    val title: LiveData<Resource<String>>
        get() = _title

    suspend fun getTitle() {
        _title.postValue(Resource.Loading())
        try{ _title.postValue(Resource.Success(repo.getTitle().title)) }
        catch (throwable: Throwable){
            when(throwable){
                is HttpException -> {
                    if(throwable.message().isEmpty())
                        _title.postValue(Resource.Error("Error ${throwable.code()}"))
                    else
                        _title.postValue(Resource.Error("Error ${throwable.code()}: ${throwable.message()}"))
                }
                is IOException
                -> _title.postValue(Resource.Error("No internet connection!"))
                else
                -> _title.postValue(Resource.Error("Unexpected error occurred"))
            }
        }
    }
}