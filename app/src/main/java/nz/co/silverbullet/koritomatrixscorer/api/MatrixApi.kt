package nz.co.silverbullet.koritomatrixscorer.api

import nz.co.silverbullet.koritomatrixscorer.model.Bike
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface MatrixApi {

   @GET("/matrix/bikes.php")
   suspend fun getBikes(): Response<List<Bike>>

   @POST("/matrix/bikes")
   suspend fun updateBike(@Body bike: Bike): Response<JSONObject>
}