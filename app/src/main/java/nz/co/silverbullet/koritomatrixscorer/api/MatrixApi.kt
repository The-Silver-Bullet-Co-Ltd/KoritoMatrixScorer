package nz.co.silverbullet.koritomatrixscorer.api

import nz.co.silverbullet.koritomatrixscorer.model.Bike
import retrofit2.Response
import retrofit2.http.*


interface MatrixApi {

   // get bike list
   @GET("/matrix/bikes.php")
   suspend fun getBikes() : Response<List<Bike>>

   // reserve a bike
   @POST("/matrix/reservations.php/{bikeNumber}/{observerName}")
   suspend fun createReservation(
      @Path("bikeNumber") bikeNumber: String,
      @Path("observerName") observerName : String
   ) : Response<Bike>

   // release a bike
   @DELETE( "/matrix/reservations/{bikeNumber}/{observerName}")
   suspend fun deleteReservation(
      @Path("bikeNumber") bikeNumber: String,
      @Path("observerName") observerName : String
   ) : okhttp3.Response

   // get list of outstanding reservations?
   @GET("/matrix/reservations/")
   suspend fun getReservations() : Response<List<Bike>>

   // save timings for matrix attempt by bike
   @PUT("/matrix/bikes.php/{bikeNumber}/{observerName}")
   suspend fun updateBike(
      @Path( "bikeNumber") bikeNumber : String,
      @Path("observerName") observerName : String,
      @Body bike: Bike
   ) : Response<Bike>
}

/* https://restfulapi.net/rest-api-design-tutorial-with-example/ */

/**
GET 	    Used to retrieve a representation of a resource.
POST 	    Used to create new new resources and sub-resources
PUT 	    Used to update existing resources
PATCH 	    Used to update existing resources
DELETE 	    Used to delete existing resources

get list of bikes
GET /matrix/bikes/
RESPONSE
{
"bikes": [
{
"bikeNumber": "350",
"rider": "Dougy",
"make": "Husqvarna",
"model": "TE250",
"arrivedAt": "2022-02-06T13:21:22Z"
},
{
"bikeNumber": "350",
"rider": "Dougy",
"make": "Husqvarna",
"model": "TE250",
"arrivedAt": "2022-02-06T13:21:22Z"
},
{
"bikeNumber": "350",
"rider": "Dougy",
"make": "Husqvarna",
"model": "TE250",
"arrivedAt": "2022-02-06T13:21:22Z"
},
{
"bikeNumber": "350",
"rider": "Dougy",
"make": "Husqvarna",
"model": "TE250",
"arrivedAt": "2022-02-06T13:21:22Z"
}
]
}

reserve a bike
GET /matrix/bikes/{number}/reservations/{observerName}
RESPONSE
{
"errorcode": "0",
"bikeNumber": "350",
"reservedBy": "Brian",
"reservedAt": "2022-02-06T13:21:22Z"
}

release a bike
DELETE /matrix/reservations/{bikeNumber}/{observerName}
RESPONSE HTTP 200 (OK), 204 (NO CONTENT)
{
"errorcode": "0",
"bikeNumber": "350"
}

get list of outstanding reservations?
GET /matrix/reservations/
RESPONSE
{
"errorcode": "0"
"bikes": [
{
"bikeNumber": "350",
"rider": "Dougy",
"make": "Husqvarna",
"model": "TE250",
"arrivedAt": "2022-02-06T13:21:22Z",
"reservedBy": "Brian",
"reservedAt": "2022-02-06T13:21:22Z"
},
{
"bikeNumber": "351",
"rider": "Abe",
"make": "Husqvarna",
"model": "TE250",
"arrivedAt": "2022-02-06T13:21:22Z",
"reservedBy": "Martha",
"reservedAt": "2022-02-06T13:21:22Z"
},
{
"bikeNumber": "35",
"rider": "Daniel",
"make": "Husqvarna",
"model": "TE250",
"arrivedAt": "2022-02-06T13:21:22Z",
"reservedBy": "Tim",
"reservedAt": "2022-02-06T13:21:22Z"
},
{
"bikeNumber": "50",
"rider": "Lynley",
"make": "Husqvarna",
"model": "TE250",
"arrivedAt": "2022-02-06T13:21:22Z",
"reservedBy": "Abe",
"reservedAt": "2022-02-06T13:21:22Z"
}
]
}

save results for this pass through the matrix
PUT /matrix/bikes/{number}/
{
"observer": "Brian",
"results": {
"startTime": "00:00:00.000",
"endTime": "00:00:00.000",
"dabs": "2"
}
}
RESPONSE HTTP 200 OK
{
"errorcode": "0"
}

 */