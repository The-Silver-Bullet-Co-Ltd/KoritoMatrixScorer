package nz.co.silverbullet.koritomatrixscorer

import nz.co.silverbullet.koritomatrixscorer.api.Matrix
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    val api: Matrix by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.2")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Matrix::class.java)
    }
}