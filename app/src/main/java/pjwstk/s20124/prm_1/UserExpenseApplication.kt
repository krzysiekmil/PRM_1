package pjwstk.s20124.prm_1

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

import pjwstk.s20124.prm_1.configuration.UserExpenseDatabase
import pjwstk.s20124.prm_1.repository.UserExpenseRepository

class UserExpenseApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { UserExpenseDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { UserExpenseRepository(database.userExpenseDao()) }
}