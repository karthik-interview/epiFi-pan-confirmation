package dev.thedukerchip.epifipan.ui.pan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.thedukerchip.epifipan.databinding.ActivityPanVerificationBinding
import dev.thedukerchip.epifipan.ui.extensions.hideKeyboard
import dev.thedukerchip.epifipan.ui.extensions.setContentView
import dev.thedukerchip.epifipan.ui.validation.setupMaskedInput

class PanVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPanVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanVerificationBinding.inflate(layoutInflater)
            .setContentView(this)

        initViews()
    }

    private fun initViews() {
        binding.panEdt.setupMaskedInput("[AAAAA0000A]") {
            binding.dayEdt.requestFocus()
        }

        binding.dayEdt.setupMaskedInput("[00]") {
            binding.monthEdt.requestFocus()
        }

        binding.monthEdt.setupMaskedInput("[00]") {
            binding.yearEdt.requestFocus()
        }

        binding.yearEdt.setupMaskedInput("[0000]") {
            hideKeyboard()
        }
    }
}