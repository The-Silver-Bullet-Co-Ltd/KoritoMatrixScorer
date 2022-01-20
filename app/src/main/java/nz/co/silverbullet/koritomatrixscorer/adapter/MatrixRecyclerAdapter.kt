package nz.co.silverbullet.koritomatrixscorer.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import nz.co.silverbullet.koritomatrixscorer.databinding.RvBikeItemBinding
import nz.co.silverbullet.koritomatrixscorer.model.Bike

class MatrixRecyclerAdapter(
    /* listener could be Activity1SelectBike, but this would
       tightly couple this adapter with this particular activity
    */
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MatrixRecyclerAdapter.MatrixViewHolder>() {

    // private var bikeList = emptyList<Bike>()
    private var bikeList  : List<Bike> /* and override get and set */
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    inner class MatrixViewHolder(val binding: RvBikeItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener /* implement onClickListener interface */ {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            /*
            could handle click here but logically not the best place
            deciding what happens when an item is clicked is not really
            the responsibility of the adapter
            Normally want to forward the click to the screen that displays the recyclerView
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatrixViewHolder {
        return MatrixViewHolder(RvBikeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount() = bikeList.size

    override fun onBindViewHolder(holder: MatrixViewHolder, position: Int) {
        holder.binding.apply {
            val bike = bikeList[position]
            number4Text.text = bike.number
            name4Text.text = bike.rider
            bikeText.text = bike.make + " " + bike.model
        }

    }

    /*
        could use a lambda... but, to keep it simple, define an interface
        so create an interface, name of interface and fun doesn't matter
        anyway, best practice way to set up onClickListener is to define an interface
    */
    interface OnItemClickListener {
        /*
        this interface works like a contract. whatever class later implements
        this interface also has to implement the onItemClick function
         */
        fun onItemClick(position: Int)
    }

    fun setData(newBikeList : List<Bike>) {
        bikeList = newBikeList
        notifyDataSetChanged()
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Bike>() {
        override fun areItemsTheSame(oldItem: Bike, newItem: Bike) :Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: Bike, newItem: Bike): Boolean {
            return oldItem == newItem
        }
    }

    /*
       So updating process will happen behind the scenes on a background thread
    */
    private val differ = AsyncListDiffer(this, diffCallback)
}