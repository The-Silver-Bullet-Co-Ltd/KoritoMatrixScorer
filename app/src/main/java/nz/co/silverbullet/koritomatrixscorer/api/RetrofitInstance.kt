package nz.co.silverbullet.koritomatrixscorer.api

import nz.co.silverbullet.koritomatrixscorer.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
object because want to make this a singleton instance
 */
object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : MatrixApi by lazy {
        retrofit.create(MatrixApi::class.java)
    }
}