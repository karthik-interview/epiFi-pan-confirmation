package dev.thedukerchip.epifipan.ui.pan

import androidx.databinding.BaseObservable

data class PanVerificationForm(
    var pan: String,
    val birthDate: BirthDate
) : BaseObservable() {
    data class BirthDate(var day: String, var month: String, var year: String) : BaseObservable()

    companion object {
        fun default(): PanVerificationForm = PanVerificationForm("", BirthDate("", "", ""))
    }
}