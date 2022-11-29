package pjwstk.s20124.prm_1.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pjwstk.s20124.prm_1.AppConstants
import pjwstk.s20124.prm_1.R
import pjwstk.s20124.prm_1.data.UserExpense
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class ExpenseViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val expenseDate: TextView = view.findViewById(R.id.expense_date)
    private val expenseValue: TextView = view.findViewById(R.id.expense_value)
    private val expenseType: TextView = view.findViewById(R.id.expense_type)
    private val expensePlace: TextView = view.findViewById(R.id.expense_place)
    private val expenseIcon: ImageView = view.findViewById(R.id.iconImageView)
    private val context = view.context;

    fun bind(data: UserExpense) {
        val df = SimpleDateFormat(AppConstants.DISPLAY_FORMAT, Locale.ENGLISH)
        expenseDate.text = df.format(data.date)
        expenseValue.text = DecimalFormat("###0.00").format(data.value)
        expensePlace.text = data.place
        expenseType.text = data.type

        if(data.value > 0){
            expenseIcon.setColorFilter(context.getColor(R.color.income))
        }
        else {
            expenseIcon.setColorFilter(context.getColor(R.color.outcome))

        }
    }
}