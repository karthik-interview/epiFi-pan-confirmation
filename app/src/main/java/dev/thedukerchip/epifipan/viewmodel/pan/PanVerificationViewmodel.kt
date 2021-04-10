package dev.thedukerchip.epifipan.viewmodel.pan

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.thedukerchip.epifipan.R
import dev.thedukerchip.epifipan.ui.pan.PanVerificationEvent
import dev.thedukerchip.epifipan.ui.pan.PanVerificationForm
import dev.thedukerchip.epifipan.ui.pan.PanVerificationState
import dev.thedukerchip.epifipan.ui.validation.FieldValidator
import dev.thedukerchip.epifipan.ui.validation.FormResolver
import dev.thedukerchip.epifipan.ui.validation.pan.isValidDate
import dev.thedukerchip.epifipan.ui.validation.pan.isValidPan
import javax.inject.Inject

@HiltViewModel
class PanVerificationViewModel @Inject constructor(
    resources: Resources
) : ViewModel() {

    private val _uiState = MutableLiveData<PanVerificationState>()
    val uiState: LiveData<PanVerificationState>
        get() = _uiState

    val form: PanVerificationForm = PanVerificationForm.default()

    private val panValidator = FieldValidator(valueResolver = form::pan)
        .addRule(resources.getString(R.string.error_empty_pan)) {
            it.isNotEmpty()
        }
        .addRule(resources.getString(R.string.error_invalid_pan)) {
            isValidPan(it)
        }

    private val birthDateValidator = FieldValidator(valueResolver = {
        form.birthDate.format()
    }).addRule(resources.getString(R.string.error_invalid_birthdate)) {
        isValidDate(it)
    }

    private val formValidationResolver: FormResolver = FormResolver(
        listOf(
            panValidator,
            birthDateValidator
        )
    )

    fun processEvents(event: PanVerificationEvent) {
        when (event) {
            is PanVerificationEvent.ValueChanged -> validateForm()
            is PanVerificationEvent.NoPan -> _uiState.postValue(PanVerificationState.CloseApp)
            is PanVerificationEvent.VerifyPan -> _uiState.postValue(PanVerificationState.CloseApp)
        }
    }

    private fun validateForm() {
        val error = formValidationResolver.validate()
        if (error == null) {
            _uiState.postValue(PanVerificationState.ValidForm)
        } else {
            _uiState.postValue(PanVerificationState.InValidForm(error))
        }
    }
}