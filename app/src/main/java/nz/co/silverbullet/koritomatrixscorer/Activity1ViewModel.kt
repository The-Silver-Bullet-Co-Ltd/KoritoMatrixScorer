package nz.co.silverbullet.koritomatrixscorer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nz.co.silverbullet.koritomatrixscorer.model.Bike
import nz.co.silverbullet.koritomatrixscorer.repository.Repository
import retrofit2.Response
import retrofit2.http.Body

class Activity1ViewModel(private val repository: Repository) : ViewModel() {

    val myResponse : MutableLiveData<Response<List<Bike>>> = MutableLiveData()

    // get bike list
    fun getBikes() {
        viewModelScope.launch {
            val response  = repository.getBikes()
            myResponse.value = response
        }
    }

    // reserve a bike
    fun createReservation(bikeNumber: String, observerName: String)  {
        viewModelScope.launch {
            val response = repository.createReservation(bikeNumber, observerName)
            //myResponse.value = response
        }
    }

    // release a bike
    fun deleteReservation(bikeNumber : String, observerName : String) {
        viewModelScope.launch {
            val response = repository.deleteReservation(bikeNumber, observerName)
            // myResponse.value = response
        }
    }

    // get list of outstanding reservations?
    fun getReservations()  {
        viewModelScope.launch {
            val response = repository.getReservations()
            myResponse.value = response
        }
    }

    // save timings for matrix attempt by bike
    fun updateBike(bikeNumber: String, observerName: String, @Body bike: Bike) {
        viewModelScope.launch {
            val response = repository.updateBike(bikeNumber, observerName, bike)
            //myResponse.value = response
        }
    }
}