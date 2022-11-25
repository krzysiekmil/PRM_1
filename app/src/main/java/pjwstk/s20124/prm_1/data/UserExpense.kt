package pjwstk.s20124.prm_1.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class UserExpense(var date: Date, var type: String, var value: Double, var place: String): java.io.Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

}
