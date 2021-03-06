package uz.pdp.findthewordkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_settings.*
import uz.pdp.findthewordkotlin.R
import uz.pdp.findthewordkotlin.model.LocalStorage

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val localStorage=LocalStorage(this)
        clearData.setOnClickListener {
            localStorage.clearData()
            Toast.makeText(this, "Data has been cleared", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
