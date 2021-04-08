package dev.thedukerchip.epifipan.ui.extensions

import android.app.Activity
import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding> T.setContentView(activity: Activity): T {
    activity.setContentView(root)
    return this
}