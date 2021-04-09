package dev.thedukerchip.epifipan.ui.validation

import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener

typealias MaskedTextListener = (maskFilled: Boolean, extractedValue: String, formattedValue: String) -> Unit
typealias MaskFilledListener = () -> Unit

fun MaskedTextChangedListener.setMaskFilledListener(maskFilledListener: MaskFilledListener): MaskedTextChangedListener {
    valueListener = object : MaskedTextChangedListener.ValueListener {
        override fun onTextChanged(
            maskFilled: Boolean,
            extractedValue: String,
            formattedValue: String,
        ) {
            if (maskFilled) {
                maskFilledListener()
            }
        }
    }
    return this
}

fun MaskedTextChangedListener.setMaskChangedListener(maskedTextListener: MaskedTextListener): MaskedTextChangedListener {
    valueListener = object : MaskedTextChangedListener.ValueListener {
        override fun onTextChanged(
            maskFilled: Boolean,
            extractedValue: String,
            formattedValue: String,
        ) {
            maskedTextListener(maskFilled, extractedValue, formattedValue)
        }
    }
    return this
}

fun EditText.setupMaskedInput(
    format: String,
    maskedTextListener: MaskedTextListener? = null
): MaskedTextChangedListener {
    val listener = MaskedTextChangedListener
        .installOn(this, format)

    if (maskedTextListener != null) {
        listener.setMaskChangedListener(maskedTextListener)
    }

    return listener
}