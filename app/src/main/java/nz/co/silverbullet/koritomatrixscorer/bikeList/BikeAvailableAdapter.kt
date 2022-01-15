package nz.co.silverbullet.koritomatrixscorer.bikeList

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nz.co.silverbullet.koritomatrixscorer.R
import nz.co.silverbullet.koritomatrixscorer.data.Bike

class BikeAvailableAdapter(
    private val bikeList: List<Bike>,
    /* listener could be Activity1SelectBike, but this would
       tightly couple this adapter with this particular activity
    */
    private val listener: OnItemClickListener ) :
    RecyclerView.Adapter<BikeAvailableAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = bikeList[position]
        holder.number.text = item.number
        holder.name.text = item.rider
        holder.bike.text = item.make + " " + item.model
    }

    override fun getItemCount(): Int {
        return bikeList.size
    }


    inner class MyViewHolder(v: View) : RecyclerView.ViewHolder(v),
        View.OnClickListener /* implement onClickListener interface */ {
        var number: TextView = v.findViewById(R.id.name4Text)
        var name: TextView = v.findViewById(R.id.number4Text)
        var bike: TextView = v.findViewById(R.id.bikeText)

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            /*
            could handle click here but logically not the best place
            deciding what happens when an item is clicked is not really
            the responsibility of the adapter
            Normally want to forward the click top the screen that displays the recyclerView
             */
            Log.d("RecyclerView", "CLICK")
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                /* need to make sure that we haven't clicked on an item
                that was e.g. in the process of being deleted
                */
                listener.onItemClick(position)
            }
        }
    }
        /*
        could use a lambda... but, to keep it simple, define an interface
        so create an interface, name of interface and fun doesn't matter
         */
        interface OnItemClickListener {
            /*
            this interface works like a contract. whatever class later implements
            this interface also has to implement the onItemClick function
             */
            fun onItemClick(position: Int)
        }
}