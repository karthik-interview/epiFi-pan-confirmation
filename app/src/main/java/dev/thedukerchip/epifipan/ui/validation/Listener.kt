package dev.thedukerchip.epifipan.ui.validation

import android.view.View
import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener

typealias MaskedTextListener = (maskFilled: Boolean, extractedValue: String, formattedValue: String) -> Unit

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

fun EditText.handleMaskListenerWhenFocusChanged(
    textChangedListener: MaskedTextChangedListener,
    valueListener: MaskedTextListener?
) {
    onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            if (valueListener != null) {
                textChangedListener.setMaskChangedListener(valueListener)
            }
        } else {
            textChangedListener.valueListener = null
        }
    }
}

fun EditText.setupMaskedInput(
    format: String,
    maskedTextListener: MaskedTextListener? = null
): MaskedTextChangedListener {
    val listener = MaskedTextChangedListener.installOn(this, format)

    if (maskedTextListener != null) {
        listener.setMaskChangedListener(maskedTextListener)
    }
    handleMaskListenerWhenFocusChanged(listener, maskedTextListener)

    return listener
}