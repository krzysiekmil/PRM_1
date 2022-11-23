package pjwstk.s20124.prm_1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Lista"
        setContentView(R.layout.activity_main)

        val button = findViewById<FloatingActionButton>(R.id.addNewEntryButton);

        button.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java);
            startActivity(intent);
        }



    }

}