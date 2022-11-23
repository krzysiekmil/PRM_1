package pjwstk.s20124.prm_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

class FormActivity : AppCompatActivity() {
    lateinit var saveButton: Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        saveButton = findViewById(R.id.form_saveButton)
        val id = intent.extras?.getInt("id");

        title = if(id == null){
            "Dodaj nowy wpis"
        } else {
            "Edytuj wpis: $id";
        }





    }


    fun onSubmitForm(){
        saveButton.setOnClickListener {
            Snackbar.make(it, R.string.add_successful, Snackbar.LENGTH_LONG).show();
            finish()
        }

    }
}