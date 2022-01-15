package nz.co.silverbullet.koritomatrixscorer.data

import android.content.res.Resources

class Datasource(resources: Resources) {
    private val initialBikeList = bikeList(resources)
    // private val bikesLiveData = MutableLiveData(initialBikeList)

    fun getBikeList(): List<Bike> {
        return initialBikeList
    }
}

/*class Datasource(val context: Context) {
    fun getBikeList(): Array<String> {

        // Return flower list from string resources
        return context.resources.getStringArray(R.array.bike_list)
    }
}*/
