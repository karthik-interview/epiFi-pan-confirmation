package dev.thedukerchip.epifipan.ui.extensions

import android.text.util.Linkify
import android.widget.TextView
import me.saket.bettermovementmethod.BetterLinkMovementMethod

fun TextView.setupClickableLinks() {
    Linkify.addLinks(this, Linkify.ALL)
    movementMethod = BetterLinkMovementMethod.getInstance()
}