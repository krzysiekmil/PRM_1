package pjwstk.s20124.prm_1.viewModel

import android.app.Application
import android.database.Cursor
import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import pjwstk.s20124.prm_1.UserExpenseApplication
import pjwstk.s20124.prm_1.data.UserExpense
import pjwstk.s20124.prm_1.repository.UserExpenseRepository

class UserExpenseViewModel(private val repository: UserExpenseRepository, application: Application) : AndroidViewModel(
    application
) {

    val userExpense: LiveData<List<UserExpense>> = repository.expenseList.asLiveData()

    val userExpenseFlow : Flow<List<UserExpense>> = repository.expenseList

    val cursor: Cursor = repository.cursor

    fun insert(expense: UserExpense) = viewModelScope.launch {
        repository.insert(expense)
    }

    fun update(userExpense: UserExpense) = viewModelScope.launch {
        repository.update(userExpense)
    }

    fun delete(userExpense: UserExpense) = viewModelScope.launch {
        repository.delete(userExpense)
    }
}


class UserExpenseViewModelFactory(private val application: UserExpenseApplication) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserExpenseViewModel::class.java)) {
            return UserExpenseViewModel(application.repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}