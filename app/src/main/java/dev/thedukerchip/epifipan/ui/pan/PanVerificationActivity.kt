package dev.thedukerchip.epifipan.ui.pan

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import dev.thedukerchip.epifipan.R
import dev.thedukerchip.epifipan.databinding.ActivityPanVerificationBinding
import dev.thedukerchip.epifipan.ui.extensions.hideKeyboard
import dev.thedukerchip.epifipan.ui.extensions.setContentView
import dev.thedukerchip.epifipan.ui.extensions.setupClickableLinks
import dev.thedukerchip.epifipan.ui.validation.setupMaskedInput
import dev.thedukerchip.epifipan.viewmodel.pan.PanVerificationViewModel
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

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
            is PanVerificationState.CloseApp -> finishAndRemoveTask()
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
        setupKeyBoardEvents()
    }

    private fun observeUiStates() {
        binding.form = viewModel.form
        viewModel.uiState.observe(this, uiStateObserver)
    }

    private fun setupForm() {
        binding.panEdt.setupMaskedInput("[AAAAA0000A]") { maskFilled, _, _ ->
            if (maskFilled) {
                binding.dateEdt.requestFocus()
            }
            viewModel.processEvents(PanVerificationEvent.ValueChanged)
        }

        binding.dateEdt.setupMaskedInput("[00]") { maskFilled, _, _ ->
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

    private fun setupKeyBoardEvents() {
        // TODO Make the elements state changes smoothly
        KeyboardVisibilityEvent.setEventListener(this) { isOpen ->
            binding.formInfoTv.isGone = isOpen
            binding.noPanBtn.isGone = isOpen
            binding.spaceBottom.isGone = isOpen
        }
    }
}