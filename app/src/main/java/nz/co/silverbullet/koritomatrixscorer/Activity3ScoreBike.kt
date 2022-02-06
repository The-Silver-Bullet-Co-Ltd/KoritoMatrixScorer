package nz.co.silverbullet.koritomatrixscorer

import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Activity3ScoreBike : AppCompatActivity() {

    private lateinit var bundle: Bundle
    private lateinit var dabCountText: TextView
    private lateinit var dabTimeText: TextView
    private lateinit var dabButton: Button
    private lateinit var failButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3_score_bike)

        bundle = intent.extras!!

        val nameText = findViewById<TextView>(R.id.name3Text)
        val numberText = findViewById<TextView>(R.id.number3Text)
        val bikeText = findViewById<TextView>(R.id.bike3Text)
        dabCountText = findViewById<TextView>(R.id.dabCountText)
        dabTimeText = findViewById<TextView>(R.id.dabTimeText)
        dabButton = findViewById<Button>(R.id.dabButton)
        failButton = findViewById<Button>(R.id.failButton)

        nameText.text = bundle.getString("rider")
        numberText.text = bundle.getString("number")
        bikeText.text = bundle.getString("bike")
        displayDabs(bundle.getInt("dabs"))


        val simpleChronometer =
            findViewById<View>(R.id.timeText) as Chronometer // initiate a chronometer
        simpleChronometer.start() // start a chronometer

    }

    private fun displayDabs(dabs: Int) {
        dabCountText.text = dabs.toString()
        dabTimeText.text = DateUtils.formatElapsedTime((dabs * 10).toLong())
    }

    fun scoreDab(view: View) {
        var dabs = bundle.getInt("dabs")
        if (dabs < 3) {
            dabs++
            bundle.putInt("dabs", dabs)
        }
        displayDabs(dabs)
    }

    fun scoreFail(view: View) {
        var dabs = bundle.getInt("dabs")
        if (dabs == 5) {
            dabs = 0
            dabButton.isEnabled = true
            failButton.setText("FAIL")
        } else {
            dabs = 5
            dabButton.isEnabled = false
            failButton.setText("reset")
        }

        bundle.putInt("dabs", dabs)
        displayDabs(dabs)
    }

    fun finishMatrix(view: View) {
        val tsLong = System.currentTimeMillis()
        bundle.putLong("finish", tsLong)

        val intent = Intent(this, Activity4FinishBike::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onBackPressed() {
    }
}