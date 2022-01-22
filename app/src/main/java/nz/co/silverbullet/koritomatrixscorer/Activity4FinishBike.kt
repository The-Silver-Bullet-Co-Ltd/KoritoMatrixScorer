package nz.co.silverbullet.koritomatrixscorer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import nz.co.silverbullet.koritomatrixscorer.model.Bike
import nz.co.silverbullet.koritomatrixscorer.repository.Repository

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

        nameText.text = bundle.getString("rider")
        numberText.text = bundle.getString("number")
        bikeText.text = bundle.getString("bike")

        var totalMilliseconds = bundle.getLong("start" )

        var totalSeconds = totalMilliseconds / 1000
        var milliSeconds = totalMilliseconds - (totalSeconds * 1000)

        var currentSeconds = totalSeconds % 60

        var totalMinutes = totalSeconds / 60
        var currentMinutes = totalMinutes % 60

        var totalHours = totalMinutes / 60
        var currentHours = totalHours % 24

        startTime.text = getString(R.string.time_format,currentHours, currentMinutes,currentSeconds, milliSeconds)

        totalMilliseconds = bundle.getLong("finish" )

        totalSeconds = totalMilliseconds / 1000
        milliSeconds = totalMilliseconds - (totalSeconds * 1000)

        currentSeconds = totalSeconds % 60

        totalMinutes = totalSeconds / 60
        currentMinutes = totalMinutes % 60

        totalHours = totalMinutes / 60
        currentHours = totalHours % 24

        finishTime.text = getString(R.string.time_format,currentHours, currentMinutes,currentSeconds, milliSeconds)

        totalMilliseconds -= bundle.getLong("start")

        // Toast.makeText(this, ""+ totalMilliseconds, Toast.LENGTH_SHORT).show()
        totalSeconds = totalMilliseconds / 1000
        milliSeconds = totalMilliseconds - (totalSeconds * 1000)

        currentSeconds = totalSeconds % 60

        totalMinutes = totalSeconds / 60
        currentMinutes = totalMinutes % 60

        totalHours = totalMinutes / 60
        currentHours = totalHours % 24

        elapsedTime.text = getString(R.string.time_format,currentHours, currentMinutes,currentSeconds, milliSeconds)

        totalMilliseconds += bundle.getLong("start")
        totalSeconds = totalMilliseconds / 1000
        milliSeconds = totalMilliseconds - (totalSeconds * 1000)

        currentSeconds = totalSeconds % 60

        totalMinutes = totalSeconds / 60
        currentMinutes = totalMinutes % 60

        totalHours = totalMinutes / 60
        currentHours = totalHours % 24

        dabTime.text = getString(R.string.time_format,currentHours, currentMinutes,currentSeconds, milliSeconds)
    }

    fun submitResults(view: View) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val observerName = prefs.getString("observer","")

        var bike = Bike(
            bundle.getLong("id"),
            bundle.getString("number")!!,
            bundle.getString("make")!!,
            bundle.getString("model")!!,
            bundle.getString("rider")!!,
            bundle.getLong("start")!!,
            bundle.getLong("finish")!!,
            bundle.getInt("dabs")!!,
            bundle.getLong("matrixTimePenalties")!!
        )
        /* make api call -> viewModel -> repository -> RetrofitInstance -> MatrixApi */
        viewModel.updateBike(bundle.getString("number")!!,observerName!!, bike)
    }

    fun restartScoring(view: View) {
        val intent = Intent(this, Activity3ScoreBike::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onBackPressed() {
        val intent = Intent(this, Activity3ScoreBike::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item : MenuItem): Boolean {
        val intent = Intent(this, Activity3ScoreBike::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
        return true
    }


}