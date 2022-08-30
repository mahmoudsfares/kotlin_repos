package com.example.kotlinrepos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinrepos.data.AppDatabase
import com.example.kotlinrepos.networking.Resource
import com.example.kotlinrepos.data.pojo.TaskId
import com.example.kotlinrepos.networking.RetrofitInterface
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
        // database calls can't be executed on the main thread
        viewModelScope.launch (Dispatchers.IO){
            _title.value = Resource.Loading()
            try {
                val taskId = database.getTaskIdDao().getSavedId()
                // no saved users
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

    //TODO: use the following instead of the previous code when working with LiveData

//    private val _title = MutableLiveData<Resource<String>>()
//    val title: LiveData<Resource<String>>
//        get() = _title
//
//    fun getTitle() {
//        // database calls can't be executed on the main thread
//        viewModelScope.launch (Dispatchers.IO){
//            _title.postValue(Resource.Loading())
//            try {
//                val taskId = database.getTaskIdDao().getSavedId()
//                // no saved users
//                if(taskId == null)
//                    // (_title.value = value) won't work off of the main thread
//                    _title.postValue(Resource.Success(repo.getTitle(1).title))
//                else
//                    _title.postValue(Resource.Success(repo.getTitle(taskId.taskId).title))
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

    fun updateSelectedTaskId(taskId: String){
        viewModelScope.launch {
            database.getTaskIdDao().clear()
            database.getTaskIdDao().insert(TaskId(taskId.toInt()))
            getTitle()
        }
    }
}