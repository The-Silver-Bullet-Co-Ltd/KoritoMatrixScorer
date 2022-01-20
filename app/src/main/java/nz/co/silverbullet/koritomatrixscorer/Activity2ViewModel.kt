package nz.co.silverbullet.koritomatrixscorer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nz.co.silverbullet.koritomatrixscorer.model.Bike
import nz.co.silverbullet.koritomatrixscorer.repository.Repository
import retrofit2.Response

class Activity2ViewModel (private val repository: Repository) : ViewModel()
{
    val myResponse: MutableLiveData<Response<Bike>> = MutableLiveData()

    // reserve a bike
    fun createReservation(bikeNumber: String, observerName: String) {
        viewModelScope.launch {
            val response = repository.createReservation(bikeNumber, observerName)
            myResponse.value = response
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