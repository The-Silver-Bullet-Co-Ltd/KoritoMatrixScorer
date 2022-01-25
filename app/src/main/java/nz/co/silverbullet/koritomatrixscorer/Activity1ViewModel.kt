package nz.co.silverbullet.koritomatrixscorer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nz.co.silverbullet.koritomatrixscorer.model.Bike
import nz.co.silverbullet.koritomatrixscorer.repository.Repository
import retrofit2.Response

class Activity1ViewModel(private val repository: Repository) : ViewModel() {

    val myResponse : MutableLiveData<Response<List<Bike>>> = MutableLiveData()

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, exception ->
        run {
            Log.i(TAG, "Coroutine Exception: " + exception.message.toString())
        }
    }

    // get bike list
    fun getBikes() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val response = repository.getBikes()
            if (response.isSuccessful) {
                myResponse.postValue(response)
                //myResponse.value = response
                Log.i(TAG, "Successfully retrieved bike list")
            } else {
                // error
                Log.i(TAG, response.code().toString() + ": " + response.message().toString())
            }
        }
    }
}