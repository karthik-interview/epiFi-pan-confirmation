package dev.thedukerchip.epifipan.ui.pan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.thedukerchip.epifipan.databinding.ActivityPanVerificationBinding

class PanVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPanVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}