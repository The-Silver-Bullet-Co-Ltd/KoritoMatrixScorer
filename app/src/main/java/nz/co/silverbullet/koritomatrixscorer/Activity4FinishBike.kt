package nz.co.silverbullet.koritomatrixscorer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import nz.co.silverbullet.koritomatrixscorer.model.Bike
import nz.co.silverbullet.koritomatrixscorer.repository.Repository
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val TAG4 = "ActivityFinishBike"

class Activity4FinishBike : AppCompatActivity() {

    private lateinit var bundle : Bundle
    private lateinit var viewModel: Activity4ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_4_finish_bike)

        val repository = Repository()
        val viewModelFactory = Activity4ViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory)[Activity4ViewModel::class.java]

        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                Toast.makeText(this,response.body()?.message, Toast.LENGTH_SHORT).show()
                Log.d("Activity 4 Response OK ", response.code().toString())
                val intent = Intent(this, Activity1SelectBike::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            } else {
                Log.d("Activity 4 Response ", response.code().toString())
            }
        })

        bundle = intent.extras!!

        val nameText =  findViewById<TextView>(R.id.name4Text)
        val numberText =  findViewById<TextView>(R.id.number4Text)
        val bikeText =  findViewById<TextView>(R.id.bike4Text)
        val startTime = findViewById<TextView>(R.id.startTimeText)
        val finishTime = findViewById<TextView>(R.id.finishTimeText)
        val elapsedTime = findViewById<TextView>(R.id.elapsedTimeText)
        val dabTime = findViewById<TextView>(R.id.dabTimeText)
        val totalTime = findViewById<TextView>(R.id.totalTimeText)
        val dabsCounted = findViewById<TextView>(R.id.dabsCountedText)

        nameText.text = bundle.getString("rider")
        numberText.text = bundle.getString("number")
        bikeText.text = bundle.getString("bike")
        dabsCounted.text = bundle.getInt("dabs").toString()

        // Creating time format
        val simple: DateFormat = SimpleDateFormat("HH:mm:ss:SSS")
        val shortened: DateFormat = SimpleDateFormat("mm:ss:SSS")

        // start time
        var totalMilliseconds = bundle.getLong("start" )
        // Creating date from milliseconds
        // using Date() constructor
        var result = Date(totalMilliseconds)
        // Formatting Date according to the
        // given format
        startTime.text = simple.format(result)

        // finish time
        totalMilliseconds = bundle.getLong("finish" )
        result = Date(totalMilliseconds)
        finishTime.text = simple.format(result)

        // difference
        totalMilliseconds -= bundle.getLong("start")
        result = Date(totalMilliseconds)
        elapsedTime.text = shortened.format(result)

        // dab penalties
        totalMilliseconds = (bundle.getInt("dabs") * 10000).toLong()
        result = Date(totalMilliseconds)
        dabTime.text = shortened.format(result)

        // total penalties
        totalMilliseconds += (bundle.getLong("finish") - bundle.getLong("start"))
        result = Date(totalMilliseconds)
        totalTime.text = shortened.format(result)
    }

    fun submitResults(view: View) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val observerName : String = prefs.getString("observer","")!!

        var bike = Bike(
            bundle.getLong("id"),
            bundle.getString("number")!!,
            bundle.getString("make")!!,
            bundle.getString("model")!!,
            bundle.getString("rider")!!,
            bundle.getInt("lap")!!,
            true,
            observerName,
            bundle.getLong("start")!!,
            bundle.getLong("finish")!!,
            bundle.getInt("dabs")!!,
            bundle.getLong("matrixTimePenalties")!!
        )
        /* make api call -> viewModel -> repository -> RetrofitInstance -> MatrixApi */
        viewModel.updateBike(bike)
    }

    override fun onBackPressed() {
//        val intent = Intent(this, Activity3ScoreBike::class.java)
//        intent.putExtras(bundle)
//        startActivity(intent)
    }

//    override fun onOptionsItemSelected(item : MenuItem): Boolean {
//        val intent = Intent(this, Activity3ScoreBike::class.java)
//        intent.putExtras(bundle)
//        startActivity(intent)
//        return true
//    }


}