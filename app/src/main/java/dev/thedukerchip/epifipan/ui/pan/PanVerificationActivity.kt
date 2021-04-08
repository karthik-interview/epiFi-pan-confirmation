package dev.thedukerchip.epifipan.ui.pan

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dev.thedukerchip.epifipan.databinding.ActivityPanVerificationBinding
import dev.thedukerchip.epifipan.ui.extensions.hideKeyboard
import dev.thedukerchip.epifipan.ui.extensions.setContentView
import dev.thedukerchip.epifipan.ui.extensions.setupClickableLinks
import dev.thedukerchip.epifipan.ui.validation.setupMaskedInput
import dev.thedukerchip.epifipan.viewmodel.pan.PanVerificationViewModel

class PanVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPanVerificationBinding
    private val viewModel: PanVerificationViewModel by viewModels()

    private val uiStateObserver = Observer<PanVerificationState> { state ->
        when (state) {
            PanVerificationState.CloseApp -> finishAffinity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanVerificationBinding.inflate(layoutInflater)
            .setContentView(this)

        initViews()
    }

    private fun initViews() {
        binding.formInfoTv.setupClickableLinks()

        observeUiStates()
        setupForm()
        setupUiEvents()
    }

    private fun observeUiStates() {
        viewModel.uiState.observe(this, uiStateObserver)
    }

    private fun setupForm() {
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

    private fun setupUiEvents() {
        binding.nextBtn.setOnClickListener {
            viewModel.processEvents(PanVerificationEvent.VerifyPan)
        }

        binding.noPanBtn.setOnClickListener {
            viewModel.processEvents(PanVerificationEvent.NoPan)
        }
    }
}