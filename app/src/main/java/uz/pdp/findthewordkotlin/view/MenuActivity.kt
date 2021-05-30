package uz.pdp.findthewordkotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu.*
import uz.pdp.findthewordkotlin.R

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        playImg.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        infoImg.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        exitImg.setOnClickListener {
            val intent=Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        settingsImg.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
