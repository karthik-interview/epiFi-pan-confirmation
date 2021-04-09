package dev.thedukerchip.epifipan.ui.pan

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import dev.thedukerchip.epifipan.R
import dev.thedukerchip.epifipan.databinding.ActivityPanVerificationBinding
import dev.thedukerchip.epifipan.ui.extensions.hideKeyboard
import dev.thedukerchip.epifipan.ui.extensions.setContentView
import dev.thedukerchip.epifipan.ui.extensions.setupClickableLinks
import dev.thedukerchip.epifipan.ui.validation.setupMaskedInput
import dev.thedukerchip.epifipan.viewmodel.pan.PanVerificationViewModel

@AndroidEntryPoint
class PanVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPanVerificationBinding
    private val viewModel: PanVerificationViewModel by viewModels()

    private val uiStateObserver = Observer<PanVerificationState> { state ->
        when (state) {
            is PanVerificationState.ValidForm -> {
                binding.nextBtn.apply {
                    setText(R.string.action_next)
                    isEnabled = true
                }
            }
            is PanVerificationState.InValidForm -> setFormError(state.error)
            is PanVerificationState.CloseApp -> finishAffinity()
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
        binding.form = viewModel.form
        viewModel.uiState.observe(this, uiStateObserver)
    }

    private fun setupForm() {
        // TODO Unable to edit the value once mask is filled - For all edt
        binding.panEdt.setupMaskedInput("[AAAAA0000A]") { maskFilled, _, _ ->
            if (maskFilled) {
                binding.dayEdt.requestFocus()
            }
            viewModel.processEvents(PanVerificationEvent.ValueChanged)
        }

        binding.dayEdt.setupMaskedInput("[00]") { maskFilled, _, _ ->
            if (maskFilled) {
                binding.monthEdt.requestFocus()
            }
            viewModel.processEvents(PanVerificationEvent.ValueChanged)
        }

        binding.monthEdt.setupMaskedInput("[00]") { maskFilled, _, _ ->
            if (maskFilled) {
                binding.yearEdt.requestFocus()
            }
            viewModel.processEvents(PanVerificationEvent.ValueChanged)
        }

        binding.yearEdt.setupMaskedInput("[0000]") { maskFilled, _, _ ->
            if (maskFilled) {
                hideKeyboard()
            }
            viewModel.processEvents(PanVerificationEvent.ValueChanged)
        }

        viewModel.processEvents(PanVerificationEvent.ValueChanged)
    }

    private fun setupUiEvents() {
        binding.nextBtn.setOnClickListener {
            viewModel.processEvents(PanVerificationEvent.VerifyPan)
        }

        binding.noPanBtn.setOnClickListener {
            viewModel.processEvents(PanVerificationEvent.NoPan)
        }
    }

    private fun setFormError(error: String) {
        binding.nextBtn.apply {
            isEnabled = false
            text = error
        }
    }
}