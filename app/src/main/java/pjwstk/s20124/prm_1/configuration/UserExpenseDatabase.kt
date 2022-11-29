package pjwstk.s20124.prm_1.configuration

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.CoroutineScope
import pjwstk.s20124.prm_1.converters.UserExpenseConverters
import pjwstk.s20124.prm_1.data.UserExpense
import pjwstk.s20124.prm_1.repository.UserExpenseDao

@Database(
    entities = [UserExpense::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(UserExpenseConverters::class)
abstract class UserExpenseDatabase : RoomDatabase() {


    abstract fun userExpenseDao(): UserExpenseDao

    companion object {

        @Volatile
        private var INSTANCE: UserExpenseDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): UserExpenseDatabase {
            return INSTANCE ?: synchronized(this) { buildDatabase(context) }
        }

        private fun buildDatabase(context: Context): UserExpenseDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                UserExpenseDatabase::class.java,
                "userExpense_database"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
    }
}