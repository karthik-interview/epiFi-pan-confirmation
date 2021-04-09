package dev.thedukerchip.epifipan.ui.pan

sealed class PanVerificationState {
    object ValidForm : PanVerificationState()
    data class InValidForm(val error: String) : PanVerificationState()
    object CloseApp : PanVerificationState()
}