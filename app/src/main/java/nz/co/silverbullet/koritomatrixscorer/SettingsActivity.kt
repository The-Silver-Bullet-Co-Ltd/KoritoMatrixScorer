package nz.co.silverbullet.koritomatrixscorer

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager


class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "refresh"){
            val prefs = sharedPreferences?.getString(key,"2")

            when (prefs?.toInt()) {
                0 -> Toast.makeText(this, "0 selected", Toast.LENGTH_SHORT).show()
                1 -> Toast.makeText(this, "1 selected", Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(this, "2 selected", Toast.LENGTH_SHORT).show()
                3 -> Toast.makeText(this, "3 selected", Toast.LENGTH_SHORT).show()
                5 -> Toast.makeText(this, "5 selected", Toast.LENGTH_SHORT).show()
                10 -> Toast.makeText(this, "10 selected", Toast.LENGTH_SHORT).show()
            }
        }
        if (key == "hostname") {
            /*val prefs = sharedPreferences?.getString(key,"192.168.1.2")
            val field: java.lang.reflect.Field = Retrofit::class.java.getDeclaredField("baseUrl")
            field.setAccessible(true)
            val newHttpUrl = HttpUrl.apply {  } .parse(prefs)
            field.set(RetrofitInstance, newHttpUrl)*/
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
    }
}