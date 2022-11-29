package pjwstk.s20124.prm_1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pjwstk.s20124.prm_1.R
import pjwstk.s20124.prm_1.data.UserExpense

class RecyclerViewAdapter(private val listener: RowClickListener): RecyclerView.Adapter<ExpenseViewHolder>() {

    private var items  = ArrayList<UserExpense>()

    fun setListData(data: ArrayList<UserExpense>) {
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.list_row, parent, false)
        return ExpenseViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.itemView.setOnClickListener { listener.onItemClickListener(items[position]) }
        holder.itemView.setOnLongClickListener {
            listener.onLongClickListener(items[position])
            true}
        holder.bind(items[position])
    }




}