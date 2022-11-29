package pjwstk.s20124.prm_1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import pjwstk.s20124.prm_1.data.UserExpense
import pjwstk.s20124.prm_1.viewModel.UserExpenseViewModel
import pjwstk.s20124.prm_1.viewModel.UserExpenseViewModelFactory
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class FormActivity : AppCompatActivity() {
    private lateinit var submitButton: Button
    private lateinit var shareButton: FloatingActionButton
    private lateinit var infoText: TextView
    private lateinit var linearLayout: LinearLayout
    private lateinit var userExpense: UserExpense

    private val viewModel: UserExpenseViewModel by lazy {
        ViewModelProvider(
            this,
            UserExpenseViewModelFactory((application as UserExpenseApplication))
        )[UserExpenseViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        initActivityState()
        initDatePicker()
        initButtons()
    }

    private fun initActivityState() {
        val id = intent.getIntExtra(AppConstants.ID, 0)
        infoText = findViewById(R.id.form_info_text)

        if (id > 0) {
            infoText.text = getString(R.string.form_info_edit)
            userExpense = intent.getSerializableExtra(AppConstants.DATA, UserExpense::class.java)!!
            title = getString(R.string.form_edit) + userExpense.id
            initFormValue(userExpense)
        } else {
            title = getString(R.string.form_add)
            infoText.text = getString(R.string.form_info_add)
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun initFormValue(userExpense: UserExpense) {
        val sdf = SimpleDateFormat(AppConstants.DISPLAY_FORMAT)
        findViewById<TextView>(R.id.input_type).text = userExpense.type
        findViewById<TextView>(R.id.input_value).text = userExpense.value.toString()
        findViewById<TextView>(R.id.input_place).text = userExpense.place
        findViewById<TextView>(R.id.input_date).text = sdf.format(userExpense.date)
    }

    private fun initDatePicker() {
        val datePicker = findViewById<TextView>(R.id.input_date)

        datePicker.setOnClickListener { openDatePicker(datePicker) }
    }

    @SuppressLint("SimpleDateFormat")
    private fun openDatePicker(dataPicker: TextView) {

        val picker = MaterialDatePicker.Builder
            .datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        picker.addOnPositiveButtonClickListener {
            val date = Date(Timestamp(it).time)
            val df = SimpleDateFormat(AppConstants.DISPLAY_FORMAT)
            dataPicker.text = df.format(date)
        }

        picker.show(supportFragmentManager, null)


    }

    private fun isValid(viewGroup: ViewGroup): Boolean {

        val count = viewGroup.childCount
        for (i in 0 until count) {
            val view: View = viewGroup.getChildAt(i)
            if (view is TextInputLayout && !validRequired(view)) {
                return false
            }
        }

        return true
    }

    private fun validRequired(edit: TextInputLayout?): Boolean {
        if (edit?.editText?.text.toString().trim { it <= ' ' } == "") {
            edit?.error = "Required!"
            return false
        } else {
            edit?.error = null
        }

        return true
    }

    private fun initButtons() {
        linearLayout = findViewById(R.id.form_linearLayout)
        submitButton = findViewById(R.id.form_saveButton)
        shareButton = findViewById(R.id.form_shareButton)

        submitButton.setOnClickListener {
            if (isValid(linearLayout)) {
                saveForm()
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                Snackbar.make(it, R.string.form_required_error, Snackbar.LENGTH_LONG).show()
            }
        }

        shareButton.setOnClickListener {
            if (!isValid(linearLayout)) {
                return@setOnClickListener
            }
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, prepareExpenseFromForm().toString())
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, getString(R.string.share)))
        }

    }

    private fun saveForm() {
        if (this::userExpense.isInitialized) {
            val edit = prepareExpenseFromForm()
            edit.id = userExpense.id
            viewModel.update(edit)
            Toast.makeText(this, getString(R.string.db_update_sucess), Toast.LENGTH_SHORT).show()
        } else {
            viewModel.insert(prepareExpenseFromForm())
            Toast.makeText(this, getString(R.string.db_insert_success), Toast.LENGTH_SHORT).show()
        }
    }

    private fun prepareExpenseFromForm(): UserExpense {
        val df = DateTimeFormatter.ofPattern(AppConstants.DISPLAY_FORMAT, Locale.ENGLISH)
        val localDate = LocalDate.parse(findViewById<TextView>(R.id.input_date).text, df)
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        val value = findViewById<TextView>(R.id.input_value).text.toString().toDouble()
        val place = findViewById<TextView>(R.id.input_place).text.toString()
        val type = findViewById<TextView>(R.id.input_type).text.toString()
        return UserExpense(date, place, value, type)
    }


}

