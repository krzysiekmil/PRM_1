package pjwstk.s20124.prm_1.viewModel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pjwstk.s20124.prm_1.data.UserExpense
import pjwstk.s20124.prm_1.repository.UserExpenseRepository

class UserExpenseViewModel(private val repository: UserExpenseRepository): ViewModel() {

    val userExpense: LiveData<List<UserExpense>> = repository.expenseList.asLiveData()

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

class UserExpenseViewModelFactory(private val repository: UserExpenseRepository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserExpenseViewModel::class.java)){
            return UserExpenseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}