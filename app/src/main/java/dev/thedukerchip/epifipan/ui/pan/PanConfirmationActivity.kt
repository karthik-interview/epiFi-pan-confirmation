package dev.thedukerchip.epifipan.ui.pan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.thedukerchip.epifipan.databinding.ActivityPanConfirmationBinding

class PanConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPanConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}