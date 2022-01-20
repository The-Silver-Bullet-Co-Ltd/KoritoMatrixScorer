package nz.co.silverbullet.koritomatrixscorer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import nz.co.silverbullet.koritomatrixscorer.adapter.MatrixRecyclerAdapter
import nz.co.silverbullet.koritomatrixscorer.databinding.Activity1SelectBikeBinding
import nz.co.silverbullet.koritomatrixscorer.repository.Repository

const val TAG = "SelectBikeActivity"

class Activity1SelectBike : AppCompatActivity(), MatrixRecyclerAdapter.OnItemClickListener {

    /*
    In an MVVM architecture you make the Retrofit call in the repository
    and then call that from the view model
     */
    private lateinit var binding : Activity1SelectBikeBinding

    private val matrixAdapter by lazy { MatrixRecyclerAdapter(this) }

    private lateinit var viewModel: Activity1ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = Activity1SelectBikeBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        val repository = Repository()
        val viewModelFactory = Activity1ViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory)[Activity1ViewModel::class.java]

        requestBikeListFromServer()

        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                response.body()?.let { matrixAdapter.setData(it) }
                Log.d("Response (first bike) ","nothing to see")
            } else {
                Log.d("Response ", response.errorBody().toString())
            }
        })

        /* options menu */
        mySettings()
    }

    private fun setupRecyclerView() = binding.recyclerView.apply {
        adapter = matrixAdapter
        layoutManager = LinearLayoutManager(this@Activity1SelectBike)
    }

    private fun requestBikeListFromServer() {
        viewModel.getBikes()
    }

    override fun onItemClick(position: Int) {
        val bike = viewModel.myResponse.value?.body()?.get(position)
        /* implement required function of OnItemClickListener interface */
       val bundle = Bundle()
       bundle.putLong("id",bike!!.id)
       bundle.putString("number",bike.number)
       bundle.putString("make",bike.make)
       bundle.putString("model",bike.model)
       bundle.putString("bike",bike.bike())
       bundle.putString("rider",bike.rider)
       bundle.putLong("start",bike.start)
       bundle.putLong("finish",bike.finish)
       bundle.putInt("dabs",bike.dabs)

       val intent = Intent(this, Activity2StartBike::class.java)
       intent.putExtras(bundle)
       startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> {
                Toast.makeText(this,"REFRESH Selected",Toast.LENGTH_SHORT).show()
                requestBikeListFromServer()
                return true
            }
            R.id.action_settings ->
            {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            R.id.action_help -> Toast.makeText(this,"HELP Selected",Toast.LENGTH_SHORT).show()
            R.id.action_about -> Toast.makeText(this,"ABOUT Selected",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun mySettings() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val host = prefs.getString("hostname","192.168.1.2")
        val refresh = prefs.getString("refresh","2")
        val observer = prefs.getString("observer","")

        binding.apply {
            observerTxt.text = observer + " on " + host + " (" + refresh + " secs)"
        }
    }
}