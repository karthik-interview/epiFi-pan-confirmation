package dev.thedukerchip.epifipan.viewmodel.pan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.thedukerchip.epifipan.ui.pan.PanVerificationEvent
import dev.thedukerchip.epifipan.ui.pan.PanVerificationForm
import dev.thedukerchip.epifipan.ui.pan.PanVerificationState
import dev.thedukerchip.epifipan.ui.validation.FieldValidator
import dev.thedukerchip.epifipan.ui.validation.FormResolver

class PanVerificationViewModel : ViewModel() {

    private val _uiState = MutableLiveData<PanVerificationState>()
    val uiState: LiveData<PanVerificationState>
        get() = _uiState

    val form: PanVerificationForm = PanVerificationForm.default()

    // TODO Add all the validation rules
    private val panValidator = FieldValidator(valueResolver = form::pan)
        .addRule("PAN cannot be empty") { it.isNotEmpty() }

    // TODO Add validators for birthdate fields
    private val formValidationResolver: FormResolver = FormResolver(listOf(panValidator))

    fun processEvents(event: PanVerificationEvent) {
        when (event) {
            is PanVerificationEvent.ValueChanged -> {
                val error = formValidationResolver.validate()
                if (error == null) {
                    _uiState.postValue(PanVerificationState.ValidForm)
                } else {
                    _uiState.postValue(PanVerificationState.InValidForm(error))
                }
            }
            is PanVerificationEvent.NoPan -> _uiState.postValue(PanVerificationState.CloseApp)
            is PanVerificationEvent.VerifyPan -> _uiState.postValue(PanVerificationState.CloseApp)
        }
    }
}