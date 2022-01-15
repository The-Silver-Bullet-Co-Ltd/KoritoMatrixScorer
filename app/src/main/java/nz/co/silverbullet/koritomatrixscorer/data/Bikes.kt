package nz.co.silverbullet.koritomatrixscorer.data

import android.content.res.Resources

fun bikeList(resources: Resources): List<Bike> {
    return listOf (
        Bike(
            id = 1,
            rider = "Bill",
            number= "23",
            make = "Suzuki",
            model = "RMZ450"
        ),
        Bike(
            id = 2,
            rider = "Lisa",
            number= "1",
            make = "Yamaha",
            model = "PeeWee"
        ),
        Bike(
            id = 3,
            rider = "Hannah",
            number= "169",
            make = "Beta",
            model = "Evo"
        ),
        Bike(
            id = 4,
            rider = "John",
            number= "32",
            make = "BMW",
            model = "R100RS"
        ),
    )
}