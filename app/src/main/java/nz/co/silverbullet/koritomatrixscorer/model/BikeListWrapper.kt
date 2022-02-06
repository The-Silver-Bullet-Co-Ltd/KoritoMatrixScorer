package nz.co.silverbullet.koritomatrixscorer.model

data class BikeListWrapper(
    val code : Long,
    val message : String="",
    val data : List<Bike>
)
