package dev.thedukerchip.epifipan.viewmodel.pan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.thedukerchip.epifipan.ui.pan.PanVerificationEvent
import dev.thedukerchip.epifipan.ui.pan.PanVerificationState

class PanVerificationViewModel : ViewModel() {

    private val _uiState = MutableLiveData<PanVerificationState>()
    val uiState: LiveData<PanVerificationState>
        get() = _uiState

    fun processEvents(event: PanVerificationEvent) {
        when (event) {
            is PanVerificationEvent.NoPan -> _uiState.postValue(PanVerificationState.CloseApp)
            is PanVerificationEvent.VerifyPan -> _uiState.postValue(PanVerificationState.CloseApp)
        }
    }
}