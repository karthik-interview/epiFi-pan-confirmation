package dev.thedukerchip.epifipan.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.thedukerchip.epifipan.ui.pan.PanVerificationActivity

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, PanVerificationActivity::class.java))
        finish()
    }
}