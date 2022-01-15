package nz.co.silverbullet.koritomatrixscorer

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Activity4FinishBike : AppCompatActivity() {

    private lateinit var bundle : Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_4_finish_bike)

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

        Toast.makeText(this, ""+ totalMilliseconds, Toast.LENGTH_SHORT).show()
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
        val intent = Intent(this, Activity1SelectBike::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
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