package nz.co.silverbullet.koritomatrixscorer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nz.co.silverbullet.koritomatrixscorer.model.BikeWrapper
import nz.co.silverbullet.koritomatrixscorer.repository.Repository
import retrofit2.Response

class Activity2ViewModel (private val repository: Repository) : ViewModel()
{
    val myResponse: MutableLiveData<Response<BikeWrapper>> = MutableLiveData()

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, exception ->
        run {
            Log.i(TAG2, "Coroutine Exception: " + exception.message.toString())
        }
    }

    // reserve a bike
    fun createReservation(lapId : Long, bikeNumber: String, observerName: String) {
        viewModelScope.launch(
            Dispatchers.IO + coroutineExceptionHandler) {
            val response = repository.createReservation(lapId, bikeNumber, observerName)
            if (response.isSuccessful) {
                Log.i(TAG2, response.body()!!.message)
                myResponse.postValue(response)
            } else {
                // error
                Log.i(TAG2, response.code().toString() + ": " + response.message().toString())
                myResponse.postValue(response)
            }
        }
    }

    // release a bike
    fun deleteReservation(lapId : Long, bikeNumber: String, observerName: String) {
        viewModelScope.launch {
            val response = repository.deleteReservation(lapId, bikeNumber, observerName)
            // myResponse.value = response
        }
    }
}