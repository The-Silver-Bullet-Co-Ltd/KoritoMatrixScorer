package nz.co.silverbullet.koritomatrixscorer.repository

import nz.co.silverbullet.koritomatrixscorer.api.RetrofitInstance
import nz.co.silverbullet.koritomatrixscorer.model.Bike
import nz.co.silverbullet.koritomatrixscorer.model.BikeListWrapper
import nz.co.silverbullet.koritomatrixscorer.model.BikeWrapper
import retrofit2.Response
import retrofit2.http.Body

class Repository {

    // get bike list
    suspend fun getBikes() : Response<BikeListWrapper> {
        return RetrofitInstance.api.getBikes()
    }

    // reserve a bike
    suspend fun createReservation(lapId : Long, bikeNumber : String, observerName : String) : Response<BikeWrapper> {
        return RetrofitInstance.api.createReservation(lapId, bikeNumber, observerName)
    }

    // release a bike
    suspend fun deleteReservation(lapId : Long, bikeNumber : String, observerName : String) : Response<BikeWrapper> {
        return RetrofitInstance.api.deleteReservation(lapId, bikeNumber, observerName)
    }

    // get list of outstanding reservations?
    suspend fun getReservations() : Response<BikeListWrapper> {
        return RetrofitInstance.api.getReservations()
    }

    // save timings for matrix attempt by bike
    suspend fun updateBike(@Body bike: Bike): Response<BikeWrapper> {
        return RetrofitInstance.api.updateBike(bike)
    }
}