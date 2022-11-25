package pjwstk.s20124.prm_1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pjwstk.s20124.prm_1.R
import pjwstk.s20124.prm_1.data.UserExpense

class RecyclerViewAdapter(val listener: RowClickListener): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var items  = ArrayList<UserExpense>()

    fun setListData(data: ArrayList<UserExpense>) {
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.list_row, parent, false)
        return MyViewHolder(inflater, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.setOnClickListener { listener.onItemClickListener(items[position]) }
        holder.itemView.setOnLongClickListener {
            listener.onLongClickListener(items[position])
            true}
        holder.bind(items[position])

    }



    class MyViewHolder(view: View, private val listener: RowClickListener): RecyclerView.ViewHolder(view) {

        private val expenseId: TextView = view.findViewById(R.id.expense_id)
        private val expenseDate: TextView = view.findViewById(R.id.expense_date)
        private val expenseValue: TextView = view.findViewById(R.id.expense_value)
        private val expenseType: TextView = view.findViewById(R.id.expense_type)
        private val expensePlace: TextView = view.findViewById(R.id.expense_place)

        fun bind(data: UserExpense) {
            expenseId.text = data.id.toString()
            expenseDate.text = data.date.toString()
            expenseValue.text = data.value.toString()
            expensePlace.text = data.place
            expenseType.text = data.type
        }
    }

    interface RowClickListener{
        fun onItemClickListener(userExpense: UserExpense)
        fun onLongClickListener(userExpense: UserExpense)
    }
}