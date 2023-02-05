package ru.kh.bintest.ui.requestbinhistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kh.bintest.R
import ru.kh.bintest.domain.dbentity.RequestedBinEntity

class RequestedBinEntityAdapter: RecyclerView.Adapter<RequestedBinEntityAdapter.MyHolder>() {
    private lateinit var data: List<RequestedBinEntity>
    class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvNumber = itemView.findViewById<TextView>(R.id.tv_bin)
        val tvDate = itemView.findViewById<TextView>(R.id.tv_date)
    }

    fun setData(data: List<RequestedBinEntity>){
        this.data = data
    }

    fun removeAll(){
        data = ArrayList<RequestedBinEntity>(0)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.bin_history_item, parent, false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.tvDate.text = data[position].requestDate
        holder.tvNumber.text = data[position].binNumber
    }

    override fun getItemCount(): Int = data.size
}