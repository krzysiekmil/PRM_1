package pjwstk.s20124.prm_1.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pjwstk.s20124.prm_1.data.UserExpense
import java.util.*

@Dao
interface UserExpenseDao {

    @Query("Select * from userexpense ORDER BY date DESC")
    fun findAllExpenses(): Flow<List<UserExpense>>

    @Query("Select * from userexpense ORDER BY date DESC")
    fun findAllExpensesList(): List<UserExpense>

    @Query("SELECT * FROM userexpense WHERE strftime('%m', date) = strftime('%m', :monthDate)")
    fun findExpensesByMonth(monthDate: Date): Flow<List<UserExpense>>

    @Insert
    fun insert(userExpense: UserExpense)

    @Update
    fun update(userExpense: UserExpense)

    @Delete
    fun delete(userExpense: UserExpense)


}

