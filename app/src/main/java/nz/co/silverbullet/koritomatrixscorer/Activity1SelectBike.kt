package nz.co.silverbullet.koritomatrixscorer

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import nz.co.silverbullet.koritomatrixscorer.bikeList.BikeAvailableAdapter
import nz.co.silverbullet.koritomatrixscorer.data.Bike
import nz.co.silverbullet.koritomatrixscorer.data.Datasource
import nz.co.silverbullet.koritomatrixscorer.databinding.Activity1SelectBikeBinding

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

class Activity1SelectBike : AppCompatActivity(), BikeAvailableAdapter.OnItemClickListener {

    private lateinit var bikeList : List<Bike>
    private lateinit var binding : Activity1SelectBikeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = Activity1SelectBikeBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

        mySettings()

        bikeList = Datasource(this.resources).getBikeList()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = BikeAvailableAdapter(bikeList,this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> Toast.makeText(this,"REFRESH Selected",Toast.LENGTH_SHORT).show()
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