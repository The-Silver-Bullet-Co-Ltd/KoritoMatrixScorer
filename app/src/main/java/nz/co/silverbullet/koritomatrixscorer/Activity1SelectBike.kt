package nz.co.silverbullet.koritomatrixscorer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import nz.co.silverbullet.koritomatrixscorer.adapter.MatrixAdapter
import nz.co.silverbullet.koritomatrixscorer.api.RetrofitInstance
import nz.co.silverbullet.koritomatrixscorer.databinding.Activity1SelectBikeBinding
import nz.co.silverbullet.koritomatrixscorer.model.Bike
import retrofit2.HttpException
import java.io.IOException

/* https://restfulapi.net/rest-api-design-tutorial-with-example/ */

/**
   GET 	    Used to retrieve a representation of a resource.
   POST 	Used to create new new resources and sub-resources
   PUT 	    Used to update existing resources
   PATCH 	Used to update existing resources
   DELETE 	Used to delete existing resources

   get list of bikes
   GET /matrix/bikes/
   RESPONSE
   {
      "bikes": [
         {
            "bikeNumber": "350",
            "rider": "Dougy",
            "make": "Husqvarna",
            "model": "TE250",
            "arrivedAt": "2022-02-06T13:21:22Z"
         },
         {
            "bikeNumber": "350",
            "rider": "Dougy",
            "make": "Husqvarna",
            "model": "TE250",
            "arrivedAt": "2022-02-06T13:21:22Z"
         },
         {
            "bikeNumber": "350",
            "rider": "Dougy",
            "make": "Husqvarna",
            "model": "TE250",
            "arrivedAt": "2022-02-06T13:21:22Z"
         },
         {
            "bikeNumber": "350",
            "rider": "Dougy",
            "make": "Husqvarna",
            "model": "TE250",
            "arrivedAt": "2022-02-06T13:21:22Z"
         }
      ]
   }

   reserve a bike
   GET /matrix/bikes/{number}/reservations/{observerName}
   RESPONSE
   {
     "errorcode": "0",
     "bikeNumber": "350",
     "reservedBy": "Brian",
     "reservedAt": "2022-02-06T13:21:22Z"
   }

   release a bike
   DELETE /matrix/reservations/{bikeNumber}/{observerName}
   RESPONSE HTTP 200 (OK), 204 (NO CONTENT)
   {
     "errorcode": "0",
     "bikeNumber": "350"
   }

   get list of outstanding reservations?
   GET /matrix/reservations/
   RESPONSE
   {
     "errorcode": "0"
      "bikes": [
         {
            "bikeNumber": "350",
            "rider": "Dougy",
            "make": "Husqvarna",
            "model": "TE250",
            "arrivedAt": "2022-02-06T13:21:22Z",
            "reservedBy": "Brian",
            "reservedAt": "2022-02-06T13:21:22Z"
         },
         {
            "bikeNumber": "351",
            "rider": "Abe",
            "make": "Husqvarna",
            "model": "TE250",
            "arrivedAt": "2022-02-06T13:21:22Z",
            "reservedBy": "Martha",
            "reservedAt": "2022-02-06T13:21:22Z"
         },
         {
            "bikeNumber": "35",
            "rider": "Daniel",
            "make": "Husqvarna",
            "model": "TE250",
            "arrivedAt": "2022-02-06T13:21:22Z",
            "reservedBy": "Tim",
            "reservedAt": "2022-02-06T13:21:22Z"
         },
         {
            "bikeNumber": "50",
            "rider": "Lynley",
            "make": "Husqvarna",
            "model": "TE250",
            "arrivedAt": "2022-02-06T13:21:22Z",
            "reservedBy": "Abe",
            "reservedAt": "2022-02-06T13:21:22Z"
         }
      ]
   }

   save results for this pass through the matrix
   PUT /matrix/bikes/{number}/
       {
          "observer": "Brian",
          "results": {
            "startTime": "00:00:00.000",
            "endTime": "00:00:00.000",
            "dabs": "2"
          }
       }
   RESPONSE HTTP 200 OK
   {
     "errorcode": "0"
   }

 */

const val TAG = "SelectBikeActivity"

class Activity1SelectBike : AppCompatActivity(), MatrixAdapter.OnItemClickListener {

    private lateinit var bikeList : List<Bike>

    /*
    In an MVVM architecture in a real app you'd make the Retrofit call in the repository
    and then call that from the view model
     */
    private lateinit var binding : Activity1SelectBikeBinding

    private lateinit var matrixAdapter: MatrixAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = Activity1SelectBikeBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        requestBikeListFromServer()

        mySettings()

        //bikeList = Datasource(this.resources).getBikeList()

        //val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        //recyclerView.adapter = BikeAvailableAdapter(bikeList,this)
    }

    private fun requestBikeListFromServer() {
        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                RetrofitInstance.api.getBikes()
            } catch (e: IOException) {
                /*
                    e.g. don't have internet connection, output stream closed, etc
                */
                Log.e(TAG, "IOException, you might not have internet connection "+e.toString())
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e : HttpException) {
                /*
                    e.g. XXX response code doesn't begin with a 2
                */
                Log.e(TAG, "HTTPException, unexpected response")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                matrixAdapter.bikes = response.body()!!
            } else {
                Log.e( TAG, "Response not successful " +response.toString())
            }
            binding.progressBar.isVisible = false
        }
    }

    private fun setupRecyclerView() = binding.recyclerView.apply {
        matrixAdapter = MatrixAdapter(this@Activity1SelectBike)
        adapter = matrixAdapter
        layoutManager = LinearLayoutManager(this@Activity1SelectBike)
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

    override fun onItemClick(position: Int) {
        /* implement required function of OnItemClickListener interface */
         val bike = bikeList[position]
        val bundle = Bundle()
        bundle.putLong("id",bike.id)
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

        // Toast.makeText(this, "item $position clicked", Toast.LENGTH_SHORT).show()

        /* to make changes to this item...
        */
        //val clickedItem: Bike = bikeList[position]
    }

}