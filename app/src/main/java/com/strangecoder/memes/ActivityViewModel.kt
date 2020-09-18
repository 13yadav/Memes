package com.strangecoder.memes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ActivityViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        getMemes()
    }

    private val _meme = MutableLiveData<Meme>()
    val meme: LiveData<Meme>
        get() = _meme

    fun getMemes() {
        coroutineScope.launch {
            val result = MemesApi.retrofitService.getMemeAsync().await()
            Log.d("JJJ", "Success: $result")
            _meme.value = result
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}