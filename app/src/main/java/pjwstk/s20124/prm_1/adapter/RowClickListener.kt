package pjwstk.s20124.prm_1.adapter

import pjwstk.s20124.prm_1.data.UserExpense

interface RowClickListener{
    fun onItemClickListener(userExpense: UserExpense)
    fun onLongClickListener(userExpense: UserExpense)
}