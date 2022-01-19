package nz.co.silverbullet.koritomatrixscorer.repository

import nz.co.silverbullet.koritomatrixscorer.api.RetrofitInstance
import nz.co.silverbullet.koritomatrixscorer.model.Bike
import retrofit2.Response

class Repository {

    suspend fun getBikes() : Response<List<Bike>> {
        return RetrofitInstance.api.getBikes()
    }
}