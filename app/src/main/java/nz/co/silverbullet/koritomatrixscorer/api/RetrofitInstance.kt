package nz.co.silverbullet.koritomatrixscorer.api

import androidx.preference.PreferenceManager
import nz.co.silverbullet.koritomatrixscorer.Activity1SelectBike
import nz.co.silverbullet.koritomatrixscorer.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
object because want to make this a singleton instance
 */
object RetrofitInstance {

    val prefs = PreferenceManager.getDefaultSharedPreferences(Activity1SelectBike.instance)
    val base_url = "http://" + if (prefs.getString("hostname", BASE_URL).toString().isNullOrBlank()) BASE_URL else prefs.getString("hostname", BASE_URL).toString()

    private val retrofit by lazy {

        Retrofit.Builder()
            .baseUrl(this.base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : MatrixApi by lazy {
        retrofit.create(MatrixApi::class.java)
    }

    fun getBaseUrl() : String {
        return this.base_url
    }
}