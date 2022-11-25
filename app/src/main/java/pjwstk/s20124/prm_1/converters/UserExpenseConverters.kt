package pjwstk.s20124.prm_1.converters

import androidx.room.TypeConverter
import java.util.*

class UserExpenseConverters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}