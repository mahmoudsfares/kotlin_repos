package com.example.kotlinrevision

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinrevision.data.AppDatabase
import com.example.kotlinrevision.data.Resource
import com.example.kotlinrevision.data.TaskIdDao
import com.example.kotlinrevision.data.pojo.TaskId
import com.example.kotlinrevision.networking.RetrofitInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: RetrofitInterface, private val database: AppDatabase): ViewModel() {

    private val _title = MutableStateFlow<Resource<String>>(Resource.Loading())
    val title: StateFlow<Resource<String>>
        get() = _title

    fun getTitle() {
        viewModelScope.launch (Dispatchers.IO){
            _title.value = Resource.Loading()
            try {
                val taskId = database.getTaskIdDao().getSavedId()
                if(taskId == null)
                    _title.value = Resource.Success(repo.getTitle(1).title)
                else
                    _title.value = Resource.Success(repo.getTitle(taskId.taskId).title)
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        if (throwable.message().isEmpty())
                            _title.value = Resource.Error("Error ${throwable.code()}")
                        else
                            _title.value = Resource.Error("Error ${throwable.code()}: ${throwable.message()}")
                    }
                    is IOException -> _title.value = Resource.Error("No internet connection!")
                    else -> _title.value = Resource.Error("Unexpected error occurred")
                }
            }
        }
    }

    fun updateSelectedTaskId(taskId: String){
        viewModelScope.launch {
            database.getTaskIdDao().clear()
            database.getTaskIdDao().insert(TaskId(taskId.toInt()))
            getTitle()
        }
    }

    //TODO: use the following instead of the previous code when working with LiveData

//    private val _title = MutableLiveData<Resource<String>>()
//    val title: LiveData<Resource<String>>
//        get() = _title
//
//    fun getTitle() {
//        viewModelScope.launch {
//            _title.postValue(Resource.Loading())
//            try {
//                _title.postValue(Resource.Success(repo.getTitle().title))
//            }
//            catch (throwable: Throwable){
//                when(throwable){
//                    is HttpException -> {
//                        if(throwable.message().isEmpty())
//                            _title.postValue(Resource.Error("Error ${throwable.code()}"))
//                        else
//                            _title.postValue(Resource.Error("Error ${throwable.code()}: ${throwable.message()}"))
//                    }
//                    is IOException
//                    -> _title.postValue(Resource.Error("No internet connection!"))
//                    else
//                    -> _title.postValue(Resource.Error("Unexpected error occurred"))
//                }
//            }
//        }
//    }
}