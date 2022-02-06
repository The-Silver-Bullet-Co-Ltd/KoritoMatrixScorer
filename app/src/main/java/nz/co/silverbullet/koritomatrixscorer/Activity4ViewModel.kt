package nz.co.silverbullet.koritomatrixscorer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nz.co.silverbullet.koritomatrixscorer.model.Bike
import nz.co.silverbullet.koritomatrixscorer.model.BikeWrapper
import nz.co.silverbullet.koritomatrixscorer.repository.Repository
import retrofit2.Response
import retrofit2.http.Body

class Activity4ViewModel (private val repository: Repository) : ViewModel() {

    val myResponse: MutableLiveData<Response<BikeWrapper>> = MutableLiveData()

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, exception ->
        run {
            Log.i(TAG4, "Coroutine Exception: " + exception.message.toString())
        }
    }

    // save timings for matrix attempt by bike
    fun updateBike(@Body bike: Bike) {
        viewModelScope.launch(
            Dispatchers.IO + coroutineExceptionHandler) {
            val response = repository.updateBike(bike)
            if (response.isSuccessful) {
                myResponse.postValue(response)
                Log.i(TAG4, "Successfully saved bike")
            } else {
                // error
                Log.i(TAG4, response.code().toString() + ": " + response.message().toString())
            }
        }
    }
}