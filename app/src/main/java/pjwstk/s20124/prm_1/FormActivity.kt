package pjwstk.s20124.prm_1

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.annotations.NotNull
import pjwstk.s20124.prm_1.utils.DbHelper
import java.util.*

class FormActivity : AppCompatActivity() {
    private lateinit var submitButton: Button
    private lateinit var infoText: TextView
    private val db = DbHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        initActivityState()
        initDatePicker()
        initSubmitButton()
    }

    private fun initActivityState() {
        val id = intent.getIntExtra(AppConstants.ID, 0);

        infoText = findViewById(R.id.form_info_text)

        if(id > 0){
            title = getString(R.string.form_edit)
            infoText.text = getString(R.string.form_info_edit)
            initFormValue(id)
        }
        else {
            title = getString(R.string.form_add)
            infoText.text = getString(R.string.form_info_add)
        }

    }

    private fun initFormValue(@NotNull id: Int) {

    }

    private fun initDatePicker() {
        val datePicker = findViewById<TextView>(R.id.input_date)

        datePicker.setOnClickListener {openDatePicker(datePicker)}
    }

    @SuppressLint("SetTextI18n")
    private fun openDatePicker(dataPicker: TextView) {

        val year: Int
        val month: Int
        val day: Int

        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.time = Date()

        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                dataPicker.text = "$dayOfMonth/$month/$year"
            }, year, month, day
        )

        datePickerDialog.show();


    }

    fun validateForm(viewGroup: ViewGroup){

    }

    private fun initSubmitButton () {

        submitButton = findViewById(R.id.form_saveButton)
        submitButton.setOnClickListener {
            Snackbar.make(it, R.string.add_successful, Snackbar.LENGTH_LONG).show()
            finish()
        }
    }


}