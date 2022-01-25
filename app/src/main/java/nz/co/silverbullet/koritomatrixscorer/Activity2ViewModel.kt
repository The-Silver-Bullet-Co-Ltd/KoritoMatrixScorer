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

class Activity2ViewModel (private val repository: Repository) : ViewModel()
{
    val myResponse: MutableLiveData<Response<Bike>> = MutableLiveData()

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, exception ->
        run {
            Log.i(TAG2, "Coroutine Exception: " + exception.message.toString())
        }
    }

    // reserve a bike
    fun createReservation(bikeNumber: String, observerName: String) {
        viewModelScope.launch(
            Dispatchers.IO + coroutineExceptionHandler) {
            val response = repository.createReservation(bikeNumber, observerName)
            if (response.isSuccessful) {
                myResponse.value = response
                Log.i(TAG, "Successfully reserved bike")
            } else {
                // error
                Log.i(TAG, response.code().toString() + ": " + response.message().toString())
            }
        }
    }

    // release a bike
    fun deleteReservation(bikeNumber: String, observerName: String) {
        viewModelScope.launch {
            val response = repository.deleteReservation(bikeNumber, observerName)
            // myResponse.value = response
        }
    }
}