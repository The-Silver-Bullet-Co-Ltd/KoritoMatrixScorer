package nz.co.silverbullet.koritomatrixscorer.data

data class Bike(
    val id : Long,
    val number: String,
    val make : String,
    val model : String,
    val rider : String,
    val start : Long=0,
    val finish : Long=0,
    val dabs : Int=0,
    val matrixTimePenalities : Long = 0
)
{
  public fun bike() : String {
      return make + " " + model
  }
}
