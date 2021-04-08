package dev.thedukerchip.epifipan.ui.pan

sealed class PanVerificationEvent {
    object VerifyPan : PanVerificationEvent()
    object NoPan : PanVerificationEvent()
}