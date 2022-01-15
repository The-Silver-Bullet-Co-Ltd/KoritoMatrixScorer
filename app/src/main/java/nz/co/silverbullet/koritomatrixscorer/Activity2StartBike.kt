package nz.co.silverbullet.koritomatrixscorer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Activity2StartBike : AppCompatActivity() {

    private lateinit var bundle : Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2_start_bike)

        bundle = intent.extras!!

        val nameText =  findViewById<TextView>(R.id.name2Text)
        val numberText =  findViewById<TextView>(R.id.number2Text)
        val bikeText =  findViewById<TextView>(R.id.bike2Text)

        nameText.text = bundle.getString("rider")
        numberText.text = bundle.getString("number")
        bikeText.text = bundle.getString("bike")
    }

    fun startBike(view: View) {
        val tsLong = System.currentTimeMillis()
        bundle.putLong("start", tsLong)

        val intent = Intent(this, Activity3ScoreBike::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}