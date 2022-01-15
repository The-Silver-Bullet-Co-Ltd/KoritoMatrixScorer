package nz.co.silverbullet.koritomatrixscorer.api

import nz.co.silverbullet.koritomatrixscorer.data.Bike
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Matrix {

   @GET("/matrix/bikes")
   suspend fun getBikes(): Response<List<Bike>>

   @POST("/matrix/bikes")
   suspend fun updateBike(@Body bike: Bike): Response<JSONObject>
}