package nz.co.silverbullet.koritomatrixscorer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import nz.co.silverbullet.koritomatrixscorer.repository.Repository

const val TAG2 = "StartBikeActivity"

class Activity2StartBike : AppCompatActivity() {

    private lateinit var bundle : Bundle
    private lateinit var viewModel: Activity2ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2_start_bike)

        bundle = intent.extras!!

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val observerName = prefs.getString("observer","")

        val repository = Repository()
        val viewModelFactory = Activity2ViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory)[Activity2ViewModel::class.java]

        /* make api call -> viewModel -> repository -> RetrofitInstance -> MatrixApi */
        viewModel.createReservation(bundle.getString("number")!!,observerName!!)

        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                val bike = viewModel.myResponse.value?.body()
                val nameText =  findViewById<TextView>(R.id.name2Text)
                val numberText =  findViewById<TextView>(R.id.number2Text)
                val bikeText =  findViewById<TextView>(R.id.bike2Text)

                nameText.text = bike?.rider
                numberText.text = bike?.number
                bikeText.text = bike?.bike()
            } else {
                Log.d(TAG2, response.code().toString())
            }
        })
    }

    fun startBike(view: View) {
        val tsLong = System.currentTimeMillis()
        bundle.putLong("start", tsLong)

        val intent = Intent(this, Activity3ScoreBike::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}