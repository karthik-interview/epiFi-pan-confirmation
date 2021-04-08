package dev.thedukerchip.epifipan.ui.validation

import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener

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

fun EditText.setupMaskedInput(
    format: String,
    maskFilledListener: MaskFilledListener? = null
): MaskedTextChangedListener {
    val listener = MaskedTextChangedListener
        .installOn(this, format)

    if (maskFilledListener != null) {
        // TODO Unable to edit the value once mask is filled
        listener.setMaskFilledListener(maskFilledListener)
    }

    return listener
}