package pjwstk.s20124.prm_1.repository

import kotlinx.coroutines.flow.Flow
import pjwstk.s20124.prm_1.data.UserExpense

class UserExpenseRepository(private val dao: UserExpenseDao) {

    val expenseList: Flow<List<UserExpense>> = dao.findAllExpenses()

    val list = dao.findAllExpenses();

    suspend fun insert(userExpense: UserExpense) {
        dao.insert(userExpense)
    }

    suspend fun update(userExpense: UserExpense) {
        dao.update(userExpense)
    }

    fun delete(userExpense: UserExpense) {
        dao.delete(userExpense)
    }
}