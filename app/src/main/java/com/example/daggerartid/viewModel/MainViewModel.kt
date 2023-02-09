package com.example.daggerartid.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.daggerartid.data.MainRepository
import com.example.daggerartid.data.model.DataModelItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val artList = MutableLiveData<ArrayList<DataModelItem>>()
    val errorMessage = MutableLiveData<String>()

    fun fetchAllArtList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = mainRepository.getList()
            if (response.isSuccessful) {
                artList.postValue(response.body())
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }
}
