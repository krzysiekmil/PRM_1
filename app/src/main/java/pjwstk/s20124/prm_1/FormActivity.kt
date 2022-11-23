package pjwstk.s20124.prm_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.annotations.NotNull
import pjwstk.s20124.prm_1.utils.DbHelper

class FormActivity : AppCompatActivity() {
    lateinit var submitButton: Button
    lateinit var infoText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)


        initActivityState();

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

    private fun initSubmitButton () {
        submitButton = findViewById(R.id.form_saveButton)
        submitButton.setOnClickListener {
            Snackbar.make(it, R.string.add_successful, Snackbar.LENGTH_LONG).show()
            finish()
        }
    }
}