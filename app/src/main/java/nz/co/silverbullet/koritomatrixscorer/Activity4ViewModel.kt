package nz.co.silverbullet.koritomatrixscorer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nz.co.silverbullet.koritomatrixscorer.model.Bike
import nz.co.silverbullet.koritomatrixscorer.repository.Repository
import retrofit2.Response
import retrofit2.http.Body

class Activity4ViewModel (private val repository: Repository) : ViewModel() {

    val myResponse: MutableLiveData<Response<Bike>> = MutableLiveData()

    // save timings for matrix attempt by bike
    fun updateBike(bikeNumber: String, observerName: String, @Body bike: Bike) {
        viewModelScope.launch {
            val response = repository.updateBike(bikeNumber, observerName, bike)
            myResponse.value = response
        }
    }
}