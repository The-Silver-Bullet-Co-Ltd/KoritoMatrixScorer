package nz.co.silverbullet.koritomatrixscorer.model

data class Bike(
    val id : Long,
    val number: String,
    val make : String,
    val model : String,
    val rider : String,
    val lap : Int=0,
    val reserved : Boolean = false,
    val observedBy : String="",
    val start : Long=0,
    val finish : Long=0,
    val dabs : Int=0,
    val matrixTimePenalties : Long = 0
)
{
  public fun bike() : String {
      return "$make $model"
  }
}
