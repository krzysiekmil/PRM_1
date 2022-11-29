package pjwstk.s20124.prm_1.repository

import android.database.Cursor
import kotlinx.coroutines.flow.Flow
import pjwstk.s20124.prm_1.data.UserExpense
import java.util.*

class UserExpenseRepository(private val dao: UserExpenseDao) {

    val cursor: Cursor = dao.findAllExpensesCursor()

    val expenseList: Flow<List<UserExpense>> = dao.findAllExpenses()

    suspend fun findExpenseByMonth (date: Date) {
        dao.findExpensesByMonth(date)
    }

    suspend fun insert(userExpense: UserExpense) {
        dao.insert(userExpense)
    }

    suspend fun update(userExpense: UserExpense) {
        dao.update(userExpense)
    }

    suspend fun delete(userExpense: UserExpense) {
        dao.delete(userExpense)
    }
}