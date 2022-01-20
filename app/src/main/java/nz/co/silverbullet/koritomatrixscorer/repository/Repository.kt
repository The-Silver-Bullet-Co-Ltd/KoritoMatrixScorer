package nz.co.silverbullet.koritomatrixscorer.repository

import nz.co.silverbullet.koritomatrixscorer.api.RetrofitInstance
import nz.co.silverbullet.koritomatrixscorer.model.Bike
import retrofit2.Response
import retrofit2.http.Body

class Repository {

    // get bike list
    suspend fun getBikes() : Response<List<Bike>> {
        return RetrofitInstance.api.getBikes()
    }

    // reserve a bike
    suspend fun createReservation(bikeNumber : String, observerName : String) : Response<Bike> {
        return RetrofitInstance.api.createReservation(bikeNumber, observerName)
    }

    // release a bike
    suspend fun deleteReservation(bikeNumber : String, observerName : String) : okhttp3.Response {
        return RetrofitInstance.api.deleteReservation(bikeNumber, observerName)
    }

    // get list of outstanding reservations?
    suspend fun getReservations() : Response<List<Bike>> {
        return RetrofitInstance.api.getReservations()
    }

    // save timings for matrix attempt by bike
    suspend fun updateBike(bikeNumber : String, observerName : String, @Body bike: Bike): Response<Bike> {
        return RetrofitInstance.api.updateBike(bikeNumber, observerName, bike)
    }
}